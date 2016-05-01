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

    values: Array<User>;

    constructor(private userService: UserService) { }

    ngOnInit() {
        this.userService.findAll().subscribe(res => this.values = res);
    }

}