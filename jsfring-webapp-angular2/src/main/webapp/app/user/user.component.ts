import {Component} from "@angular/core";

import {UserService} from "./user.service";

@Component({
    selector: 'user',
    template: `
        <p62-datatable [service]="userService" [(selectedValue)]="selectedUser">
        
            <p62-datatable-columns>
                <p-column field="login" header="Login" sortable="true" filter="true" filterMatchMode="contains"></p-column>
                <p-column field="password" header="Password" hidden="true" sortable="true" filter="true" filterMatchMode="contains"></p-column>
                <p-column field="email" header="Email" sortable="true" filter="true" filterMatchMode="contains"></p-column>
                <p-column field="active" header="Active" sortable="true"></p-column>
                <p-column field="lastConnection" header="Last connection" sortable="true"></p-column>
            </p62-datatable-columns>
        
            <p62-datatable-dialog>
                <ng-template [ngIf]="selectedUser">
                    <md-input-container>
                        <input mdInput placeholder="Login" [(value)]="selectedUser.login" disabled>
                    </md-input-container>
                    <br>
                    <md-input-container>
                        <input mdInput placeholder="Email" [(value)]="selectedUser.email">
                    </md-input-container>
                    <br>
                    <md-checkbox [(ngModel)]="selectedUser.active">Active</md-checkbox>
                    <br>
                    <span>Last connection</span>
                    <p-calendar [(ngModel)]="selectedUser.lastConnection" showTime="showTime"></p-calendar>
                </ng-template>
            </p62-datatable-dialog>
        
        </p62-datatable>`
})
export class UserComponent {

    selectedUser: any;

    constructor(public userService: UserService) {}

}