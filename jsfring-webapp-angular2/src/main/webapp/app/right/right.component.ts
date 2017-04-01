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
                    <md-input-container>
                        <input mdInput placeholder="Code" [(value)]="selectedRight.code" disabled>
                    </md-input-container>
                    <br>
                    <md-input-container>
                        <input mdInput placeholder="Title" [(value)]="selectedRight.title">
                    </md-input-container>
                </ng-template>
            </p62-datatable-dialog>
        
        </p62-datatable>`
})
export class RightComponent {

    selectedRight: any;

    constructor(public rightService: RightService) {}

}