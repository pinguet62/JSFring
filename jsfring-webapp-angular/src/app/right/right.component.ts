import {Component} from "@angular/core";

import {RightService} from "./right.service";

@Component({
    selector: 'right',
    template: `
        <p62-datatable [service]="rightService" [(selectedValue)]="selectedRight">
        
            <p62-datatable-columns>
                <p-column field="code" header="Code" sortable="true" filter="true" filterMatchMode="contains"></p-column>
                <p-column field="title" header="Title" sortable="true" filter="true" filterMatchMode="contains"></p-column>
            </p62-datatable-columns>
            
            <p62-datatable-dialog>
                <ng-template [ngIf]="selectedRight">
                    <mat-input-container>
                        <input matInput placeholder="Code" [(value)]="selectedRight.code" disabled>
                    </mat-input-container>
                    <br>
                    <mat-input-container>
                        <input matInput placeholder="Title" [(value)]="selectedRight.title">
                    </mat-input-container>
                </ng-template>
            </p62-datatable-dialog>
        
        </p62-datatable>`
})
export class RightComponent {

    selectedRight: any;

    constructor(public rightService: RightService) {}

}