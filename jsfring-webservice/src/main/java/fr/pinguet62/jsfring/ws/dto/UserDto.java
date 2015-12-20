package fr.pinguet62.jsfring.ws.dto;

import java.util.Date;
import java.util.Set;

import fr.pinguet62.jsfring.model.User;

/** @see User */
public final class UserDto {

    private Boolean active;

    private String email;

    private Date lastConnection;

    private String login;

    private Set<Integer> profiles;

    public Boolean getActive() {
        return active;
    }

    public String getEmail() {
        return email;
    }

    public Date getLastConnection() {
        return lastConnection;
    }

    public String getLogin() {
        return login;
    }

    public Set<Integer> getProfiles() {
        return profiles;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLastConnection(Date lastConnection) {
        this.lastConnection = lastConnection;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setProfiles(Set<Integer> profiles) {
        this.profiles = profiles;
    }

}