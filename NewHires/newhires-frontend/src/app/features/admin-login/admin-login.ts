import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../core/services/auth';

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
              <h5 class="mb-0">Acceso Empleados</h5>
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

  constructor(private authService: AuthService) {}

  login() {
    if (!this.username || !this.password) return;
    this.loading = true;
    this.error = false;

    this.authService.login(this.username, this.password, {
      onError: () => {
        this.error = true;
        this.loading = false;
      }
    });
  }
}