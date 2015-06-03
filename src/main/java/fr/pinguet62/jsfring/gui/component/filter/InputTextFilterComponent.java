package fr.pinguet62.jsfring.gui.component.filter;

import javax.faces.component.FacesComponent;

import org.primefaces.component.inputtext.InputText;

@FacesComponent(value = "inputTextFilter")
public final class InputTextFilterComponent extends InputText {

    private boolean ignoreValidation;

    public boolean isIgnoreValidation() {
        return ignoreValidation;
    }

    public void setIgnoreValidation(boolean ignoreValidation) {
        this.ignoreValidation = ignoreValidation;
    }

}
