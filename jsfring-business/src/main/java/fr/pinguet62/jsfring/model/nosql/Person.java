package fr.pinguet62.jsfring.model.nosql;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
public class Person {

    private String biography;

    private LocalDateTime birthDate;

    @Id
    private ObjectId id;

    private String name;

}