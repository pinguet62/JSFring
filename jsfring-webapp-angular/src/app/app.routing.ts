import {Routes} from "@angular/router";

import {IndexComponent} from "./index.component";
import {OAuthRedirectComponent} from "./security/oauth-redirect.component";
import {RightComponent} from "./right/right.component";
import {ProfileComponent} from "./profile/profile.component";
import {UserComponent} from "./user/user.component";

export const routes: Routes = [
    { path: '', component: IndexComponent },
    {
        // OAuth redirect after login
        // URL: %REDIRECT_URI%#access_token=:access_token&token_type=:token_type&expires_in=:expires_in'
        // %REDIRECT_URI% = %HOST%:%PORT%/oauth
        path: 'oauth', component: OAuthRedirectComponent
    },
    { path: 'rights', component: RightComponent },
    { path: 'profiles', component: ProfileComponent },
    { path: 'users', component: UserComponent }
];