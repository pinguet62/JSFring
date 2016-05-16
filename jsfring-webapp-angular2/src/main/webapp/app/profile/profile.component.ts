import { Component } from '@angular/core';

import { Column, InputText } from 'primeng/primeng';

import { DatatableComponent, DatatableColumnsComponent } from '../shared/datatable.component'
import { ProfileService } from './profile.service';

@Component({
    selector: 'profile',
    templateUrl: './app/profile/profile.component.html',
    directives: [Column, DatatableComponent, DatatableColumnsComponent, InputText],
    providers: [ProfileService]
})
export class ProfileComponent {

    selectedProfil: any;

    constructor(public profileService: ProfileService) { }

}