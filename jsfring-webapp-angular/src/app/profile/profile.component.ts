import {Component} from '@angular/core';
import {ProfileService} from './profile.service';

@Component({
    selector: 'app-profile',
    template: `
        <app-datatable [service]="profileService" [(selectedValue)]="selectedProfile">

            <app-datatable-columns>
                <p-column field="id" header="Id" sortable="true" filter="true" filterMatchMode="contains" hidden="true"></p-column>
                <p-column field="title" header="Title" sortable="true" filter="true" filterMatchMode="contains"></p-column>
            </app-datatable-columns>

            <app-datatable-dialog>
                <ng-template [ngIf]="selectedProfile">
                    <mat-form-field>
                        <input matInput placeholder="Id" [(value)]="selectedProfile.id" disabled>
                    </mat-form-field>
                    <br>
                    <mat-form-field>
                        <input matInput placeholder="Title" [(value)]="selectedProfile.title">
                    </mat-form-field>
                </ng-template>
            </app-datatable-dialog>

        </app-datatable>`
})
export class ProfileComponent {

    selectedProfile: any;

    constructor(public profileService: ProfileService) {
    }

}
