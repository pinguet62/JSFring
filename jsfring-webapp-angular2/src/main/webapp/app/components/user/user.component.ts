import {Component, OnInit} from 'angular2/core';

import {DataTable} from 'primeng/primeng';
import {Column} from 'primeng/primeng';

import {UserService} from '../../services/user.service';
import {User} from '../../dto/User';

@Component({
    selector: 'user',
    templateUrl: './app/components/user/user.component.html',
    directives: [DataTable, Column],
    providers: [UserService]
})
export class UserComponent implements OnInit {

    users: User[];

    constructor(private _userService: UserService) { }

    ngOnInit() {
        this.users = this._userService.getValues();
    }

}