package fr.pinguet62.jsfring.model.nosql;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public final class Movie {

    private List<Casting> castings;

    @DBRef
    private List<Comment> comments;

    @Id
    private ObjectId id;

    private Date releaseDate;

    private String synopsis;

    private String title;

}