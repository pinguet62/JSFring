import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';

export abstract class CrudService<T> {

    private readonly baseUrl: string;

    protected constructor(protected http: HttpClient, resourceUrl: string) {
        this.baseUrl = environment.api + resourceUrl;
    }

    protected abstract getId(value: T): string;

    findAll(): Observable<T[]> {
        return this.http.get<T[]>(this.baseUrl);
    }

    create(value: T): Observable<T> {
        const body: string = JSON.stringify(value);
        return this.http.put<T>(this.baseUrl, body);
    }

    update(value: T): Observable<T> {
        const body: string = JSON.stringify(value);
        return this.http.post<T>(this.baseUrl /* + '/' + this.getId(value)*/, body);
    }

    delete(value: T): Observable<T> {
        return this.http.delete<T>(`${this.baseUrl}/${this.getId(value)}`);
    }

}
