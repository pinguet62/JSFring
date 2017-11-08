import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {HTTP_INTERCEPTORS} from '@angular/common/http';

import {AuthorizeComponent} from "./authorize.component";
import {OAuthRedirectComponent} from "./oauth-redirect.component";
import {SecurityService} from "./security.service";
import {OAuthHeaderHttpClientInterceptor} from "./oauth-header-http-client.http-interceptor";

@NgModule({
    imports: [CommonModule],
    declarations: [AuthorizeComponent, OAuthRedirectComponent],
    exports: [AuthorizeComponent, OAuthRedirectComponent],
    providers: [
        SecurityService,
        {provide: HTTP_INTERCEPTORS, useClass: OAuthHeaderHttpClientInterceptor, multi: true}
    ]
})
export class SecurityModule {
}
