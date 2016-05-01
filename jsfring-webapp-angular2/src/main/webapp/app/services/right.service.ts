import {Injectable} from 'angular2/core';
import {Http} from 'angular2/src/http/http';

import {CrudService} from './crud.service';
import {Right} from '../dto/Right';

@Injectable()
export class RightService extends CrudService<Right> {

    constructor(private http: Http) { super(http); }

    getServiceSubUrl(): string {
        return '/right';
    }

}