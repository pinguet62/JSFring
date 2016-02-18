package fr.pinguet62.jsfring.gui.htmlunit.datatable;

import java.util.Iterator;

/**
 * {@link Iterator} to iterate on each {@link AbstractRow row} of each page of
 * {@link AbstractDatatablePage datatable}.
 */
public final class RowIterator<T extends AbstractRow<?, ?>> implements Iterator<T> {

    private final AbstractDatatablePage<T> datatable;

    private Iterator<T> rowsOfCurrentPage;

    RowIterator(AbstractDatatablePage<T> datatable) {
        this.datatable = datatable;
        rowsOfCurrentPage = datatable.getRows().iterator();
    }

    @Override
    public boolean hasNext() {
        return rowsOfCurrentPage.hasNext() || datatable.hasNextPage();
    }

    @Override
    public T next() {
        if (rowsOfCurrentPage.hasNext())
            return rowsOfCurrentPage.next();

        // next page
        datatable.gotoNextPage();
        rowsOfCurrentPage = datatable.getRows().iterator();

        return rowsOfCurrentPage.next();
    }

}