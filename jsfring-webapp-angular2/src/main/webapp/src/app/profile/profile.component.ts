import {Component} from "@angular/core";

import {Column, InputText} from "primeng/primeng";

import {DatatableComponent, DatatableColumnsComponent} from "../shared/datatable.component"
import {ProfileService} from "./profile.service";

@Component({
    selector: 'profile',
    template: `
        <p62-datatable [service]="profileService" [(selectedValue)]="selectedProfile">
        
            <p62-datatable-columns>
                <p-column field="id" header="Id" sortable="true" filter="true" filterMatchMode="contains" hidden="true"></p-column>
                <p-column field="title" header="Title" sortable="true" filter="true" filterMatchMode="contains"></p-column>
            </p62-datatable-columns>
        
            <p62-datatable-dialog>
                <ng-template [ngIf]="selectedProfile">
                    <mat-input-container>
                        <input matInput placeholder="Id" [(value)]="selectedProfile.id" disabled>
                    </mat-input-container>
                    <br>
                    <mat-input-container>
                        <input matInput placeholder="Title" [(value)]="selectedProfile.title">
                    </mat-input-container>
                </ng-template>
            </p62-datatable-dialog>
        
        </p62-datatable>`
})
export class ProfileComponent {

    selectedProfil: any;

    constructor(public profileService: ProfileService) {}

}