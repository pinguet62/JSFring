package fr.pinguet62.jsfring.gui.component;

import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;

import org.primefaces.component.datatable.DataTable;

@FacesComponent(value = "dataTable")
public final class DataTableComponent extends DataTable {

    @Override
    public String getFamily() {
        return UINamingContainer.COMPONENT_FAMILY;
    }

}
