import {HTTP_INTERCEPTORS} from '@angular/common/http';
import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {OAuth2HeaderHttpInterceptor} from './oauth2-header-http-client.http-interceptor';
import {Oauth2LoginClickDirective} from './oauth2-login-click.directive';
import {OAuth2RedirectInterceptorComponent} from './oauth2-redirect-interceptor.component';
import {SecurityService} from './security.service';

@NgModule({
    imports: [RouterModule.forRoot([{path: 'oauth', component: OAuth2RedirectInterceptorComponent}])],
    providers: [
        SecurityService,
        {provide: HTTP_INTERCEPTORS, useClass: OAuth2HeaderHttpInterceptor, multi: true}
    ],
    declarations: [Oauth2LoginClickDirective, OAuth2RedirectInterceptorComponent],
    exports: [Oauth2LoginClickDirective]
})
export class OAuth2Module {
}
