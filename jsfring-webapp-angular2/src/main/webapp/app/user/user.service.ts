import {Injectable} from "@angular/core";
import {Observable} from "rxjs";

import {CrudService} from "../shared/crud.service";
import {OAuthHttp} from "../security/oauth-http.service";
import {User} from "./user.model";

@Injectable()
export class UserService extends CrudService<User> {

    constructor(protected http: OAuthHttp) {
        super(http);
    }

    getServiceSubUrl(): string {
        return '/user';
    }

    findAll(): Observable<User[]> {
        let initialObservable: Observable<User[]> = super.findAll();

        return initialObservable.map((users: User[]) => {
            for (let user of users)
                if (user.lastConnection !== null)
                    user.lastConnection = new Date(user.lastConnection);
            return users;
        });
    }

}