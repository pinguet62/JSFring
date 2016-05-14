import { Injectable } from '@angular/core';
import { Http } from '@angular/http';

import { CrudService } from './crud.service';
import { Right } from '../dto/Right';

@Injectable()
export class RightService extends CrudService<Right> {

    constructor(protected http: Http) { super(http); }

    getServiceSubUrl(): string {
        return '/right';
    }

}