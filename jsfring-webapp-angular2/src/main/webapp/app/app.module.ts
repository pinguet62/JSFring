import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {RouterModule} from "@angular/router";

import {MaterialModule} from "@angular/material";
import {MenubarModule, DataTableModule, InputTextModule, ButtonModule, SliderModule, DialogModule, CheckboxModule, CalendarModule} from "primeng/primeng";

import {routes} from "./app.routing";
import {AppComponent} from "./app.component";
import {SecurityModule} from "./security/security-module";
import {DatatableComponent, DatatableColumnsComponent, DatatableDialogComponent} from "./shared/datatable.component";
import {IndexComponent} from "./index.component";
import {MenuComponent} from "./menu.component";
import {UserComponent} from "./user/user.component";
import {ProfileComponent} from "./profile/profile.component";
import {RightComponent} from "./right/right.component";
import {UserService} from "./user/user.service";
import {ProfileService} from "./profile/profile.service";
import {RightService} from "./right/right.service";

@NgModule({
    imports: [
        BrowserModule, BrowserAnimationsModule,
        FormsModule, ReactiveFormsModule,
        MaterialModule.forRoot(),
        MenubarModule, DataTableModule, InputTextModule, ButtonModule, SliderModule, DialogModule, CheckboxModule, CalendarModule,
        RouterModule.forRoot(routes), SecurityModule
    ],
    declarations: [
        AppComponent,
        DatatableComponent, DatatableColumnsComponent, DatatableDialogComponent,
        IndexComponent, MenuComponent,
        UserComponent, ProfileComponent, RightComponent
    ],
    bootstrap: [AppComponent],
    providers: [UserService, ProfileService, RightService]
})
export class AppModule {}