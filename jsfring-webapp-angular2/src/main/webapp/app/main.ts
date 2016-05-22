import 'rxjs/Rx'; // Observable methods

import { bootstrap } from '@angular/platform-browser-dynamic';
import { provide } from '@angular/core';
import { APP_BASE_HREF } from '@angular/common';
import { ROUTER_PROVIDERS } from '@angular/router';
import { HTTP_PROVIDERS } from '@angular/http';

import {MdIconRegistry} from '@angular2-material/icon/icon-registry';

import { AppComponent } from './app.component';
import { SecurityService } from './security/security.service';
import { OAuthHttp } from './security/oauth-http.service';

bootstrap(AppComponent, [
    ROUTER_PROVIDERS,
    provide(APP_BASE_HREF, { useValue: '/' }),
    HTTP_PROVIDERS,
	
	MdIconRegistry,
	
    SecurityService,
    OAuthHttp
]);