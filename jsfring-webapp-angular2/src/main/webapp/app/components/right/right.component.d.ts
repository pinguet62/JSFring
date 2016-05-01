import { OnInit } from 'angular2/core';
import { RightService } from '../../services/right.service';
import { Right } from '../../dto/Right';
export declare class RightComponent implements OnInit {
    private _rightService;
    rights: Right[];
    constructor(_rightService: RightService);
    ngOnInit(): void;
}
