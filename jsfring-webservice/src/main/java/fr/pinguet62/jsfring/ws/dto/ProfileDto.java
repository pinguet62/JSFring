package fr.pinguet62.jsfring.ws.dto;

import fr.pinguet62.jsfring.model.Profile;

/** @see Profile */
public class ProfileDto {

    private int id;

    private String title;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}