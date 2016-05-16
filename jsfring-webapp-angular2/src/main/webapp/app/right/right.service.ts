import { Injectable } from '@angular/core';

import { CrudService } from '../shared/crud.service';
import { OAuthHttp } from '../security/oauth-http.service';
import { Right } from './right.model';

@Injectable()
export class RightService extends CrudService<Right> {

    constructor(protected http: OAuthHttp) { super(http); }

    getServiceSubUrl(): string {
        return '/right';
    }

}