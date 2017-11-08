import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";

import {CrudService} from "../shared/crud.service";
import {Right} from "./right.model";

@Injectable()
export class RightService extends CrudService<Right> {

    constructor(protected http: HttpClient) {
        super(http);
    }

    protected getServiceSubUrl(): string {
        return '/right';
    }

    protected getId(value: Right): string {
        return value.code;
    }

}