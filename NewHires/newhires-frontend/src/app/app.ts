import { Component } from '@angular/core';
import { Router, RouterOutlet, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,

  imports: [RouterOutlet, RouterModule, CommonModule],
  template: `
    <nav class="navbar navbar-dark custom-nav mb-4 shadow-sm">
      <div class="container">
        <a class="navbar-brand d-flex align-items-center" [routerLink]="[getLogoRoute()]" style="cursor: pointer;">
          <img src="logo.png" 
               alt="Logo" 
               width="30" 
               height="30" 
               class="d-inline-block align-text-top me-2">
          <div class="d-flex flex-column lh-1">
            <span class="brand-text">Nuevas Incorporaciones</span>
            <span class="brand-text2">Cívica Software</span>
          </div>
        </a>
      </div>
    </nav>
    
    <main class="container">
      <router-outlet></router-outlet>
    </main>
  `
})
export class AppComponent {
  constructor(private router: Router) {}

  getLogoRoute(): string {
    const currentUrl = this.router.url;
    
    if (currentUrl.includes('/admin')) {
      return '/admin';
    }
    
    return '/';
  }
}