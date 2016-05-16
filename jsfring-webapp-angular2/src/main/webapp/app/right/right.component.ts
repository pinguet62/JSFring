import { Component } from '@angular/core';

import { Column, InputText} from 'primeng/primeng';

import { DatatableComponent, DatatableColumnsComponent } from '../shared/datatable.component'
import { RightService } from './right.service';

@Component({
    selector: 'right',
    templateUrl: './app/right/right.component.html',
    directives: [Column, DatatableComponent, DatatableColumnsComponent, InputText],
    providers: [RightService]
})
export class RightComponent {

    selectedRight: any;

    constructor(public rightService: RightService) { }

}