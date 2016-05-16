import { Component } from '@angular/core';

import { Column, InputText, Checkbox } from 'primeng/primeng';

import { DatatableComponent, DatatableColumnsComponent } from '../shared/datatable.component'
import { UserService } from './user.service';

@Component({
    selector: 'user',
    templateUrl: './app/user/user.component.html',
    directives: [Column, DatatableComponent, DatatableColumnsComponent, InputText],
    providers: [UserService]
})
export class UserComponent {

    selectedUser: any;

    constructor(public userService: UserService) { }

}