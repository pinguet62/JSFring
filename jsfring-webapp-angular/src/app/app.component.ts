import {Component} from '@angular/core';
import {MatSnackBar} from '@angular/material';
import {SecurityService} from './oauth2';
import {WebSocketService} from './websocket';

@Component({
    selector: 'app-root',
    template: `
        <mat-toolbar color="primary">
            <!-- Open menu -->
            <button (click)="sidenav.toggle()" mat-icon-button>
                <mat-icon>menu</mat-icon>
            </button>

            <!-- Title -->
            <span>JSFring</span>

            <!-- Current user -->
            <span style="flex: 1 1 auto"></span>
            <div *ngIf="!securityService.isAuthenticated(); then notAuthenticatedBlock else authenticatedBlock"></div>
            <ng-template #notAuthenticatedBlock>
                <button mat-raised-button color="accent" appOauth2LoginClick>Login</button>
            </ng-template>
            <mat-menu #authenticatedMenu="matMenu">
                <button mat-menu-item>
                    <mat-icon>account_circle</mat-icon>
                    <span>My acount</span>
                </button>
                <button mat-menu-item>
                    <mat-icon>loop</mat-icon>
                    <span>Change password</span>
                </button>
                <button mat-menu-item>
                    <mat-icon>exit_to_app</mat-icon>
                    <span>Logout</span>
                </button>
            </mat-menu>
            <ng-template #authenticatedBlock>
                <button mat-icon-button [matMenuTriggerFor]="authenticatedMenu">
                    <mat-icon>account_circle</mat-icon>
                </button>
            </ng-template>
        </mat-toolbar>

        <mat-sidenav-container fullscreen style="top: 64px;">
            <!-- Left menu -->
            <mat-sidenav #sidenav mode="side" opened="true">
                <mat-nav-list>
                    <mat-divider></mat-divider>
                    <h3 mat-subheader>Administration</h3>
                    <mat-list-item [routerLink]="['/users']">
                        <mat-icon mat-list-icon>accessibility</mat-icon>
                        <span mat-line>Users</span>
                    </mat-list-item>
                    <mat-list-item [routerLink]="['/profiles']">
                        <mat-icon mat-list-icon>assignment</mat-icon>
                        <span mat-line>Profiles</span>
                    </mat-list-item>
                    <mat-list-item [routerLink]="['/rights']">
                        <mat-icon mat-list-icon>copyright</mat-icon>
                        <span mat-line>Rights</span>
                    </mat-list-item>
                </mat-nav-list>
            </mat-sidenav>

            <!-- View content -->
            <router-outlet></router-outlet>
        </mat-sidenav-container>`
})
export class AppComponent {

    constructor(
        snackBar: MatSnackBar,
        websocketService: WebSocketService,
        public securityService: SecurityService,
    ) {
        websocketService.userRightsUpdated.subscribe(message =>
            snackBar.open(message)
        );
    }

}
