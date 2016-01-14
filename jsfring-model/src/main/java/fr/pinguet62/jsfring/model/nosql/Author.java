package fr.pinguet62.jsfring.model.nosql;

import java.util.List;

public class Author {

    private List<Message> messages;

    private String pseudo;

    public List<Message> getMessages() {
        return messages;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

}