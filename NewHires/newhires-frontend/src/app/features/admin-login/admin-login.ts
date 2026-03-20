// src/app/features/admin-login/admin-login.ts
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { AuthService } from '../../core/services/auth';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-admin-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="container py-5">
      <div class="row justify-content-center">
        <div class="col-md-4">
          <div class="card shadow border-0">
            <div class="card-header bg-dark text-white text-center py-3">
              <h5 class="mb-0">Acceso Administración</h5>
            </div>
            <div class="card-body p-4">
              @if (error) {
                <div class="alert alert-danger small">Usuario o contraseña incorrectos</div>
              }
              <div class="mb-3">
                <label class="form-label small fw-bold">Usuario</label>
                <input class="form-control" [(ngModel)]="username" placeholder="Usuario">
              </div>
              <div class="mb-3">
                <label class="form-label small fw-bold">Contraseña</label>
                <input class="form-control" type="password" [(ngModel)]="password" placeholder="Contraseña">
              </div>
              <div class="d-grid">
                <button class="btn btn-dark" (click)="login()" [disabled]="loading">
                  {{ loading ? 'Verificando...' : 'Entrar' }}
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  `
})
export class AdminLoginComponent {
  username = '';
  password = '';
  error = false;
  loading = false;

  constructor(
    private authService: AuthService,
    private router: Router,
    private http: HttpClient
  ) {}

  login() {
    if (!this.username || !this.password) return;
    this.loading = true;
    this.error = false;

    const credentials = btoa(`${this.username}:${this.password}`);
    const headers = new HttpHeaders({ 'Authorization': `Basic ${credentials}` });

    // Verificamos las credenciales contra el backend
    this.http.get('http://localhost:8080/api/admin/forms/submissions', { headers }).subscribe({
      next: () => {
        this.authService.login(this.username, this.password);
        this.router.navigate(['/admin']);
        this.loading = false;
      },
      error: () => {
        this.error = true;
        this.loading = false;
      }
    });
  }
}