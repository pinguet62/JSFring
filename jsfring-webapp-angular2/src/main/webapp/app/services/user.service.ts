import {Injectable} from 'angular2/core';
import {Http} from 'angular2/src/http/http';

import {CrudService} from './crud.service';
import {User} from '../dto/User';

@Injectable()
export class UserService extends CrudService<User> {

    constructor(private http: Http) { super(http); }

    getServiceSubUrl(): string {
        return '/user';
    }

}