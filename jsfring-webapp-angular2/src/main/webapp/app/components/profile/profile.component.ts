import { Component } from '@angular/core';

import { Column, InputText } from 'primeng/primeng';

import { Datatable, DatatableColumns } from '../datatable/datatable'
import { ProfileService } from '../../services/profile.service';

@Component({
    selector: 'profile',
    templateUrl: './app/components/profile/profile.component.html',
    directives: [Datatable, DatatableColumns, Column, InputText],
    providers: [ProfileService]
})
export class ProfileComponent {

    selectedPRofil: any;

    constructor(public profileService: ProfileService) { }

}