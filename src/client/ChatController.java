package client;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatController {
    @FXML
    private ListView<String> messagesList;
    @FXML
    private TextField messageField;

    private PrintWriter writer;

    public void initialize() {
        try {
            Socket socket = new Socket("localhost", 8000); // 서버 주소와 포트를 설정
            // 3WAY 핸드 쉐이킹 서버 연결요청
            writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            new Thread(() -> {
                try {
                    String message;
                    while ((message = reader.readLine()) != null) {
                        String finalMessage = message;
                        Platform.runLater(() -> messagesList.getItems().add(finalMessage));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void sendMessage() {
        String message = messageField.getText();
        if (message != null && !message.isEmpty()) {
            writer.println(message);
            messageField.clear();
        }
    }
}
