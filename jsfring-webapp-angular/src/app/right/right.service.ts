import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {CrudService} from '../shared/crud.service';
import {Right} from './right.model';

@Injectable()
export class RightService extends CrudService<Right> {

    constructor(protected http: HttpClient) {
        super(http, '/right');
    }

    protected getId(value: Right): string {
        return value.code;
    }

}
