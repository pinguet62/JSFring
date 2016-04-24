import {Component, OnInit} from 'angular2/core';

import {UserService} from '../../services/user.service';

import {MenuComponent} from '../menu/menu.component';
import {DataTableComponent} from '../dataTable/dataTable';
import {ColumnComponent} from '../dataTable/column';

import {User} from '../../dto/User';

@Component({
    selector: 'user',
    templateUrl: './app/components/user/user.component.html',
    directives: [MenuComponent, DataTableComponent, ColumnComponent],
    providers: [UserService]
})
export class UserComponent implements OnInit {

    users: User[];

    constructor(private _userService: UserService) { }

    ngOnInit() {
        this.users = this._userService.getValues();
    }

}