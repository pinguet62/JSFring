import { Component } from '@angular/core';
import { ROUTER_DIRECTIVES, ROUTER_PROVIDERS, Routes } from '@angular/router';

import { MdButton } from '@angular2-material/button';
import { MdIcon, MdIconRegistry } from '@angular2-material/icon';
import { MdList, MdListItem } from '@angular2-material/list';
import { MdSidenav, MdSidenavLayout } from '@angular2-material/sidenav';
import { MdToolbar } from '@angular2-material/toolbar';

import { MenuComponent } from './menu/menu.component';
import { OAuthRedirectComponent } from './security/oauth-redirect.component';
import { IndexComponent } from './index.component';
import { RightComponent } from './right/right.component';
import { ProfileComponent } from './profile/profile.component';
import { UserComponent } from './user/user.component';

@Component({
    selector: 'p62-app',
    templateUrl: './app/app.component.html',
    directives: [MenuComponent, ROUTER_DIRECTIVES, MdButton, MdIcon, MdList, MdListItem, MdSidenav, MdSidenavLayout, MdToolbar],
	viewProviders: [MdIconRegistry],
    providers: [ROUTER_PROVIDERS]
})
@Routes([
    {
        path: '/',
        component: IndexComponent
    },
    {
        // OAuth redirect after login
        // URL: %REDIRECT_URI%#access_token=:access_token&token_type=:token_type&expires_in=:expires_in'
        // %REDIRECT_URI% = %HOST%:%PORT%/oauth
        path: '/oauth',
        component: OAuthRedirectComponent
    },
    {
        path: '/rights',
        component: RightComponent
    },
    {
        path: '/profiles',
        component: ProfileComponent
    },
    {
        path: '/users',
        component: UserComponent
    }
])
export class AppComponent { }