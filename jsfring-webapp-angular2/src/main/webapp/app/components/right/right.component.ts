import {Component, OnInit} from 'angular2/core';

import {RightService} from '../../services/right.service';

import {MenuComponent} from '../menu/menu.component';
import {DataTableComponent} from '../dataTable/dataTable';
import {ColumnComponent} from '../dataTable/column';

import {Right} from '../../dto/Right';

@Component({
    selector: 'right',
    templateUrl: './app/components/right/right.component.html',
    directives: [MenuComponent, DataTableComponent, ColumnComponent],
    providers: [RightService]
})
export class RightComponent implements OnInit {

    rights: Right[];

    constructor(private _rightService: RightService) { }

    ngOnInit() {
        this.rights = this._rightService.getValues();
    }

}