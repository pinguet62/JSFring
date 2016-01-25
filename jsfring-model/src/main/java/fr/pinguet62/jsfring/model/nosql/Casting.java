package fr.pinguet62.jsfring.model.nosql;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class Casting {

    @Id
    private ObjectId id;

    @DBRef
    private Person person;

    private String role;

    public ObjectId getId() {
        return id;
    }

    public Person getPerson() {
        return person;
    }

    public String getRole() {
        return role;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return getClass().getName() + ": [person.name=\"" + person.getName() + "\", role=\"" + role + "\"]";
    }

}