class Message {
	String id;
	String user;
	String messageText;

public Message(String user, String messageText, String id)
{
	this.id = id;
	this.user = user;
	this.messageText = messageText;
}

public String toString()
{
	return "{\"id\":\"" + this.id + "\",\"user\":\"" + this.user + "\",\"messageText\":\"" + this.messageText + "\"}";
}

};