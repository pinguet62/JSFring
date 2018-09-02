import {Routes} from '@angular/router';
import {IndexComponent} from './index.component';

export const routes: Routes = [
    {path: '', component: IndexComponent},
    {path: 'rights', loadChildren: './right/right.module#RightModule'},
    {path: 'profiles', loadChildren: './profile/profile.module#ProfileModule'},
    {path: 'users', loadChildren: './user/user.module#UserModule'},
];
