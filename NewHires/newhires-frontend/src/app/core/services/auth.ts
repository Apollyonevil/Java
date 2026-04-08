import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private router: Router) {}

  login(username: string, password: string): void {
    // Guardamos las credenciales en Base64 como hacias antes
    const credentials = btoa(`${username}:${password}`);
    sessionStorage.setItem('admin_credentials', credentials);
    
    // IMPORTANTE: Guardamos el username y el role para que el Dashboard funcione.
    // Como es un login local, asignamos ADMIN por defecto o basado en el nombre.
    sessionStorage.setItem('username', username);
    
    if (username.toLowerCase() === 'admin') {
      sessionStorage.setItem('role', 'ADMIN');
    } else {
      sessionStorage.setItem('role', 'EMPLOYEE');
    }

    // Navegamos al dashboard tras el "login"
    this.router.navigate(['/admin/dashboard']);
  }

  logout(): void {
    sessionStorage.clear(); // Limpiamos todo
    this.router.navigate(['/admin/login']);
  }

  getCredentials(): string | null {
    return sessionStorage.getItem('admin_credentials');
  }

  // Este es el método que pedía el Guard
  isAuthenticated(): boolean {
    return !!this.getCredentials();
  }
}