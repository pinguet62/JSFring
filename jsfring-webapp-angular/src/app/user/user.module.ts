import {CommonModule} from '@angular/common';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {MatCheckboxModule, MatFormFieldModule, MatInputModule} from '@angular/material';
import {RouterModule} from '@angular/router';
import {CalendarModule, DataTableModule} from 'primeng/primeng';
import {DatatableModule} from '../shared/datatable';
import {UserComponent} from './user.component';
import {routes} from './user.routing';
import {UserService} from './user.service';

@NgModule({
    imports: [
        // framework
        CommonModule,
        FormsModule,
        // libs
        MatCheckboxModule, MatFormFieldModule, MatInputModule,
        CalendarModule, DataTableModule,
        // app
        RouterModule.forChild(routes),
        DatatableModule,
    ],
    declarations: [UserComponent],
    providers: [UserService],
})
export class UserModule {
}
