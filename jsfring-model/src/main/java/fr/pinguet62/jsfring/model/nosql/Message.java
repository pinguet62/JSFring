package fr.pinguet62.jsfring.model.nosql;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;

public class Message {

    private Author author;

    private String content;

    private Date date;

    @Id
    private String id;

    private List<Message> responses;

    public Author getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public Date getDate() {
        return date;
    }

    public String getId() {
        return id;
    }

    public List<Message> getResponses() {
        return responses;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setResponses(List<Message> responses) {
        this.responses = responses;
    }

}