package fr.pinguet62.jsfring.webserivce.dto;

import fr.pinguet62.jsfring.model.sql.Right;

/** @see Right */
public final class RightDto {

    private String code;

    private String title;

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}