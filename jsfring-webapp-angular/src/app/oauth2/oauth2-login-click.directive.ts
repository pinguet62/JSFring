import {HttpParams} from '@angular/common/http';
import {Directive, HostListener} from '@angular/core';
import {environment} from '../../environments/environment';

/** @example `<button loginClick>` */
@Directive({selector: '[appOauth2LoginClick]'})
export class Oauth2LoginClickDirective {
    @HostListener('click')
    redirectToOauth2(): void {
        const paramBuilder: HttpParams = new HttpParams()
            .append('client_id', 'clientId')
            .append('response_type', 'token')
            .append('scope', 'read')
            .append('redirect_uri', `${window.location.protocol}//${window.location.host}/oauth`);
        window.location.href = `${environment.api}/oauth/authorize?${paramBuilder.toString()}`; // let popup: Window = window.open(authorizeUrl, 'Login', 'location=1');
    }
}
