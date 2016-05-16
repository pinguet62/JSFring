import { Injectable } from '@angular/core';

import { CrudService } from './crud.service';
import { OAuthHttpClient } from './../OAuthHttpClient';
import { Profile } from '../dto/Profile';

@Injectable()
export class ProfileService extends CrudService<Profile> {

    constructor(protected http: OAuthHttpClient) { super(http); }

    getServiceSubUrl(): string {
        return '/profile';
    }

}