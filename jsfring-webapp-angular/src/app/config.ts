import {HttpClient} from '@angular/common/http';
import {environment} from '../environments/environment';
import {tap} from 'rxjs/operators';

export function configInitialize(http: HttpClient): () => Promise<any> {
    return () => http
        .get(environment.configFile)
        .pipe(tap(config => {
            for (let prop in config)
                environment[prop] = eval(config[prop]);
        }))
        .toPromise();
}
