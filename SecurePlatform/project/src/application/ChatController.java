package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.print.DocFlavor.URL;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;

import acsse.csc03a3.Blockchain;
import acsse.csc03a3.Transaction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ChatController {
	
	@FXML
    private TextField email_address;

    @FXML
    private TextField full_name;

    @FXML
    private TextField id_number;

    @FXML
    private PasswordField password;

    @FXML
    private TextField phone_number;
      
    @FXML
    private TextField message;
     
    @FXML
    private Pane mainPane;
    
    @FXML
    private TextField message_member;
    
    @FXML
    private Text received_message;
    
    @FXML
    private Text received_message1;

    @FXML
    private Text received_message2;

    @FXML
    private Text received_message3;

    @FXML
    private Text received_message4;

    @FXML
    private Text received_message5;

    @FXML
    private Text received_message6;

    @FXML
    private Text received_message7;

    @FXML
    private Text time;

    @FXML
    private Text time1;

    @FXML
    private Text time2;

    @FXML
    private Text time3;

    @FXML
    private Text time4;

    @FXML
    private Text time5;

    @FXML
    private Text time6;

    @FXML
    private Text time7;
    
    @FXML
    private Pane messagePane;
    
    @FXML
    private Pane MessagePane;
    
    @FXML
    private Pane InidePane;
    
    //login
    @FXML
    private PasswordField user_password;

    @FXML
    private TextField username;
    
    private Blockchain<Message> blockchain = new Blockchain<>();
	
	private Helper helper_methods = new Helper();
	
	private boolean stakeR= false;
	
	private double currentY = 0;
 
	
	public ChatController() {
        if (!stakeR) {
            blockchain.registerStake("0", 100);
            stakeR = true;
        }
     }

    @FXML
    void alreadyHaveAccount(ActionEvent event) {
    	helper_methods.switchscene(event, "Login.fxml");
    }

    @FXML
    void registerUser(ActionEvent event) {
    	helper_methods.registerUser(full_name.getText(), email_address.getText(), phone_number.getText(),
    			id_number.getText(), password.getText());
    	helper_methods.switchscene(event, "Login.fxml");
     }  
    
    @FXML
    void dontHaveAccount(ActionEvent event) {
    	helper_methods.switchscene(event, "Registration.fxml");	
    }
    
 
    @FXML
    void login(ActionEvent event) throws IOException {
    	helper_methods.signin(username.getText(), user_password.getText(), event);
	
    }

    public void printMessagesFromDatabase() {

        String sql = "SELECT content FROM messages ORDER BY id ASC";

        InidePane.getChildren().clear();
        currentY = 0;

        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                String message = rs.getString("content");

                System.out.println("Message: " + message);

                MessageBubble messageBubble = new MessageBubble("Anonymous", message, false);

                messageBubble.setLayoutY(currentY);

                InidePane.getChildren().add(messageBubble);

                currentY += messageBubble.getHeight() + 50;

                InidePane.layout();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    void loadStories(ActionEvent event) {

        printMessagesFromDatabase();
    }


	  //extract the message from a line
	  private static String extractMessageFromLine(String line) {
	        String message = null;
	        Pattern pattern = Pattern.compile("message\\s*=\\s*(.*?),");
	        Matcher matcher = pattern.matcher(line);        
	        if (matcher.find()) {
	            message = matcher.group(1).trim();
	        }
	        
	        return message;
	    }
    
	  
	  
    @FXML
    void logout(ActionEvent event) {
    	helper_methods.switchscene(event, "Login.fxml");	
    }

    @FXML
    void sendMessage(ActionEvent event) {
    	
    	Message mess = new Message(message.getText());

 	    Transaction<Message> transaction = new Transaction<>("Anonymous", "Everyone", mess);
  
  	    List<Transaction<Message>> transactions = new ArrayList<>();
  	   
        transactions.add(transaction);
 	   
 	    blockchain.addBlock(transactions);
 	    
 	    helper_methods.saveTransactionsToDatabase(transactions);
 	   
 	    String messageText = message.getText();
 	  
      if (!messageText.isEmpty()) {
          
    	  setMessage(messageText);
    	  
    	  setTime();
    	  
          MessageBubble messageBubble = new MessageBubble("Anonymous", messageText, true);

          messageBubble.setLayoutY(currentY);

          mainPane.getChildren().add(messageBubble);

          currentY += messageBubble.getHeight() + 50;

          message.clear();

          mainPane.layout();
      }
  
 	   System.out.println(blockchain.toString());
       message.setText("");
    }
 
    
    
    @FXML
    void sendMessageMember(ActionEvent event) {
    	Message mess = new Message(message.getText());

 	    Transaction<Message> transaction = new Transaction<>("Anonymous080", "Everyone", mess);
  
  	    List<Transaction<Message>> transactions = new ArrayList<>();
  	   
        transactions.add(transaction);
 	   
 	    blockchain.addBlock(transactions);
 	    
 	   helper_methods.saveTransactionsToDatabase(transactions);
 	   
 	  String messageText = message_member.getText();
 	  
 	  
      if (!messageText.isEmpty()) {
        
          MessageBubble messageBubble = new MessageBubble("Anonymous", messageText, false);

          messageBubble.setLayoutY(currentY);

          mainPane.getChildren().add(messageBubble);

          currentY += messageBubble.getHeight() + 50;

          message.clear();

          mainPane.layout();
      }
   
 	   System.out.println(blockchain.toString());

 	  message_member.setText("");
    }

    
	public void setTime() {
	    String currentTime = LocalDateTime.now().toLocalTime().toString();
		time.setText(currentTime);
		time1.setText(currentTime);
		time2.setText(currentTime);
		time3.setText(currentTime);
		time4.setText(currentTime);
		time5.setText(currentTime);
		time6.setText(currentTime);
		time7.setText(currentTime);
	}

	
	public void setMessage(String messageText) {
		received_message.setText(messageText);
    	  received_message1.setText(messageText);
    	  received_message2.setText(messageText);
    	  received_message3.setText(messageText);
    	  received_message4.setText(messageText);
    	  received_message5.setText(messageText);
    	  received_message6.setText(messageText);
    	  received_message7.setText(messageText);
	}

}
