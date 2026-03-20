
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private router: Router) {}

  login(username: string, password: string): void {
    sessionStorage.setItem('admin_credentials', btoa(`${username}:${password}`));
  }

  logout(): void {
    sessionStorage.removeItem('admin_credentials');
    this.router.navigate(['/admin/login']);
  }

  getCredentials(): string | null {
    return sessionStorage.getItem('admin_credentials');
  }

  isAuthenticated(): boolean {
    return !!this.getCredentials();
  }
}