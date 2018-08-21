import {HttpClient} from '@angular/common/http';
import {environment} from '../environments/environment';
import {tap} from 'rxjs/operators';

export function configInitialize(http: HttpClient): () => Promise<any> {
    return () => http
        .get(environment.configFile)
        .pipe(tap(config => {
            for (const [key, value] of Object.entries(config)) {
                environment[key] = eval(value);
            }
        }))
        .toPromise();
}
