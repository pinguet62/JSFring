import {Injectable} from "@angular/core";
import {Http} from "@angular/http";

import {CrudService} from "../shared/crud.service";
import {Profile} from "./profile.model";

@Injectable()
export class ProfileService extends CrudService<Profile> {

    constructor(protected http: Http) {
        super(http);
    }

    getServiceSubUrl(): string {
        return '/profile';
    }

}