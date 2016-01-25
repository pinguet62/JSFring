package fr.pinguet62.jsfring.model.nosql;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User {

    @Id
    private ObjectId id;

    private String pseudo;

    public ObjectId getId() {
        return id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    @Override
    public String toString() {
        return getClass().getName() + ": [pseudo=\"" + pseudo + "\"]";
    }

}