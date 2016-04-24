import {Component, OnInit} from 'angular2/core';

import {ProfileService} from '../../services/profile.service';

import {MenuComponent} from '../menu/menu.component';
import {DataTableComponent} from '../dataTable/dataTable';
import {ColumnComponent} from '../dataTable/column';

import {Profile} from '../../dto/Profile';

@Component({
    selector: 'profile',
    templateUrl: './app/components/profile/profile.component.html',
    directives: [MenuComponent, DataTableComponent, ColumnComponent],
    providers: [ProfileService]
})
export class ProfileComponent implements OnInit {

    profiles: Profile[];

    constructor(private _profileService: ProfileService) { }

    ngOnInit() {
        this.profiles = this._profileService.getValues();
    }

}