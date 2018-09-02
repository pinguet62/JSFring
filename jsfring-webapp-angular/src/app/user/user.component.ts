import {Component} from '@angular/core';
import {UserService} from './user.service';

@Component({
    selector: 'app-user',
    template: `
        <app-datatable [service]="userService" [(selectedValue)]="selectedUser">

            <app-datatable-columns>
                <p-column field="email" header="Email" sortable="true" filter="true" filterMatchMode="contains"></p-column>
                <p-column field="password" header="Password" hidden="true" sortable="true" filter="true" filterMatchMode="contains"></p-column>
                <p-column field="active" header="Active" sortable="true"></p-column>
                <p-column field="lastConnection" header="Last connection" sortable="true"></p-column>
            </app-datatable-columns>

            <app-datatable-dialog>
                <ng-template [ngIf]="selectedUser">
                    <mat-form-field>
                        <input matInput placeholder="Email" [(value)]="selectedUser.email" disabled>
                    </mat-form-field>
                    <br>
                    <mat-checkbox [(ngModel)]="selectedUser.active">Active</mat-checkbox>
                    <br>
                    <span>Last connection</span>
                    <p-calendar [(ngModel)]="selectedUser.lastConnection" showTime="showTime"></p-calendar>
                </ng-template>
            </app-datatable-dialog>

        </app-datatable>`
})
export class UserComponent {

    selectedUser: any;

    constructor(public userService: UserService) {
    }

}
