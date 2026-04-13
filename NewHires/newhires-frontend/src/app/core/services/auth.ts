import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private router: Router, private http: HttpClient) {}

  login(username: string, password: string, callbacks?: { onError?: () => void }): void {
  const credentials = btoa(`${username}:${password}`);
  const headers = new HttpHeaders({ Authorization: `Basic ${credentials}` });

  this.http.get<any>('http://localhost:8080/api/auth/me', { headers }).subscribe({
    next: (user) => {
      sessionStorage.setItem('admin_credentials', credentials);
      sessionStorage.setItem('username', user.username);
      sessionStorage.setItem('user_id', user.id);
      sessionStorage.setItem('role', user.role);
      this.router.navigate(['/admin/']);
    },
    error: () => {
      callbacks?.onError?.();
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