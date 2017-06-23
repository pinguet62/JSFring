package fr.pinguet62.jsfring.model.nosql;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import lombok.Data;

@Data
public class Comment {

    private String content;

    private LocalDateTime date;

    @Id
    private ObjectId id;

    private int rating;

    @DBRef
    private User user;

}