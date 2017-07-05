import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";

import {AuthorizeComponent} from "./authorize.component";
import {OAuthRedirectComponent} from "./oauth-redirect.component";

import {OAuthRequestOptions} from "./oauth-request-options.service";
import {SecurityService} from "./security.service";

import {RequestOptions} from "@angular/http";

@NgModule({
    imports: [CommonModule],
    declarations: [AuthorizeComponent, OAuthRedirectComponent],
    exports: [AuthorizeComponent, OAuthRedirectComponent],
    providers: [SecurityService,
        {provide: RequestOptions, useClass: OAuthRequestOptions}
    ]
})
export class SecurityModule {}