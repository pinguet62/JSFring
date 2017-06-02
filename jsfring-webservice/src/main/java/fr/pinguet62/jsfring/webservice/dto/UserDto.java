package fr.pinguet62.jsfring.webservice.dto;

import java.util.Date;
import java.util.Set;

import fr.pinguet62.jsfring.model.sql.User;
import lombok.Data;

/** @see User */
@Data
public final class UserDto {

    private Boolean active;

    private String email;

    private Date lastConnection;

    private Set<Integer> profiles;

}