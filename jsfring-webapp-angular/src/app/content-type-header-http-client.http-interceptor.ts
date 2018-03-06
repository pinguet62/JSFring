import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';

/** Add common header {@code Content-type} to each request. */
@Injectable()
export class ContentTypeHeaderHttpClientInterceptor implements HttpInterceptor {

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(req.clone({headers: req.headers.set('Content-type', 'application/json')}));
    }

}
