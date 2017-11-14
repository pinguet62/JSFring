import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";

import {CrudService} from "../shared/crud.service";
import {Profile} from "./profile.model";

@Injectable()
export class ProfileService extends CrudService<Profile> {

    constructor(protected http: HttpClient) {
        super(http);
    }

    protected getServiceSubUrl(): string {
        return '/profile';
    }

    protected getId(value: Profile): string {
        return value.id.toString();
    }

}