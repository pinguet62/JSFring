package fr.pinguet62.jsfring.ws.dto;

import java.util.Date;

import fr.pinguet62.jsfring.model.User;

/** @see User */
public final class UserDto {

    private String email;

    private Date lastConnection;

    private String login;

    public String getEmail() {
        return email;
    }

    public Date getLastConnection() {
        return lastConnection;
    }

    public String getLogin() {
        return login;
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

}