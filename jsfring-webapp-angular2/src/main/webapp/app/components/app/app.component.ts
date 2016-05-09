import { Component } from 'angular2/core';
import { RouteConfig, ROUTER_DIRECTIVES } from 'angular2/router';

import { OAuthRedirectComponent } from './../oauth-redirect.component';
import { MenuComponent } from './../menu/menu.component';
import { RightComponent } from '../right/right.component';
import { ProfileComponent } from '../profile/profile.component';
import { UserComponent } from '../user/user.component';

@Component({
    selector: 'app',
    templateUrl: './app/components/app/app.component.html',
    directives: [MenuComponent, ROUTER_DIRECTIVES]
})
@RouteConfig([
    {
        // OAuth redirect after login
        // URL: %REDIRECT_URI%#access_token=:access_token&token_type=:token_type&expires_in=:expires_in'
        // %REDIRECT_URI% = %HOST%:%PORT%/oauth
        path: '/oauth',
        component: OAuthRedirectComponent
    },
    {
        path: '/rights',
        name: 'Rights',
        component: RightComponent
    },
    {
        path: '/profiles',
        name: 'Profiles',
        component: ProfileComponent
    },
    {
        path: '/users',
        name: 'Users',
        component: UserComponent
    }
])
export class AppComponent { }