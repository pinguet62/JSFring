import { Injectable } from '@angular/core';

/** Store informations about user's security information. */
@Injectable()
export class SecurityService {

    token: string;
    roles: Array<string>

    isAuthenticated(): boolean {
        return this.token != null;
    }

    // TODO: Webservice call "oauth/authorities
    getRoles(): Array<string> {
        return ['role1', 'role2'];
    }

}