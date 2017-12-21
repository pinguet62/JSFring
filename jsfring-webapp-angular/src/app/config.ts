import {HttpClient} from '@angular/common/http';
import {environment} from '../environments/environment';
import 'rxjs/add/operator/do';

export function configInitialize(http: HttpClient): () => Promise<any> {
    return () => http
        .get(environment.configFile)
        .do(config => {
            for (let prop in config)
                environment[prop] = eval(config[prop]);
        })
        .toPromise();
}
