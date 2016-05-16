import { Observable } from 'rxjs/Observable';

import { Injectable } from '@angular/core';
import { RequestOptions, RequestOptionsArgs, Headers } from '@angular/http';

import { OAuthHttpClient } from './../OAuthHttpClient';

export abstract class CrudService<T> {

    private baseUrl: string = 'http://jsfring-webservice.herokuapp.com/rest';

    abstract getServiceSubUrl(): string;

    private options: RequestOptionsArgs = new RequestOptions({ headers: new Headers({ 'Content-Type': 'application/json' }) });

    constructor(protected http: OAuthHttpClient) { }

    findAll(): Observable<Array<T>> {
        let targetUrl: string = this.baseUrl + this.getServiceSubUrl();
        return this.http.get(targetUrl).map(res => res.json());
    }

    create(value: T) {
        let url: string = this.baseUrl + this.getServiceSubUrl();
        let body: string = JSON.stringify(value);
        this.http.put(url, body, this.options);
    }

    update(value: T) {
        let url: string = this.baseUrl + this.getServiceSubUrl();
        let body: string = JSON.stringify(value);
        this.http.post(url, body, this.options).toPromise();
    }

}