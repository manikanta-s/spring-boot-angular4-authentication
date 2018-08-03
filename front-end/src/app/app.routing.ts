import { Routes, RouterModule } from '@angular/router';
import {LoginComponent} from "./components/login/login.component";
import {RegisterComponent} from "./components/register/register.component";
import {ProfileComponent} from "./components/profile/profile.component";
import {EventComponent} from "./components/event/event.component";
import {EventDetailsComponent} from "./components/event-details/event-details.component";
import {EventEditComponent} from "./components/event-edit/event-edit.component";
import {UrlPermission} from "./urlPermission/url.permission";


const appRoutes: Routes = [
  { path: 'profile', component: ProfileComponent ,canActivate: [UrlPermission] },
  { path: 'event', component: EventComponent ,canActivate: [UrlPermission] },
  { path: 'event-details', component: EventDetailsComponent ,canActivate: [UrlPermission] },
  { path: 'event-edit', component: EventEditComponent ,canActivate: [UrlPermission] },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },

  // otherwise redirect to profile
  { path: '**', redirectTo: '/login' }
];

export const routing = RouterModule.forRoot(appRoutes);
