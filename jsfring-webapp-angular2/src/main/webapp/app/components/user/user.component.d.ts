import { OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { User } from '../../dto/User';
export declare class UserComponent implements OnInit {
    private _userService;
    users: User[];
    constructor(_userService: UserService);
    ngOnInit(): void;
}
