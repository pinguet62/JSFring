import {Component, Input, Query, QueryList} from 'angular2/core';
import {ColumnComponent} from './column';

@Component({
    selector: 'p62-dataTable',
    templateUrl: './app/components/dataTable/dataTable.html'
})
export class DataTableComponent {

    @Input() value: any[];

    columns: ColumnComponent[];
    columns2: QueryList<ColumnComponent>;

    constructor( @Query(ColumnComponent) columns: QueryList<ColumnComponent>) {
        this.columns2 = columns;
        columns.changes.subscribe(_ => { this.columns = columns.toArray(); });
    }

}