import { bootstrap } from '@angular/platform-browser-dynamic';
import { provide } from '@angular/core';
import { APP_BASE_HREF } from '@angular/common';
import { ROUTER_PROVIDERS } from '@angular/router';
import { HTTP_PROVIDERS } from '@angular/http';
import 'rxjs/Rx'; // Observable methods

import { AppComponent } from './components/app/app.component';
import { SecurityService } from './components/security.component';
import { OAuthHttpClient } from './OAuthHttpClient';

bootstrap(AppComponent, [
    ROUTER_PROVIDERS,
    provide(APP_BASE_HREF, { useValue: '/' }),
    HTTP_PROVIDERS,
    SecurityService,
    OAuthHttpClient
]);