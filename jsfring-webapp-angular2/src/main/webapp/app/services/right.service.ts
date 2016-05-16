import { Injectable } from '@angular/core';

import { CrudService } from './crud.service';
import { OAuthHttpClient } from './../OAuthHttpClient';
import { Right } from '../dto/Right';

@Injectable()
export class RightService extends CrudService<Right> {

    constructor(protected http: OAuthHttpClient) { super(http); }

    getServiceSubUrl(): string {
        return '/right';
    }

}