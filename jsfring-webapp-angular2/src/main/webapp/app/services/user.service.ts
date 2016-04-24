import {Injectable} from 'angular2/core';

import {User} from '../dto/User';

@Injectable()
export class UserService {

    getValues(): Array<User> { // Mocked
        return [
            {
                login: 'super admin',
                password: 'Azerty1!',
                email: 'admin@domain.fr',
                active: true,
                lastConnection: null,//'2015-11-14 13:45:41',
                profiles: [1, 2]
            },
            {
                login: 'admin profile',
                password: 'Azerty1!',
                email: 'admin_profile@domain.fr',
                active: true,
                lastConnection: null,
                profiles: [1]
            },
            {
                login: 'admin user',
                password: 'Azerty1!',
                email: 'admin_user@domain.fr',
                active: true,
                lastConnection: null,
                profiles: [2]
            }
        ];
    }

}