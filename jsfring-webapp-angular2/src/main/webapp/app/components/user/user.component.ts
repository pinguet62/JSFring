import {Component, OnInit} from 'angular2/core';

import {Column, InputText, Checkbox} from 'primeng/primeng';
import {Datatable, DatatableColumns} from '../datatable/datatable'

import {UserService} from '../../services/user.service';

@Component({
    selector: 'user',
    templateUrl: './app/components/user/user.component.html',
    directives: [Datatable, DatatableColumns, Column, InputText, Checkbox],
    providers: [UserService]
})
export class UserComponent {

    selectedUser: any;

    constructor(public userService: UserService) { }

}