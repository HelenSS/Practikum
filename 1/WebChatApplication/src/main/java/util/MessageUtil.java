package util;

import model.Message;
import model.MessageStorage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Date;

public final class MessageUtil {
    public static final String TOKEN = "token";
    public static final String USER = "user";
    public static final String MESSAGES = "messages";
    private static final String TE = "TE";
    private static final String EN = "EN";
    private static final String ID = "id";
    private static final String TEXT = "text";
    private static final String DATE = "date";

    private MessageUtil() {
    }

    public static String getToken() {
        Integer number = MessageStorage.getSize();
        return TE + number + EN;
    }

    public static int getIndex(String token) {
        return Integer.valueOf(token.substring(2, token.length() - 2));
    }

    public static JSONObject stringToJson(String data) throws ParseException {
        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(data.trim());
    }

    public static Message jsonToMessage(JSONObject json) {
        Object id = json.get(ID);
        Object user_id = json.get(USER);
        Object text = json.get(TEXT);
        Object date = json.get(DATE);
        if (id != null && user_id != null && text != null && date != null) {
            return new Message(Integer.parseInt((String) id), (String) user_id, (String) text, new Date((long)date));
        }
        return null;
    }
}