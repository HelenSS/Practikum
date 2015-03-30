import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.List;

public class MessageExchange {

    public class Mess
    {
	String id;
	String user;
	String message;
    };

    private JSONParser jsonParser = new JSONParser();

    public String getToken(int index) {
        Integer number = index * 8 + 11;
        return "TN" + number + "EN";
    }

    public int getIndex(String token) {
        return (Integer.valueOf(token.substring(2, token.length() - 2)) - 11) / 8;
    }

    public String getServerResponse(List<String> messages) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("messages", messages);
        jsonObject.put("token", getToken(messages.size()));
        return jsonObject.toJSONString();
    }

    public String getClientSendMessageRequest(String id, String user, String message) {
        JSONObject jsonObject = new JSONObject();
	jsonObject.put("id", id);
	jsonObject.put("user", user);
        jsonObject.put("message", message);
        return jsonObject.toJSONString();
    }

    public Mess getClientIdUserMessage(InputStream inputStream) throws ParseException {
	Mess mess = new Mess();
	JSONObject jsonObject = getJSONObject(inputStreamToString(inputStream));
	mess.id = (String) jsonObject.get("id");
	mess.user = (String) jsonObject.get("user");
	mess.message = (String) jsonObject.get("message");
	return mess;
    }

    public String getIdForDelete(InputStream inputStream) throws ParseException {
	JSONObject jsonObject = getJSONObject(inputStreamToString(inputStream));
	return (String) jsonObject.get("id");
    }

    public Mess getPut(InputStream inputStream) throws ParseException {
	Mess mess = new Mess();
	JSONObject jsonObject = getJSONObject(inputStreamToString(inputStream));
	mess.id = (String) jsonObject.get("id");
	mess.message = (String) jsonObject.get("message");
	mess.user = "";
	return mess;
    }

    public String getUser (String message) throws ParseException {
	for (int j = 6; j < message.length(); j++)
	    if (message.charAt(j - 2) == ':')
		for (int k = j; k<message.length(); k++)
		     if(message.charAt(k+8) == ':')
			return message.substring(j, k);
    return null;
    }

    public JSONObject getJSONObject(String json) throws ParseException {
        return (JSONObject) jsonParser.parse(json.trim());
    }

    public String inputStreamToString(InputStream in) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length = 0;
        try {
            while ((length = in.read(buffer)) != -1) {
                baos.write(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new String(baos.toByteArray());
    }
}
