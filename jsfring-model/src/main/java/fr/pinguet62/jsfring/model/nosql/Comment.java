package fr.pinguet62.jsfring.model.nosql;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class Comment {

    private Author author;

    private String content;

    private Date date;

    @Id
    private String id;

    private short rating;

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

    public short getRating() {
        return rating;
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

    public void setRating(short rating) {
        this.rating = rating;
    }

}