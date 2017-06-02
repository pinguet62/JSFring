package fr.pinguet62.jsfring.webservice.dto;

import java.util.Set;

import fr.pinguet62.jsfring.model.sql.Profile;
import lombok.Data;

/** @see Profile */
@Data
public class ProfileDto {

    private int id;

    private Set<String> rights;

    private String title;

}