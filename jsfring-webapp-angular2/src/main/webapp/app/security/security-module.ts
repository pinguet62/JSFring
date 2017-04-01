import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";

import {AuthorizeComponent} from "./authorize.component";
import {OAuthRedirectComponent} from "./oauth-redirect.component";

import {OAuthHttp} from "./oauth-http.service";
import {SecurityService} from "./security.service";
import {UnauthorizedObservable} from "./unauthorized-observable";

@NgModule({
    imports: [CommonModule],
    declarations: [AuthorizeComponent, OAuthRedirectComponent],
    exports: [AuthorizeComponent, OAuthRedirectComponent],
    providers: [OAuthHttp, SecurityService, UnauthorizedObservable]
})
export class SecurityModule {}