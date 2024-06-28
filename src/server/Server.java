    // 서버 패키지 선언
    package server;

    // 필요한 자바 라이브러리들을 임포트
    import java.io.*;
    import java.net.*;
    import java.util.*;

    // Server 클래스 정의
    public class Server {
        // 서버 소켓을 저장할 변수
        private ServerSocket serverSocket;
        // 연결된 클라이언트를 관리하는 리스트
        private List<ClientHandler> clients = new ArrayList<>();

        // 서버 생성자: 포트 번호를 인자로 받아 서버 소켓을 초기화
        public Server(int port) {
            try {
                // 서버 소켓을 해당 포트에 바인드
                serverSocket = new ServerSocket(port);
                // 서버 시작 메시지 출력
                System.out.println("Server started on port: " + port);
            } catch (IOException e) {
                // 예외 발생 시 스택 트레이스를 출력
                e.printStackTrace();
            }
        }

        // 서버 시작 메소드
        public void start() {
            // 무한 루프를 돌면서 클라이언트 연결을 계속 기다림
            while (true) {
                try {
                    // 클라이언트 소켓 수락
                    Socket clientSocket = serverSocket.accept();
                    // 새 클라이언트 연결 메시지 출력
                    System.out.println("New client connected");
                    // 새 클라이언트 핸들러 객체 생성 및 클라이언트 리스트에 추가
                    ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                    clients.add(clientHandler);
                    // 클라이언트 핸들러를 위한 새 스레드 시작
                    Thread temp = new Thread(clientHandler);
                    temp.start();
                    temp.interrupt();
                } catch (IOException e) {
                    // 예외 발생 시 스택 트레이스를 출력
                    e.printStackTrace();
                }
            }
        }

        // 메시지를 모든 클라이언트에게 전송하는 메소드
        public void broadcastMessage(String message) {
            // 연결된 모든 클라이언트에게 메시지 전송
            for (ClientHandler client : clients) {
                client.sendMessage(message);
            }
        }

        // 메인 메소드: 서버 실행
        public static void main(String[] args) {
            int port = 8000; // 사용할 포트 번호를 설정
            Server server = new Server(port); // 서버 객체 생성
            server.start(); // 서버 시작
        }
    }
