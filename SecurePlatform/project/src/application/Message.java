package application;

public class Message {

	private String message;
	private String sender = "anonymous08";
	private String receiver = "Everyone";
	
	public Message(String message, String sender, String receiver) {
		this.message = message;
		this.sender = sender;
		this.receiver = receiver;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public Message(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "[message = " + message + ", sender=" + sender + ", receiver=" + receiver + " ]" ;
	}
	
	
	
}
