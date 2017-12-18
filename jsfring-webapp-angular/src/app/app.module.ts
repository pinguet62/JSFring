import {NgModule, APP_INITIALIZER} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule, HttpClient, HTTP_INTERCEPTORS} from '@angular/common/http';
import {RouterModule} from "@angular/router";
import {MatAutocompleteModule, MatButtonModule, MatCheckboxModule, MatDialogModule, MatIconModule, MatInputModule, MatListModule, MatMenuModule, MatSelectModule, MatSidenavModule, MatTableModule, MatToolbarModule} from "@angular/material";
import {MenubarModule, DataTableModule, InputTextModule, ButtonModule, SliderModule, DialogModule, CheckboxModule, CalendarModule} from "primeng/primeng";

import {ContentTypeHeaderHttpClientInterceptor} from './content-type-header-http-client.http-interceptor';
import {routes} from "./app.routing";
import {AppComponent} from "./app.component";
import {SecurityModule} from "./security/security-module";
import {DatatableComponent, DatatableColumnsComponent, DatatableDialogComponent} from "./shared/datatable.component";
import {IndexComponent} from "./index.component";
import {UserComponent} from "./user/user.component";
import {ProfileComponent} from "./profile/profile.component";
import {RightComponent} from "./right/right.component";
import {UserService} from "./user/user.service";
import {ProfileService} from "./profile/profile.service";
import {RightService} from "./right/right.service";

import {configInitialize} from './config';

@NgModule({
    imports: [
        BrowserModule, BrowserAnimationsModule,
        FormsModule, ReactiveFormsModule,
        HttpClientModule,
        MatAutocompleteModule, MatButtonModule, MatCheckboxModule, MatDialogModule, MatIconModule, MatInputModule, MatListModule, MatMenuModule, MatSelectModule, MatSidenavModule, MatTableModule, MatToolbarModule,
        MenubarModule, DataTableModule, InputTextModule, ButtonModule, SliderModule, DialogModule, CheckboxModule, CalendarModule,
        RouterModule.forRoot(routes), SecurityModule
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
export class AppModule {}