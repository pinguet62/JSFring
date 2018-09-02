import {HttpClient} from '@angular/common/http';
import {APP_INITIALIZER, NgModule} from '@angular/core';
import {tap} from 'rxjs/operators';
import {environment} from '../environments/environment';

export function configLoaderInitializer(http: HttpClient): () => Promise<any> {
    return () => http
        .get(environment.configFile)
        .pipe(tap(config => {
            for (const [key, value] of Object.entries(config)) {
                environment[key] = eval(value);
            }
        }))
        .toPromise();
}

@NgModule({
    providers: [
        {
            provide: APP_INITIALIZER,
            useFactory: configLoaderInitializer,
            deps: [HttpClient],
            multi: true
        },
    ]
})
export class ConfigLoaderModule {
}
