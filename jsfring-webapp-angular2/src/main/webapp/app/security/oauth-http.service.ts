import { Observable } from 'rxjs/Observable';

import { Injectable } from '@angular/core';
import { Headers, Http, RequestOptions, RequestOptionsArgs, Response } from '@angular/http';

import { SecurityService } from './security.service';

/** Proxy of {@link Http}, who add OAuth 2 {@code "Authorization"} header to each requests. */
@Injectable()
export class OAuthHttp {

    constructor(protected http: Http, private securityService: SecurityService) { }

    /**
     * Add "Authorization" key to header, if not present.
     * @param options The {@link RequestOptionsArgs} to initialize.
     * @return The initialized {@link RequestOptionsArgs}.
     *         The new {@link RequestOptionsArgs} if not present.
     */
    private addAuthorizationHeader(options: RequestOptionsArgs): RequestOptionsArgs {
        if (options == null)
            options = new RequestOptions();
        if (options.headers == null)
            options.headers = new Headers();
        if (!options.headers.has('Authorization'))
            options.headers.append('Authorization', 'Bearer ' + this.securityService.token);
        return options;
    }

    // Proxyfied methods

    get(url: string, options?: RequestOptionsArgs): Observable<Response> {
        options = this.addAuthorizationHeader(options);
        return this.http.get(url, options);
    }

    post(url: string, body: string, options?: RequestOptionsArgs): Observable<Response> {
        options = this.addAuthorizationHeader(options);
        return this.http.post(url, body, options);
    }

    put(url: string, body: string, options?: RequestOptionsArgs): Observable<Response> {
        options = this.addAuthorizationHeader(options);
        return this.http.put(url, body, options);
    }

    delete(url: string, options?: RequestOptionsArgs): Observable<Response> {
        options = this.addAuthorizationHeader(options);
        return this.http.delete(url, options);
    }

    patch(url: string, body: string, options?: RequestOptionsArgs): Observable<Response> {
        options = this.addAuthorizationHeader(options);
        return this.http.patch(url, body, options);
    }

    head(url: string, options?: RequestOptionsArgs): Observable<Response> {
        options = this.addAuthorizationHeader(options);
        return this.http.head(url, options);
    }

}