public class Message {
	String id;
	String user;
	String messageText;

public Message(String user, String messageText, String id) {
	this.id = id;
	this.user = user;
	this.messageText = messageText;
}

public String toString() {
	return "{\"id\":\"" + this.id + "\",\"user\":\"" + this.user + "\",\"messageText\":\"" + this.messageText + "\"}";
}

public boolean equals(Message mess) {
	if(mess.id != this.id)
		return false;
	if(mess.user != this.user)
		return false;
	if(mess.messageText != this.messageText)
		return false;
	return true;
}

};