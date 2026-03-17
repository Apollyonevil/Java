import { Routes } from '@angular/router';
import { OnboardingFormComponent } from './features/onboarding-form/onboarding-form'; 
import { AdminDashboardComponent } from './features/admin-dashboard/admin-dashboard';

export const routes: Routes = [
  // Ruta para el empleado (ej: /onboarding?token=uuid-123)
  { 
    path: 'onboarding', 
    component: OnboardingFormComponent 
  },
  
  // Ruta para RRHH
  { 
    path: 'admin', 
    component: AdminDashboardComponent 
  },

  // Redirección inicial
  { 
    path: '', 
    redirectTo: '/onboarding', 
    pathMatch: 'full' 
  },

  // Comodín para páginas no encontradas (404)
  { 
    path: '**', 
    redirectTo: '/onboarding' 
  }
];