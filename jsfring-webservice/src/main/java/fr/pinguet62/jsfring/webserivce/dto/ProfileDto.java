package fr.pinguet62.jsfring.webserivce.dto;

import java.util.Set;

import fr.pinguet62.jsfring.model.sql.Profile;

/** @see Profile */
public class ProfileDto {

    private int id;

    private Set<String> rights;

    private String title;

    public int getId() {
        return id;
    }

    public Set<String> getRights() {
        return rights;
    }

    public String getTitle() {
        return title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRights(Set<String> rights) {
        this.rights = rights;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}