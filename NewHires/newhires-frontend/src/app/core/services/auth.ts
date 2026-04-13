import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private router: Router, private http: HttpClient) {}

  login(username: string, password: string): void {
    // 1. Preparamos las credenciales
    const credentials = btoa(`${username}:${password}`);
    const headers = new HttpHeaders({
      Authorization: `Basic ${credentials}`
    });

    // 2. Llamamos al "espejo" que creamos en el Backend
    // Usamos GET y la ruta que definimos en el AuthController
    this.http.get<any>('http://localhost:8080/api/auth/me', { headers }).subscribe({
      next: (user) => {
        // 3. ¡Ahora sí! Guardamos los datos reales del servidor
        sessionStorage.setItem('admin_credentials', credentials);
        sessionStorage.setItem('username', user.username);
        sessionStorage.setItem('user_id', user.id); // Aquí llega el 8888... real
        sessionStorage.setItem('role', user.role);

        this.router.navigate(['/admin/dashboard']);
      },
      error: (err) => {
        console.error('Error en el login:', err);
        alert('Usuario o contraseña incorrectos');
      }
    });
  }

  logout(): void {
    sessionStorage.clear();
    this.router.navigate(['/admin/login']);
  }

  getCredentials(): string | null {
    return sessionStorage.getItem('admin_credentials');
  }

  isAuthenticated(): boolean {
    return !!this.getCredentials();
  }
}