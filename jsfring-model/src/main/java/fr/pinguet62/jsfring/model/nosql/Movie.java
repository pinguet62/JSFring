package fr.pinguet62.jsfring.model.nosql;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;

public final class Movie {

    private List<Casting> castings;

    private List<Comment> comments;

    @Id
    private String id;

    private Date releaseDate;

    private String synopsis;

    private String title;

    public List<Casting> getCastings() {
        return castings;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public String getId() {
        return id;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getTitle() {
        return title;
    }

    public void setCastings(List<Casting> castings) {
        this.castings = castings;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return getClass().getName() + ": [title=\"" + title + "\"]";
    }

}