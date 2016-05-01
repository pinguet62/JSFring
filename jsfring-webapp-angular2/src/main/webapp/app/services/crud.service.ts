import {Injectable} from 'angular2/core';
import {Http} from 'angular2/src/http/http';

export abstract class CrudService<T> {

    private baseUrl: string = 'http://localhost:8080/rest';

    abstract getServiceSubUrl(): string;

    constructor(private http: Http) { }

    findAll(): Observable<Array<T>> {
        var targetUrl: string = this.baseUrl + this.getServiceSubUrl();
        return this.http.get(targetUrl).map(res => res.json());
    }

}