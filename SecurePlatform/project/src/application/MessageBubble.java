package application;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MessageBubble extends HBox {

    private String senderName;
    private String message;
    private LocalDateTime timestamp;

    public MessageBubble(String senderName, String message, boolean isCurrentUser) {
        this.senderName = senderName;
        this.message = message;
        this.timestamp = LocalDateTime.now();

        setSpacing(5);
        setAlignment(isCurrentUser ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);
        setBackground(isCurrentUser ? Color.LIGHTBLUE : Color.LIGHTGREEN);

        Label nameLabel = new Label(this.senderName);
        nameLabel.setStyle("-fx-font-weight: bold;");
        Label messageLabel = new Label(this.message);
        Label timeLabel = new Label(timestamp.format(DateTimeFormatter.ofPattern("HH:mm")));

        if (!isCurrentUser) {
            getChildren().addAll(nameLabel, messageLabel, timeLabel);
        } else {
            getChildren().addAll(timeLabel, messageLabel, nameLabel);
        }
    }

    private void setBackground(Color color) {
        String hex = String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
        setStyle("-fx-background-color: " + hex + "; -fx-padding: 8px;");
    }

    public String getSenderName() {
        return senderName;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}