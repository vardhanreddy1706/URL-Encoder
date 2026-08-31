import { Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { LandingPageComponent } from './landing-page/landing-page.component';

export const routes: Routes = [
  { path: '', component: LandingPageComponent},
  {
    path: '/',
    component: DashboardComponent,
  },
  { path: '**', redirectTo: '' },
];
