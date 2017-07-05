import {Injectable} from "@angular/core";
import {Http} from "@angular/http";

import {CrudService} from "../shared/crud.service";
import {Right} from "./right.model";

@Injectable()
export class RightService extends CrudService<Right> {

    constructor(protected http: Http) {
        super(http);
    }

    getServiceSubUrl(): string {
        return '/right';
    }

}