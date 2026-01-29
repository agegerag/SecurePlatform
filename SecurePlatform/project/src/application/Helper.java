package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.security.MessageDigest;

import acsse.csc03a3.Transaction;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Helper {

	public void switchscene(ActionEvent event, String path) {
		try {

			Parent root = FXMLLoader.load(getClass().getResource(path));
			Scene scene = new Scene(root);
			Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String hashPassword(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hash = md.digest(password.getBytes());

			StringBuilder hexString = new StringBuilder();
			for (byte b : hash) {
				hexString.append(String.format("%02x", b));
			}

			return hexString.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	//helper method to register a user
	public void registerUser(String name_and_surname, String email, String phone_Number, String id_number, String password) {
		String hashedPassword = hashPassword(password);

		String sql = "INSERT INTO users(full_name, email, phone, id_number, password) VALUES (?, ?, ?, ?, ?)";

		try (Connection conn = Database.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, name_and_surname);
			stmt.setString(2, email);
			stmt.setString(3, phone_Number);
			stmt.setString(4, id_number);
			stmt.setString(5, hashedPassword);

			stmt.executeUpdate();
			System.out.println("User registered successfully!");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	//Helper method to sign in
	  public void signin(String Email, String Password, ActionEvent event) {
		  String hashedPassword = hashPassword(Password);
		  String sql = "SELECT * FROM users WHERE email = ? AND password = ?";

		  try (Connection conn = Database.getConnection();
			   PreparedStatement stmt = conn.prepareStatement(sql)) {

			  stmt.setString(1, Email);
			  stmt.setString(2, hashedPassword);

			  ResultSet rs = stmt.executeQuery();

			  if (rs.next()) {
				  // Login successful
				  try {
					  Parent root = FXMLLoader.load(getClass().getResource("Chat.fxml"));
					  Scene scene = new Scene(root);
					  Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
					  stage.setScene(scene);
					  stage.show();
				  } catch (IOException e) {
					  e.printStackTrace();
				  }
			  } else {
				  System.out.println("Invalid email or password.");
			  }

		  } catch (Exception e) {
			  e.printStackTrace();
		  }
			  }
	  
	  
	  public  void printMessages(String inputFile) throws IOException {
          BufferedReader reader = new BufferedReader(new FileReader(inputFile));
          String line;
          
          while ((line = reader.readLine()) != null) {
              String message = extractMessageFromLine(line);
              if (message != null && !message.isEmpty()) {
                  System.out.println("Message: " + message);
                  
                  
              }
          }
          
          reader.close();
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
	  

	  public void saveTransactionsToDatabase(List<Transaction<Message>> transactions) {
		  String sql = "INSERT INTO messages(sender, content) VALUES (?, ?)";

		  try (Connection conn = Database.getConnection();
			   PreparedStatement stmt = conn.prepareStatement(sql)) {

			  for (Transaction<Message> transaction : transactions) {

				  String transactionString = transaction.toString();
				  String messageContent = extractMessageFromLine(transactionString);

				  if (messageContent != null) {
					  stmt.setString(1, "System");   // or actual sender if available
					  stmt.setString(2, messageContent);
					  stmt.executeUpdate();
				  }
			  }

			  System.out.println("Transactions saved to database!");

		  } catch (Exception e) {
			  e.printStackTrace();
		  }
	    }
}
