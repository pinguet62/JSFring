import {APP_INITIALIZER, NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClient, HttpClientModule} from '@angular/common/http';
import {RouterModule} from '@angular/router';
import {
    MatAutocompleteModule,
    MatButtonModule,
    MatCheckboxModule,
    MatDialogModule,
    MatIconModule,
    MatInputModule,
    MatListModule,
    MatMenuModule,
    MatSelectModule,
    MatSidenavModule,
    MatSnackBarModule,
    MatTableModule,
    MatToolbarModule
} from '@angular/material';
import {ButtonModule, CalendarModule, CheckboxModule, DataTableModule, DialogModule, InputTextModule, MenubarModule, SliderModule} from 'primeng/primeng';
import {ContentTypeHeaderHttpClientInterceptor} from './content-type-header-http-client.http-interceptor';
import {routes} from './app.routing';
import {AppComponent} from './app.component';
import {DatatableColumnsComponent, DatatableComponent, DatatableDialogComponent} from './shared/datatable.component';
import {IndexComponent} from './index.component';
import {UserComponent} from './user/user.component';
import {ProfileComponent} from './profile/profile.component';
import {RightComponent} from './right/right.component';
import {UserService} from './user/user.service';
import {ProfileService} from './profile/profile.service';
import {RightService} from './right/right.service';
import {configInitialize} from './config';
import {WebSocketModule} from './websocket';
import {OAuth2Module} from './oauth2';

@NgModule({
    imports: [
        BrowserModule, BrowserAnimationsModule,
        FormsModule, ReactiveFormsModule,
        HttpClientModule,
        MatAutocompleteModule, MatButtonModule, MatCheckboxModule, MatDialogModule, MatIconModule, MatInputModule, MatListModule, MatMenuModule, MatSelectModule, MatSidenavModule, MatSnackBarModule, MatTableModule, MatToolbarModule,
        MenubarModule, DataTableModule, InputTextModule, ButtonModule, SliderModule, DialogModule, CheckboxModule, CalendarModule,
        RouterModule.forRoot(routes), WebSocketModule, OAuth2Module,
    ],
    declarations: [
        AppComponent,
        DatatableComponent, DatatableColumnsComponent, DatatableDialogComponent,
        IndexComponent,
        UserComponent, ProfileComponent, RightComponent
    ],
    bootstrap: [AppComponent],
    providers: [
        {provide: HTTP_INTERCEPTORS, useClass: ContentTypeHeaderHttpClientInterceptor, multi: true},
        UserService, ProfileService, RightService,
        {
            provide: APP_INITIALIZER,
            useFactory: configInitialize,
            deps: [HttpClient],
            multi: true
        }
    ]
})
export class AppModule {
}
