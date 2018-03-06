import {Component} from '@angular/core';
import {ProfileService} from './profile.service';

@Component({
    selector: 'p62-profile',
    template: `
        <p62-datatable [service]="profileService" [(selectedValue)]="selectedProfile">

            <p62-datatable-columns>
                <p-column field="id" header="Id" sortable="true" filter="true" filterMatchMode="contains" hidden="true"></p-column>
                <p-column field="title" header="Title" sortable="true" filter="true" filterMatchMode="contains"></p-column>
            </p62-datatable-columns>

            <p62-datatable-dialog>
                <ng-template [ngIf]="selectedProfile">
                    <mat-form-field>
                        <input matInput placeholder="Id" [(value)]="selectedProfile.id" disabled>
                    </mat-form-field>
                    <br>
                    <mat-form-field>
                        <input matInput placeholder="Title" [(value)]="selectedProfile.title">
                    </mat-form-field>
                </ng-template>
            </p62-datatable-dialog>

        </p62-datatable>`
})
export class ProfileComponent {

    selectedProfile: any;

    constructor(public profileService: ProfileService) {
    }

}
