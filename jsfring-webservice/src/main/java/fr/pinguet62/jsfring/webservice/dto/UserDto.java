package fr.pinguet62.jsfring.webservice.dto;

import java.time.LocalDateTime;
import java.util.Set;

import fr.pinguet62.jsfring.model.sql.User;
import lombok.Data;

/** @see User */
@Data
public final class UserDto {

    private Boolean active;

    private String email;

    private LocalDateTime lastConnection;

    private Set<Integer> profiles;

}