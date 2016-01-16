package fr.pinguet62.jsfring.model.nosql;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;

public final class Movie {

    private List<Comment> comments;

    private String description;

    @Id
    private String id;

    private Date releaseDate;

    private String title;

    public List<Comment> getComments() {
        return comments;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}