import { Injectable } from '@angular/core';
import { Http } from '@angular/http';

import { CrudService } from './crud.service';
import { Profile } from '../dto/Profile';

@Injectable()
export class ProfileService extends CrudService<Profile> {

    constructor(protected http: Http) { super(http); }

    getServiceSubUrl(): string {
        return '/profile';
    }

}