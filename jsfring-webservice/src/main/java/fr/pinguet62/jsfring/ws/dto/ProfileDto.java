package fr.pinguet62.jsfring.ws.dto;

import fr.pinguet62.jsfring.model.Profile;

public class ProfileDto {

    private int id;

    private String title;

    public ProfileDto() {
    }

    public ProfileDto(Profile profile) {
        id = profile.getId();
        title = profile.getTitle();
    }

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