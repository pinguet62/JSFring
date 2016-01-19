package fr.pinguet62.jsfring.model.nosql;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class Person {

    private String biography;

    private Date birthDate;

    @Id
    private String id;

    private String name;

    public String getBiography() {
        return biography;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getClass().getName() + ": [name=\"" + name + "\"]";
    }

}