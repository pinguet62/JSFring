import { QueryList } from 'angular2/core';
import { ColumnComponent } from './column';
export declare class DataTableComponent {
    value: any[];
    columns: ColumnComponent[];
    columns2: QueryList<ColumnComponent>;
    constructor(columns: QueryList<ColumnComponent>);
}
