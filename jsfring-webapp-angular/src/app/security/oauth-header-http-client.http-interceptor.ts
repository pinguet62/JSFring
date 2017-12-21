import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {SecurityService} from './security.service';

/** Add common header {@code Authorization} to each request. */
@Injectable()
export class OAuthHeaderHttpClientInterceptor implements HttpInterceptor {

    public constructor(private securityService: SecurityService) {
    }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        if (this.securityService.token)
            req = req.clone({headers: req.headers.set('Authorization', 'Bearer ' + this.securityService.token)});
        return next.handle(req);
    }

}
