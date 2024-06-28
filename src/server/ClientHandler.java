// 서버 패키지 정의
package server; // 'server' 패키지에 속하는 클래스임을 선언

import java.io.*; // 입출력 관련 클래스들을 임포트
import java.net.*; // 네트워크 관련 클래스들을 임포트

// ClientHandler 클래스 정의, Runnable 인터페이스를 구현하여 스레드에서 실행 가능
public class ClientHandler implements Runnable {
    private static int clientCount = 0; // 클라이언트 ID를 위한 정적 변수, 모든 인스턴스가 공유
    private final int clientId; // 클라이언트 ID를 위한 인스턴스 변수, 각 인스턴스마다 고유
    // 클라이언트의 소켓과 서버 인스턴스를 저장할 필드
    private final Socket clientSocket; // 클라이언트와의 통신을 위한 소켓
    private final Server server; // 서버 인스턴스 참조
    // 클라이언트로 메시지를 전송하기 위한 PrintWriter
    private PrintWriter out; // 클라이언트로 데이터를 보내기 위한 PrintWriter 객체

    // 생성자
    public ClientHandler(Socket socket, Server server) {
        this.clientSocket = socket; // 클라이언트 소켓 초기화
        this.server = server; // 서버 인스턴스 초기화
        this.clientId = ++clientCount; // 클라이언트 ID 할당, 클라이언트 수 증가

        try {
            // 클라이언트 소켓의 출력 스트림을 얻어 PrintWriter를 생성, autoFlush를 true로 설정
            this.out = new PrintWriter(clientSocket.getOutputStream(), true); // 클라이언트로 데이터를 보내기 위한 출력 스트림 생성
        } catch (IOException e) {
            // 출력 스트림을 얻는 데 실패하면 오류 메시지를 출력
            e.printStackTrace(); // 예외 발생 시 스택 트레이스를 출력
        }
    }

    // 스레드에서 실행할 run 메소드
    @Override
    public void run() {
        try {
            // 클라이언트로부터 메시지를 읽기 위한 BufferedReader 생성
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); // 클라이언트로부터
                                                                                                          // 데이터를 읽기 위한
                                                                                                          // 입력 스트림 생성
            String inputLine; // 입력된 데이터를 저장할 변수
            // 입력 스트림에서 한 줄씩 읽어서 처리
            while ((inputLine = in.readLine()) != null) { // 클라이언트로부터 한 줄씩 데이터를 읽음
                // 받은 메시지를 콘솔에 출력
                String message = "Client " + clientId + ": " + inputLine; // 메시지에 클라이언트 ID 추가
                System.out.println("Received: " + message); // 받은 메시지를 콘솔에 출력

                // 서버를 통해 다른 클라이언트에게 메시지 브로드캐스트
                server.broadcastMessage(message); // 서버를 통해 다른 클라이언트에게 메시지를 전송
            }
        } catch (IOException e) {
            // 읽기 중 오류 발생 시 스택 트레이스 출력
            e.printStackTrace(); // 예외 발생 시 스택 트레이스를 출력
        } finally {
            System.out.println("Client " + clientId + " disconnected");
            // 마지막에 클라이언트 소켓을 닫음
            try {
                clientSocket.close(); // 클라이언트 소켓을 닫음
            } catch (IOException e) {
                // 소켓 닫기 실패 시 오류 메시지 출력
                e.printStackTrace(); // 예외 발생 시 스택 트레이스를 출력
            }
        }
    }

    // 클라이언트에게 메시지를 전송하는 메소드
    public void sendMessage(String message) {
        out.println(message); // 클라이언트로 메시지를 전송
    }
}