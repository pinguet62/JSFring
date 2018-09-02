import {Component} from '@angular/core';
import {RightService} from './right.service';

@Component({
    selector: 'app-right',
    template: `
        <app-datatable [service]="rightService" [(selectedValue)]="selectedRight">

            <app-datatable-columns>
                <p-column field="code" header="Code" sortable="true" filter="true" filterMatchMode="contains"></p-column>
                <p-column field="title" header="Title" sortable="true" filter="true" filterMatchMode="contains"></p-column>
            </app-datatable-columns>

            <app-datatable-dialog>
                <ng-template [ngIf]="selectedRight">
                    <mat-form-field>
                        <input matInput placeholder="Code" [(value)]="selectedRight.code" disabled>
                    </mat-form-field>
                    <br>
                    <mat-form-field>
                        <input matInput placeholder="Title" [(value)]="selectedRight.title">
                    </mat-form-field>
                </ng-template>
            </app-datatable-dialog>

        </app-datatable>`
})
export class RightComponent {

    selectedRight: any;

    constructor(public rightService: RightService) {
    }

}
