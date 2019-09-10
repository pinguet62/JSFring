import {HttpClientModule} from '@angular/common/http';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatButtonModule, MatIconModule, MatListModule, MatMenuModule, MatSidenavModule, MatSnackBarModule, MatToolbarModule} from '@angular/material';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {RouterModule} from '@angular/router';
import {ServiceWorkerModule} from '@angular/service-worker';
import {environment} from '../environments/environment';
import {AppComponent} from './app.component';
import {routes} from './app.routing';
import {ConfigLoaderModule} from './config-loader';
import {IndexComponent} from './index.component';
import {OAuth2Module} from './oauth2';
import {WebSocketModule} from './websocket';

@NgModule({
    imports: [
        // framework
        BrowserModule, BrowserAnimationsModule,
        FormsModule, ReactiveFormsModule,
        HttpClientModule,
        // libs
        MatButtonModule, MatIconModule, MatListModule, MatMenuModule, MatSidenavModule, MatSnackBarModule, MatToolbarModule,
        // app
        ServiceWorkerModule.register('ngsw-worker.js', { enabled: environment.production }),
        RouterModule.forRoot(routes), ConfigLoaderModule, OAuth2Module, WebSocketModule,
    ],
    declarations: [
        AppComponent,
        IndexComponent,
    ],
    bootstrap: [AppComponent],
})
export class AppModule {
}
