import {CommonModule} from '@angular/common';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {MatFormFieldModule, MatInputModule} from '@angular/material';
import {RouterModule} from '@angular/router';
import {DataTableModule} from 'primeng/primeng';
import {DatatableModule} from '../shared/datatable';
import {RightComponent} from './right.component';
import {routes} from './right.routing';
import {RightService} from './right.service';

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
        DatatableModule
    ],
    declarations: [RightComponent],
    providers: [RightService],
})
export class RightModule {
}
