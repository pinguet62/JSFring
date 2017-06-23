package fr.pinguet62.jsfring.model.nosql;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class Person {

    private String biography;

    private LocalDateTime birthDate;

    @Id
    private ObjectId id;

    private String name;

}