import { Component } from '@angular/core';
import { OnActivate, RouteSegment, RouteTree } from '@angular/router';
import { URLSearchParams } from '@angular/http';

/** Interceptor for OAuth 2 redirection. */
@Component({})
export class OAuthRedirectComponent implements OnActivate {

    /**
      * Parse the URL to extract fragment who contains OAuth login result.
      * Store results on context.
      */
    routerOnActivate(curr: RouteSegment, prev?: RouteSegment, currTree?: RouteTree, prevTree?: RouteTree): void {
        let fragment: string = window.location.href.split('#')[1];
        let paramParser: URLSearchParams = new URLSearchParams(fragment);
        let access_token: string = paramParser.get('access_token');
        let token_type: string = paramParser.get('token_type');
        let expires_in: number = +paramParser.get('expires_in');
    }

}