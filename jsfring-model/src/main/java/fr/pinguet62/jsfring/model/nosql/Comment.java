package fr.pinguet62.jsfring.model.nosql;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class Comment {

    private String content;

    private Date date;

    @Id
    private ObjectId id;

    private int rating;

    @DBRef
    private User user;

    public String getContent() {
        return content;
    }

    public Date getDate() {
        return date;
    }

    public ObjectId getId() {
        return id;
    }

    public int getRating() {
        return rating;
    }

    public User getUser() {
        return user;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setUser(User author) {
        this.user = author;
    }

}