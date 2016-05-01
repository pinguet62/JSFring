import { OnInit } from 'angular2/core';
import { ProfileService } from '../../services/profile.service';
import { Profile } from '../../dto/Profile';
export declare class ProfileComponent implements OnInit {
    private _profileService;
    profiles: Profile[];
    constructor(_profileService: ProfileService);
    ngOnInit(): void;
}
