import { Injectable } from '@angular/core';

import { CrudService } from './crud.service';
import { OAuthHttpClient } from './../OAuthHttpClient';
import { User } from '../dto/User';

@Injectable()
export class UserService extends CrudService<User> {

    constructor(protected http: OAuthHttpClient) { super(http); }

    getServiceSubUrl(): string {
        return '/user';
    }

}