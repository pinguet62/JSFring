import {Component, ContentChild, ContentChildren, EventEmitter, Input, OnInit, Output, QueryList} from '@angular/core';
import {Column} from 'primeng/primeng';
import {CrudService} from './crud.service';

@Component({
    selector: 'p62-datatable-dialog',
    template: '<ng-content></ng-content>'
})
export class DatatableDialogComponent {
}

@Component({
    selector: 'p62-datatable-columns',
    template: ''
})
export class DatatableColumnsComponent {

    @ContentChildren(Column) columns: QueryList<Column>;

}

/**
 * Wrapper for {@link primeng/primeng/DataTable}.
 * @param service The CRUD service.
 * @param selectedValue The selected value.
 * @param <p62-datatable-columns> The {@link primeng/primeng/Column}s definition.
 * @param <p62-datatable-dialog> The {@link primeng/primeng/Dialog}s content.
 */
@Component({
    selector: 'p62-datatable',
    template: `
        <p-dataTable [value]="values" selectionMode="single" [(selection)]="selectedValue" (onRowSelect)="onRowSelect($event.data)">
            <p-column *ngFor="let column of datatableColumns.columns"
                      [field]="column.field"
                      [hidden]="column.hidden"
                      [sortable]="column.sortable"
                      [filter]="column.filter" [filterMatchMode]="column.filterMatchMode"
                      [header]="column.header" [footer]="column.footer"></p-column>
        </p-dataTable>

        <!-- TODO modal & z-index -->
        <!-- TODO width & responsive -->
        <p-dialog header="Show/Update/Create" [(visible)]="displayDialog" [modal]="true" [width]="500" [responsive]="true">
            <ng-content select="p62-datatable-dialog"></ng-content>
            <footer>
                <button pButton type="button" (click)="save()" icon="fa-check" label="Save"></button>
                <button pButton type="button" (click)="delete()" icon="fa-close" label="Delete"></button>
            </footer>
        </p-dialog>`
})
export class DatatableComponent implements OnInit {

    @ContentChild(DatatableColumnsComponent) datatableColumns: DatatableColumnsComponent;

    /** {@link CrudService} used to CRUD operations: list, create, update and delete items. */
    @Input() service: CrudService<any>;

    values: Array<any>;

    /** The current value: creation, modification or deletion. */
    @Input() selectedValue: any;
    @Output() selectedValueChange: EventEmitter<any> = new EventEmitter();

    displayDialog: boolean;

    /** Distinguishes the creation and modification, because two operations use the same dialog. */
    isCreation: boolean;

    ngOnInit() {
        this.refresh();
    }

    refresh() {
        this.service.findAll().subscribe(res => this.values = res);
    }

    onRowSelect(value: any) {
        this.selectedValueChange.emit(value);
        this.selectedValue = this.clone(value);
        this.displayDialog = true;
    }

    /**
     * Clone the object.
     * @param src The source object to clone.
     * @return The new cloned object.
     */
    private clone(src: any): any {
        let tgt = new (<any>src.constructor)();
        for (let prop in src)
            tgt[prop] = src[prop];
        return tgt;
    }

    /**
     * Click on "Save" button of Dialog.<br>
     * Call the create() or update() method, depending on whether it is a creation or a modification.
     */
    save() {
        this.isCreation ? this.create() : this.update();
    }

    private create() {
        this.service.create(this.selectedValue).subscribe(_ => this.refresh());
        this.displayDialog = false;
    }

    private update() {
        this.service.update(this.selectedValue).subscribe(_ => this.refresh());
        this.displayDialog = false;
    }

    /** Click on "Delete" button of Dialog. */
    delete() {
        this.service.delete(this.selectedValue).subscribe(_ => this.refresh());
        this.displayDialog = false;
    }

}
