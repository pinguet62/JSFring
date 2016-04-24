import {Injectable} from 'angular2/core';

import {Right} from '../dto/Right';

@Injectable()
export class RightService {

    getValues(): Array<Right> { // Mocked
        return [
            {
                code: 'RIGHT_RO',
                title: 'Affichage des droits'
            },
            {
                code: 'PROFILE_RO',
                title: 'Affichage des profils'
            },
            {
                code: 'PROFILE_RW',
                title: 'Edition des profils'
            },
            {
                code: 'USER_RO',
                title: 'Affichage des utilisateurs'
            },
            {
                code: 'USER_RW',
                title: 'Edition des utilisateurs'
            }
        ];
    }

}