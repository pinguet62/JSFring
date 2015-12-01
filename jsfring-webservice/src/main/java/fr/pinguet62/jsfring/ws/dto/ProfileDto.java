package fr.pinguet62.jsfring.ws.dto;

import fr.pinguet62.jsfring.model.Profile;

public class ProfileDto {

    private String title;

    public ProfileDto() {}

    public ProfileDto(Profile profile) {
        title = profile.getTitle();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}