import {Component, OnInit} from 'angular2/core';

import {DataTable} from 'primeng/primeng';
import {Column} from 'primeng/primeng';

import {ProfileService} from '../../services/profile.service';
import {Profile} from '../../dto/Profile';

@Component({
    selector: 'profile',
    templateUrl: './app/components/profile/profile.component.html',
    directives: [DataTable, Column],
    providers: [ProfileService]
})
export class ProfileComponent implements OnInit {

    values: Array<Profile>;

    constructor(private profileService: ProfileService) { }

    ngOnInit() {
        this.profileService.findAll().subscribe(res => this.values = res);
    }

}