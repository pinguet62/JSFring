package fr.pinguet62.jsfring.webservice.dto;

import fr.pinguet62.jsfring.model.sql.Right;
import lombok.Data;

/**
 * @see Right
 */
@Data
public final class RightDto {

    private String code;

    private String title;

}