import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";

import {AuthorizeComponent} from "./authorize.component";
import {OAuthRedirectComponent} from "./oauth-redirect.component";

import {OAuthHttp, OAuthRequestOptions} from "./oauth-http.service";
import {SecurityService} from "./security.service";
import {UnauthorizedObservable} from "./unauthorized-observable";

import {RequestOptions} from "@angular/http";

@NgModule({
    imports: [CommonModule],
    declarations: [AuthorizeComponent, OAuthRedirectComponent],
    exports: [AuthorizeComponent, OAuthRedirectComponent],
    providers: [OAuthHttp, SecurityService, UnauthorizedObservable,
        {provide: RequestOptions, useClass: OAuthRequestOptions}
    ]
})
export class SecurityModule {}