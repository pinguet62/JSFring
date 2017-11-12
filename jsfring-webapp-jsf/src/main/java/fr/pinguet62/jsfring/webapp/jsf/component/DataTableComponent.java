package fr.pinguet62.jsfring.webapp.jsf.component;

import org.primefaces.component.datatable.DataTable;

import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.context.ExternalContext;

@FacesComponent("fr.pinguet62.jsfring.webapp.jsf.component.DataTableComponent")
public final class DataTableComponent extends DataTable {

    /**
     * Used to evaluate the {@code var} value into this component implementation.<br>
     * <ul>
     * <li>The {@code var} variable is defined during the component declaration:<br>
     * <code>&#60;p62:dataTable var="foo"&#62;</code></li>
     * <li>and used in the <code> &#60;composite:insertChildren&#62;</code> like:<br>
     * <code>&#60;p:column&#62;#&#123;foo&#125;&#60;/p:column&#62;</code></li>
     * <li>Into custom component, it's not possible to access to this variable because the component doesn't know its name:
     * <code>#&#123;foo&#125;</code></li>
     * </ul>
     * To fix the problem:
     * <ol>
     * <li>The name of variable is passed, by parameter, to the custom component:<br>
     * <code>&#60;p62:dataTable var="foo"&#62;</code></li>
     * <li>The value of variable is get into {@link ExternalContext#getRequestMap()}, to the key {@link #getVar()}.</li>
     * </ol>
     *
     * @return The evaluated {@code var} variable.
     */
    public Object getEvaluatedVar() {
        return getFacesContext().getExternalContext().getRequestMap().get(getVar());
    }

    @Override
    public String getFamily() {
        return UINamingContainer.COMPONENT_FAMILY;
    }

}