import {Injectable} from 'angular2/core';
import {Http, Headers, RequestOptionsArgs, RequestOptions} from 'angular2/http';
import {Observable} from 'rxjs/Observable';

export abstract class CrudService<T> {

    private baseUrl: string = 'http://localhost:8080/rest';

    abstract getServiceSubUrl(): string;

    private options: RequestOptionsArgs = new RequestOptions({ headers: new Headers({ 'Content-Type': 'application/json' }) });

    constructor(protected http: Http) { }

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