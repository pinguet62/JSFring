import {HttpParams} from '@angular/common/http';
import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {SecurityService} from './security.service';

/**
 * Interceptor for OAuth 2 redirection.<br>
 * Redirect to '/' after completion.
 */
@Component({template: ''})
export class OAuth2RedirectInterceptorComponent {

    constructor(securityService: SecurityService, router: Router) {
        const fragment: string = window.location.href.split('#')[1];
        const paramParser: HttpParams = new HttpParams({fromString: fragment});
        const access_token: string = paramParser.get('access_token');
        const token_type: string = paramParser.get('token_type');
        const expires_in: number = +paramParser.get('expires_in');

        securityService.token = access_token;

        router.navigate(['/']);
    }

}
