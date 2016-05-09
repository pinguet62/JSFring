import { Component } from '@angular/core';
import { ROUTER_DIRECTIVES } from '@angular/router';
import { URLSearchParams } from '@angular/http';

import { Menubar } from 'primeng/primeng';

@Component({
    selector: 'p62-menu',
    templateUrl: './app/components/menu/menu.component.html',
    directives: [Menubar, ROUTER_DIRECTIVES]
})
export class MenuComponent {

    isLogged(): boolean {
        return localStorage.getItem('oauth_token');
    }

    login(): void {
        let loginRoute: string = '/oauth';

        let paramBuilder: URLSearchParams = new URLSearchParams();
        paramBuilder.append('client_id', 'clientId');
        paramBuilder.append('response_type', 'token');
        paramBuilder.append('scope', 'read');
        paramBuilder.append('redirect_uri', window.location.protocol + "//" + window.location.host + loginRoute);

        let oauthServer: string = 'http://jsfring-webservice.herokuapp.com';
        let oauthPath: string = '/oauth/authorize';
        let params: string = paramBuilder.toString();
        let authorizeUrl: string = oauthServer + oauthPath + '?' + params;

        window.location.replace(authorizeUrl);
    }

}