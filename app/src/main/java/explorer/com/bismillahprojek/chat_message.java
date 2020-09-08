package explorer.com.bismillahprojek;

import java.util.Date;

/**
 * Created by root on 19/06/18.
 */

public class chat_message {private String messageText;
    private String messageUser;
    private long messageTime;

    public chat_message(String messageText, String messageUser){
        this.messageText = messageText;
        this.messageUser = messageUser;

        messageTime = new Date().getTime();
    }

    public chat_message() {
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }
}