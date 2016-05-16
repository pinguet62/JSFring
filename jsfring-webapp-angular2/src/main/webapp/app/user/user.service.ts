import { Injectable } from '@angular/core';

import { CrudService } from '../shared/crud.service';
import { OAuthHttp } from '../security/oauth-http.service';
import { User } from './user.model';

@Injectable()
export class UserService extends CrudService<User> {

    constructor(protected http: OAuthHttp) { super(http); }

    getServiceSubUrl(): string {
        return '/user';
    }

}