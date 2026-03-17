import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormService } from '../../core/services/form';
import { Submission } from '../../shared/models/form.model';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.html',
  standalone: true,
  imports: [CommonModule]
})
export class AdminDashboardComponent implements OnInit {
  submissions: Submission[] = [];
  loading: boolean = true;

  constructor(private formService: FormService) {}

  ngOnInit() {
    this.loadSubmissions();
  }

  loadSubmissions() {
    this.formService.getAllSubmissions().subscribe({
      next: (data) => {
        this.submissions = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error cargando registros', err);
        this.loading = false;
      }
    });
  }

  getStatusClass(status: string) {
    switch (status) {
      case 'VALIDATED': return 'badge bg-success';
      case 'PENDING': return 'badge bg-warning text-dark';
      case 'REJECTED': return 'badge bg-danger';
      default: return 'badge bg-secondary';
    }
  }
}