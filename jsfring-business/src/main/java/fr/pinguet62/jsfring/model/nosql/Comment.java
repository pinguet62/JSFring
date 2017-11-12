package fr.pinguet62.jsfring.model.nosql;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDateTime;

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