import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../core/services/auth';

@Component({
  selector: 'app-admin-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-login.html',
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