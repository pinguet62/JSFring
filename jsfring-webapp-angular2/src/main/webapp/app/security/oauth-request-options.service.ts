import {Injectable} from "@angular/core";
import {Headers, RequestOptions} from "@angular/http";

import {SecurityService} from "./security.service";

@Injectable()
export class OAuthRequestOptions extends RequestOptions {

    public constructor(securityService: SecurityService) {
        super({ headers: new Headers() });

        securityService.onConnect.subscribe(() => {
            console.log("Adding OAuth token to HTTP headers");
            this.headers.append('Authorization', 'Bearer ' + securityService.token);
        });
    }

}