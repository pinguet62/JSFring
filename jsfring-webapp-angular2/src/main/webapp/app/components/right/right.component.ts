import {Component, OnInit} from 'angular2/core';

import {Column, InputText} from 'primeng/primeng';
import {Datatable, DatatableColumns} from '../datatable/datatable'

import {RightService} from '../../services/right.service';

@Component({
    selector: 'right',
    templateUrl: './app/components/right/right.component.html',
    directives: [Datatable, DatatableColumns, Column, InputText],
    providers: [RightService]
})
export class RightComponent {

    selectedRight: any;

    constructor(public rightService: RightService) { }

}