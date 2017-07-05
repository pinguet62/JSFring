import {Http, RequestOptionsArgs, Headers} from "@angular/http";
import {Observable} from "rxjs/Observable";
import * as Rx from "rxjs/Rx";

import {environment} from '../environment';

export abstract class CrudService<T> {

    private baseUrl: string = environment.api;

    private options: RequestOptionsArgs = { headers: new Headers({ 'Content-Type': 'application/json' }) };

    abstract getServiceSubUrl(): string;

    constructor(protected http: Http) {}

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