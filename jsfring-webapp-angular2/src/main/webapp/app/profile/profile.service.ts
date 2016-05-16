import { Injectable } from '@angular/core';

import { CrudService } from '../shared/crud.service';
import { OAuthHttp } from '../security/oauth-http.service';
import { Profile } from './profile.model';

@Injectable()
export class ProfileService extends CrudService<Profile> {

    constructor(protected http: OAuthHttp) { super(http); }

    getServiceSubUrl(): string {
        return '/profile';
    }

}