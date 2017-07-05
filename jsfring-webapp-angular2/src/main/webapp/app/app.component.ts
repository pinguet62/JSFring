import {Component} from "@angular/core";
import {URLSearchParams} from "@angular/http";

import {environment} from './environment';
import {SecurityService} from "./security/security.service";

@Component({
    selector: 'p62-app',
    template: `
        <md-toolbar color="primary">
            <!-- Open menu -->
            <button (click)="sidenav.toggle()" md-icon-button>
                <md-icon>menu</md-icon>
            </button>
            
            <!-- Title -->
            <span>JSFring</span>
            
            <!-- Current user -->
            <span style="flex: 1 1 auto"></span>
            <div *ngIf="!securityService.isAuthenticated(); then notAuthenticatedBlock else authenticatedBlock"></div>
            <ng-template #notAuthenticatedBlock>
                <button md-raised-button color="accent" (click)="login()">Login</button>
            </ng-template>
            <md-menu #authenticatedMenu="mdMenu">
                <button md-menu-item>
                    <md-icon>account_circle</md-icon>
                    <span>My acount</span>
                </button>
                <button md-menu-item>
                    <md-icon>loop</md-icon>
                    <span>Change password</span>
                </button>
                <button md-menu-item>
                    <md-icon>exit_to_app</md-icon>
                    <span>Logout</span>
                </button>
            </md-menu>
            <ng-template #authenticatedBlock>
                <button md-icon-button [mdMenuTriggerFor]="authenticatedMenu">
                    <md-icon>account_circle</md-icon>
                </button>
            </ng-template>
        </md-toolbar>
        
        <md-sidenav-container>
            <!-- Left menu -->
            <md-sidenav #sidenav mode="side" [opened]="true">
                <md-nav-list>
                    <md-divider></md-divider>
                    <h3 md-subheader>Administration</h3>
                    <md-list-item [routerLink]="['/users']">
                        <md-icon md-list-icon>accessibility</md-icon>
                        <span md-line>Users</span>
                    </md-list-item>
                    <md-list-item [routerLink]="['/profiles']">
                        <md-icon md-list-icon>assignment</md-icon>
                        <span md-line>Profiles</span>
                    </md-list-item>
                    <md-list-item [routerLink]="['/rights']">
                        <md-icon md-list-icon>copyright</md-icon>
                        <span md-line>Rights</span>
                    </md-list-item>
                </md-nav-list>
            </md-sidenav>
            
            <!-- View content -->
            <router-outlet></router-outlet>
        </md-sidenav-container>`
})
export class AppComponent {

    constructor(private securityService: SecurityService) {
        securityService.unauthorized.subscribe((x: any) => console.log("toto: " + x));
    }

    login(): void {
        let loginRoute: string = '/oauth';

        let paramBuilder: URLSearchParams = new URLSearchParams();
        paramBuilder.append('client_id', 'clientId');
        paramBuilder.append('response_type', 'token');
        paramBuilder.append('scope', 'read');
        paramBuilder.append('redirect_uri', window.location.protocol + "//" + window.location.host + loginRoute);

        let oauthPath: string = '/oauth/authorize';
        let params: string = paramBuilder.toString();
        let authorizeUrl: string = environment.api + oauthPath + '?' + params;

        window.location.replace(authorizeUrl); // let popup: Window = window.open(authorizeUrl, 'Login', 'location=1');
    }

}