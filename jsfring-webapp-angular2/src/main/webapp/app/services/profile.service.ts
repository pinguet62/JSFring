import {Injectable} from 'angular2/core';
import {Http} from 'angular2/src/http/http';

import {CrudService} from './crud.service';
import {Profile} from '../dto/Profile';

@Injectable()
export class ProfileService extends CrudService<Profile> {

    constructor(protected http: Http) { super(http); }

    getServiceSubUrl(): string {
        return '/profile';
    }

}