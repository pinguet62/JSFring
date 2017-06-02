package fr.pinguet62.jsfring.model.nosql;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class Person {

    private String biography;

    private Date birthDate;

    @Id
    private ObjectId id;

    private String name;

}