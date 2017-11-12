package fr.pinguet62.jsfring.model.nosql;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class User {

    @Id
    private ObjectId id;

    private String pseudo;

}