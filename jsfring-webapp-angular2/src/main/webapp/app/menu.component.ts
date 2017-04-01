import {Component} from "@angular/core";
import {URLSearchParams} from "@angular/http";

@Component({
    selector: 'p62-menu',
    template: `
        <p-menubar>
            <ul>
                <li><a [routerLink]="['/']" data-icon="fa-home"><span>Index</span></a></li>
                <li>
                    <a data-icon="fa-gear"><span>Administration</span></a>
                    <ul>
                        <li><a [routerLink]="['/users']" data-icon="fa-user"><span>Users</span></a></li>
                        <li><a [routerLink]="['/profiles']" data-icon="fa-flag"><span>Profiles</span></a></li>
                        <li><a [routerLink]="['/rights']" data-icon="fa-flag"><span>Rights</span></a></li>
                    </ul>
                </li>
        
                <li style="float: right;">
                    <!-- Anonymous -->
                    <template [ngIf]="!isLogged()">
                        <a (click)="login()" data-icon="fa-edit">Login...</a>
                    </template>
                    <!-- Logged -->
                    <template [ngIf]="isLogged()">
                        <a data-icon="fa-edit">Welcome!</a>
                        <ul>
                            <li><a><span>Login</span></a></li>
                            <li><a><span>My account</span></a></li>
                            <li><a><span>Change password</span></a></li>
                            <li><a><span>Logout</span></a></li>
                        </ul>
                    </template>
                </li>
            </ul>
        </p-menubar>`
})
export class MenuComponent {

    isLogged(): boolean {
        return localStorage.getItem('oauth_token') != null;
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