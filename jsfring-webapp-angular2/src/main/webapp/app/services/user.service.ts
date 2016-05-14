import { Injectable } from '@angular/core';
import { Http } from '@angular/http';

import { CrudService } from './crud.service';
import { User } from '../dto/User';

@Injectable()
export class UserService extends CrudService<User> {

    constructor(protected http: Http) { super(http); }

    getServiceSubUrl(): string {
        return '/user';
    }

}