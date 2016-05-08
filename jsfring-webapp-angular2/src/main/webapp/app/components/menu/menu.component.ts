import { Component } from 'angular2/core';
import { ROUTER_DIRECTIVES } from 'angular2/router';

import { Menubar } from 'primeng/primeng';

@Component({
    selector: 'p62-menu',
    templateUrl: './app/components/menu/menu.component.html',
    directives: [Menubar, ROUTER_DIRECTIVES]
})
export class MenuComponent { }