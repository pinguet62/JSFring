import {Component} from 'angular2/core';
import {RouteConfig, ROUTER_DIRECTIVES} from 'angular2/router';

import {MenuComponent} from './../menu/menu.component';
import {RightComponent} from '../right/right.component';
import {ProfileComponent} from '../profile/profile.component';
import {UserComponent} from '../user/user.component';

@Component({
    selector: 'app',
    templateUrl: './app/components/app/app.component.html',
    directives: [MenuComponent, ROUTER_DIRECTIVES]
})
@RouteConfig([
    {
        path: '/rights',
        name: 'Rights',
        component: RightComponent
    },
    {
        path: '/profiles',
        name: 'Profiles',
        component: ProfileComponent
    },
    {
        path: '/users',
        name: 'Users',
        component: UserComponent
    }
])
export class AppComponent { }