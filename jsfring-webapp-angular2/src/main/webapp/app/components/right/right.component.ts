import {Component, OnInit} from 'angular2/core';

import {DataTable} from 'primeng/primeng';
import {Column} from 'primeng/primeng';

import {RightService} from '../../services/right.service';
import {Right} from '../../dto/Right';

@Component({
    selector: 'right',
    templateUrl: './app/components/right/right.component.html',
    directives: [DataTable, Column],
    providers: [RightService]
})
export class RightComponent implements OnInit {

    values: Array<Right>;

    constructor(private rightService: RightService) { }

    ngOnInit() {
        this.rightService.findAll().subscribe(res => this.values = res);
    }

}