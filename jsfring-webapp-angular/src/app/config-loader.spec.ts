import {HttpClient} from '@angular/common/http';
import {of} from 'rxjs';
import {environment} from '../environments/environment';
import {configLoaderInitializer} from './config-loader';

describe('config-loader', () => {
    const initialEnvironmentApi = environment.api;
    beforeEach(() => environment.api = 'mock');
    afterEach(() => environment.api = initialEnvironmentApi);

    it('configLoaderInitializer', async () => {
        const configValue = 'http://localhost:8080';
        let http = jasmine.createSpyObj<HttpClient>('http', ['get']);
        http.get.and.returnValue(of({api: `"${configValue}"`}));
        await configLoaderInitializer(http)();
        expect(environment.api).toEqual(configValue);
    });
});
