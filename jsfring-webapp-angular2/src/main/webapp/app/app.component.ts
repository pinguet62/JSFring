import { Component } from '@angular/core';
import { ROUTER_DIRECTIVES, ROUTER_PROVIDERS, Routes } from '@angular/router';

import { MenuComponent } from './menu/menu.component';
import { OAuthRedirectComponent } from './security/oauth-redirect.component';
import { IndexComponent } from './index.component';
import { RightComponent } from './right/right.component';
import { ProfileComponent } from './profile/profile.component';
import { UserComponent } from './user/user.component';

@Component({
    selector: 'p62-app',
    templateUrl: './app/app.component.html',
    directives: [MenuComponent, ROUTER_DIRECTIVES],
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