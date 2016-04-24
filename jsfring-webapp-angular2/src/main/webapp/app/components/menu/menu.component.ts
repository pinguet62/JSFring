import {Component} from 'angular2/core';
import {ROUTER_DIRECTIVES} from "angular2/router";

@Component({
    selector: 'menu',
    templateUrl: './app/components/menu/menu.component.html',
    directives: [ROUTER_DIRECTIVES]
})
export class MenuComponent { }