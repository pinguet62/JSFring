package fr.pinguet62.jsfring.webservice.dto;

import fr.pinguet62.jsfring.model.sql.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * @see User
 */
@Data
public final class UserDto {

    private Boolean active;

    private String email;

    private LocalDateTime lastConnection;

    private Set<Integer> profiles;

}