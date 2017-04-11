import {RequestOptionsArgs, Headers} from "@angular/http";
import {Observable} from "rxjs/Observable";
import {OAuthHttp} from "../security/oauth-http.service";
import * as Rx from "rxjs/Rx";

export abstract class CrudService<T> {

    private baseUrl: string = 'http://jsfring-webservice.herokuapp.com';

    private options: RequestOptionsArgs = { headers: new Headers({ 'Content-Type': 'application/json' }) };

    abstract getServiceSubUrl(): string;

    constructor(protected http: OAuthHttp) { }

    findAll(): Observable<T[]> {
        let targetUrl: string = this.baseUrl + this.getServiceSubUrl();
        return this.http.get(targetUrl).map(res => res.json());
    }

    create(value: T): void {
        let url: string = this.baseUrl + this.getServiceSubUrl();
        let body: string = JSON.stringify(value);
        this.http.put(url, body, this.options);
    }

    update(value: T): void {
        let url: string = this.baseUrl + this.getServiceSubUrl();
        let body: string = JSON.stringify(value);
        this.http.post(url, body, this.options);
    }

}