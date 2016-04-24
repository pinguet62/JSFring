import {Component, Input} from 'angular2/core';

@Component({
    selector: 'p62-column',
    template: ''
})
export class ColumnComponent {

    @Input() header: string;
    @Input() field: string;

}