import {Routes} from '@angular/router';
import {IndexComponent} from './index.component';
import {RightComponent} from './right/right.component';
import {ProfileComponent} from './profile/profile.component';
import {UserComponent} from './user/user.component';

export const routes: Routes = [
    {path: '', component: IndexComponent},
    {path: 'rights', component: RightComponent},
    {path: 'profiles', component: ProfileComponent},
    {path: 'users', component: UserComponent}
];
