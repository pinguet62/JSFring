import {Component} from '@angular/core';
import {UserService} from './user.service';

@Component({
    selector: 'p62-user',
    template: `
        <p62-datatable [service]="userService" [(selectedValue)]="selectedUser">

            <p62-datatable-columns>
                <p-column field="email" header="Email" sortable="true" filter="true" filterMatchMode="contains"></p-column>
                <p-column field="password" header="Password" hidden="true" sortable="true" filter="true" filterMatchMode="contains"></p-column>
                <p-column field="active" header="Active" sortable="true"></p-column>
                <p-column field="lastConnection" header="Last connection" sortable="true"></p-column>
            </p62-datatable-columns>

            <p62-datatable-dialog>
                <ng-template [ngIf]="selectedUser">
                    <mat-input-container>
                        <input matInput placeholder="Email" [(value)]="selectedUser.email" disabled>
                    </mat-input-container>
                    <br>
                    <mat-checkbox [(ngModel)]="selectedUser.active">Active</mat-checkbox>
                    <br>
                    <span>Last connection</span>
                    <p-calendar [(ngModel)]="selectedUser.lastConnection" showTime="showTime"></p-calendar>
                </ng-template>
            </p62-datatable-dialog>

        </p62-datatable>`
})
export class UserComponent {

    selectedUser: any;

    constructor(public userService: UserService) {
    }

}
