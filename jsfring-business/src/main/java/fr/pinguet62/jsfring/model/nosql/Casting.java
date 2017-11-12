package fr.pinguet62.jsfring.model.nosql;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
public class Casting {

    @Id
    private ObjectId id;

    @DBRef
    private Person person;

    private String role;

}