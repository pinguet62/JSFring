import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import {CrudService} from '../shared/crud.service';
import {User} from './user.model';

@Injectable()
export class UserService extends CrudService<User> {

    constructor(protected http: HttpClient) {
        super(http);
    }

    protected getServiceSubUrl(): string {
        return '/user';
    }

    protected getId(value: User): string {
        return value.email;
    }

    findAll(): Observable<User[]> {
        return super.findAll().map((users: User[]) => {
            for (let user of users)
                if (user.lastConnection !== null)
                    user.lastConnection = new Date(user.lastConnection);
            return users;
        });
    }

}
