import {Injectable} from 'angular2/core';

import {Profile} from '../dto/Profile';

@Injectable()
export class ProfileService {

    getValues(): Array<Profile> { // Mocked
        return [
            {
                id: 1,
                title: 'Profile admin',
                rights: ['RIGHT_RO', 'PROFILE_RO', 'PROFILE_RW']
            },
            {
                id: 2,
                title: 'User admin',
                rights: ['PROFILE_RO', 'USER_RO', 'USER_RW']
            }
        ];
    }

}