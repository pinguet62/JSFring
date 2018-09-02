import {CommonModule} from '@angular/common';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {MatFormFieldModule, MatInputModule} from '@angular/material';
import {RouterModule} from '@angular/router';
import {DataTableModule} from 'primeng/primeng';
import {DatatableModule} from '../shared/datatable';
import {ProfileComponent} from './profile.component';
import {routes} from './profile.routing';
import {ProfileService} from './profile.service';

@NgModule({
    imports: [
        // framework
        CommonModule,
        FormsModule,
        // libs
        MatFormFieldModule, MatInputModule,
        DataTableModule,
        // app
        RouterModule.forChild(routes),
        DatatableModule,
    ],
    declarations: [ProfileComponent],
    providers: [ProfileService],
})
export class ProfileModule {
}
