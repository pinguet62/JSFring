import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {HttpParams} from '@angular/common/http';
import {SecurityService} from './security.service';

/**
 * Interceptor for OAuth 2 redirection.<br>
 * Redirect to '/index' after completion.
 */
@Component({
    template: ''
})
export class OAuthRedirectComponent {

    constructor(securityService: SecurityService, router: Router) {
        console.log('Parsing URL to get OAuth information: ' + window.location.href);
        const fragment: string = window.location.href.split('#')[1];
        const paramParser: HttpParams = new HttpParams({fromString: fragment});
        const access_token: string = paramParser.get('access_token');
        const token_type: string = paramParser.get('token_type');
        const expires_in: number = +paramParser.get('expires_in');

        console.log('OAuth token: ' + access_token);
        securityService.token = access_token;
        securityService.onConnect.emit(null);

        console.log('Redirect to index');
        router.navigate(['/']);
    }

}
