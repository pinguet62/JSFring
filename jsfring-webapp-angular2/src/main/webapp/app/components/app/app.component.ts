import { Component } from '@angular/core';
import { Routes, ROUTER_DIRECTIVES, ROUTER_PROVIDERS } from '@angular/router';

import { OAuthRedirectComponent } from './../oauth-redirect.component';
import { MenuComponent } from './../menu/menu.component';
import { RightComponent } from '../right/right.component';
import { ProfileComponent } from '../profile/profile.component';
import { UserComponent } from '../user/user.component';

@Component({
    selector: 'p62-app',
    templateUrl: './app/components/app/app.component.html',
    directives: [MenuComponent, ROUTER_DIRECTIVES],
    providers: [ROUTER_PROVIDERS]
})
@Routes([
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