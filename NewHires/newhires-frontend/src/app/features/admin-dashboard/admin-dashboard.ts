import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormService } from '../../core/services/form';
import { Submission, FieldDefinition } from '../../shared/models/form.model';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.html',
  standalone: true,
  imports: [CommonModule]
})
export class AdminDashboardComponent implements OnInit {
  submissions: Submission[] = [];
  fields: FieldDefinition[] = [];
  loading: boolean = true;

  constructor(private formService: FormService) {}

  ngOnInit() {
    this.loadSubmissions();
    this.loadFields();
  }

  loadSubmissions() {
    this.loading = true;
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

  loadFields() {
    this.formService.getFormStructure().subscribe({
      next: (data) => {
        // Usamos el spread operator para evitar el error de mutación de Sonar
        this.fields = [...data].sort((a, b) => (a.sortOrder || 0) - (b.sortOrder || 0));
      },
      error: (err) => console.error('Error cargando configuración del formulario', err)
    });
  }

  moveField(index: number, direction: 'up' | 'down') {
    const newIndex = direction === 'up' ? index - 1 : index + 1;
    
    if (newIndex >= 0 && newIndex < this.fields.length) {
      // 1. Intercambio de posiciones en el array local
      const temp = this.fields[index];
      this.fields[index] = this.fields[newIndex];
      this.fields[newIndex] = temp;

      // 2. Actualizamos los sortOrder de todos según su posición actual
      this.fields.forEach((field, i) => field.sortOrder = i + 1);

      // 3. Persistimos los cambios
      this.saveNewOrder();
    }
  }

  saveNewOrder() {
    console.log('Sincronizando nuevo orden con el servidor...', this.fields);
    // Para cada campo modificado, llamamos al backend
    this.fields.forEach(field => {
      // Asumiendo que has creado updateField en tu servicio
      this.formService.updateField(field).subscribe({
        error: (err) => console.error(`Error al actualizar campo ${field.id}`, err)
      });
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