import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';

export abstract class CrudService<T> {

    private baseUrl: string = environment.api;

    protected abstract getServiceSubUrl(): string;

    protected abstract getId(value: T): string;

    constructor(protected http: HttpClient) {
    }

    findAll(): Observable<T[]> {
        let targetUrl: string = this.baseUrl + this.getServiceSubUrl();
        return this.http.get<T[]>(targetUrl);
    }

    create(value: T): Observable<T> {
        let url: string = this.baseUrl + this.getServiceSubUrl();
        let body: string = JSON.stringify(value);
        return this.http.put<T>(url, body);
    }

    update(value: T): Observable<T> {
        let url: string = this.baseUrl + this.getServiceSubUrl()/* + '/' + this.getId(value)*/;
        let body: string = JSON.stringify(value);
        return this.http.post<T>(url, body);
    }

    delete(value: T): Observable<T> {
        let url: string = this.baseUrl + this.getServiceSubUrl() + '/' + this.getId(value);
        return this.http.delete<T>(url);
    }

}
