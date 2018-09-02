import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {CrudService} from '../shared/crud.service';
import {Profile} from './profile.model';

@Injectable()
export class ProfileService extends CrudService<Profile> {

    constructor(protected http: HttpClient) {
        super(http, '/profile');
    }

    protected getId(value: Profile): string {
        return value.id.toString();
    }

}
