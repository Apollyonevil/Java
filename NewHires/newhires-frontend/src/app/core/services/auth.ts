import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class AuthService {

  constructor(private router: Router, private http: HttpClient) {}

  login(username: string, password: string, callbacks?: { onError?: () => void }): void {
    this.hashPassword(password).then(hashedPassword => {
      this.http.post<any>('http://localhost:8080/api/auth/login', { username, password: hashedPassword })
        .subscribe({
          next: (user) => {
            const credentials = btoa(`${username}:${hashedPassword}`);
            sessionStorage.setItem('admin_credentials', credentials);
            sessionStorage.setItem('username', user.username);
            sessionStorage.setItem('user_id', user.id);
            sessionStorage.setItem('role', user.role);
            this.router.navigate(['/admin/']);
          },
          error: () => callbacks?.onError?.()
        });
    });
  }

  private async hashPassword(password: string): Promise<string> {
    const encoder = new TextEncoder();
    const data = encoder.encode(password);
    const hashBuffer = await crypto.subtle.digest('SHA-256', data);
    const hashArray = Array.from(new Uint8Array(hashBuffer));
    return hashArray.map(b => b.toString(16).padStart(2, '0')).join('');
  }

  logout(): void {
    sessionStorage.clear();
    this.router.navigate(['/admin/login']);
  }

  getCredentials(): string | null {
    return sessionStorage.getItem('admin_credentials');
  }

  isAuthenticated(): boolean {
    return !!sessionStorage.getItem('username');
  }
}