package model;

import java.util.*;

public final class MessageStorage {
    private static final List<Message> INSTANSE = Collections.synchronizedList(new ArrayList<Message>());

    private MessageStorage() {
    }

    public static List<Message> getStorage() {
        return INSTANSE;
    }

    public static void addMessage(Message message) {
        INSTANSE.add(message);
    }

    public static void addAll(Message [] messages) {
        INSTANSE.addAll(Arrays.asList(messages));
    }

    public static void addAll(List<Message> messages) {
        INSTANSE.addAll(messages);
    }

    public static int getSize() {
        return INSTANSE.size();
    }

    public static List<Message> getSubMessagesByIndex(int index) {
        return INSTANSE.subList(index, INSTANSE.size());
    }

    public static int getIndex(String token) {
        return Integer.parseInt(token.substring(2, token.length()-2));
    }
}