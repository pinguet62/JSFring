import {Component} from "@angular/core";

import {UnauthorizedObservable} from "./security/unauthorized-observable";

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
            <md-menu #connectedUserMenu="mdMenu">
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
            <button md-icon-button [mdMenuTriggerFor]="connectedUserMenu">
                <md-icon>more_vert</md-icon>
            </button>
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

    constructor(private unauthorizedObservable: UnauthorizedObservable) {
        unauthorizedObservable.observable.subscribe((x: any) => console.log("toto: " + x));
    }

}