package fr.pinguet62.jsfring.model.nosql;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document
public final class Movie {

    private List<Casting> castings;

    @DBRef
    private List<Comment> comments;

    @Id
    private ObjectId id;

    private LocalDateTime releaseDate;

    private String synopsis;

    private String title;

}