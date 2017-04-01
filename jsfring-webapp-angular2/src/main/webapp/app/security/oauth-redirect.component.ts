import {Component} from "@angular/core";
import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot} from "@angular/router";
import {URLSearchParams} from "@angular/http";

import {SecurityService} from "./security.service";

/** Interceptor for OAuth 2 redirection. */
@Component({
    template: ''
})
export class OAuthRedirectComponent implements CanActivate {

    constructor(private securityService: SecurityService) { }

    /**
     * Parse the URL to extract fragment who contains OAuth login result.
     * Store results on context.
     */
    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
        let fragment: string = window.location.href.split('#')[1];
        let paramParser: URLSearchParams = new URLSearchParams(fragment);

        let access_token: string = paramParser.get('access_token');
        let token_type: string = paramParser.get('token_type');
        let expires_in: number = +paramParser.get('expires_in');

        this.securityService.token = access_token;

        return true;
    }

}