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
                    <md-input-container>
                        <input mdInput placeholder="Id" [(value)]="selectedProfile.id" disabled>
                    </md-input-container>
                    <br>
                    <md-input-container>
                        <input mdInput placeholder="Title" [(value)]="selectedProfile.title">
                    </md-input-container>
                </ng-template>
            </p62-datatable-dialog>
        
        </p62-datatable>`
})
export class ProfileComponent {

    selectedProfil: any;

    constructor(public profileService: ProfileService) {}

}