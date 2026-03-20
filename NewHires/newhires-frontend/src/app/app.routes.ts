import { Routes } from '@angular/router';
import { OnboardingFormComponent } from './features/onboarding-form/onboarding-form';
import { AdminDashboardComponent } from './features/admin-dashboard/admin-dashboard';
import { AdminLoginComponent } from './features/admin-login/admin-login';
import { authGuard } from './core/guards/auth';

export const routes: Routes = [
  {
    path: 'onboarding',
    component: OnboardingFormComponent
  },
  {
    path: 'admin',
    component: AdminDashboardComponent,
    canActivate: [authGuard]
  },
  {
    path: 'admin/login',
    component: AdminLoginComponent
  },
  {
    path: '',
    redirectTo: '/onboarding',
    pathMatch: 'full'
  },
  {
    path: '**',
    redirectTo: '/onboarding'
  }
];