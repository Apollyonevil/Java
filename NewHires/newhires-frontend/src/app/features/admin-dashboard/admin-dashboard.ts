import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { FormService } from '../../core/services/form';
import { FieldDefinition, Submission } from '../../shared/models/form.model';

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-dashboard.html'
})
export class AdminDashboardComponent implements OnInit {
  fields: FieldDefinition[] = [];
  submissions: Submission[] = [];
  loading: boolean = false;

  editingField: any = null;
  optionsText: string = '';

  newCandidateName: string = '';
  newCandidateEmail: string = '';

  constructor(private formService: FormService) {}

  ngOnInit(): void {
    this.loadFormStructure();
    this.loadSubmissions();
  }

  loadFormStructure() {
    this.loading = true;
    this.formService.getFormStructure().subscribe({
      next: (data) => {
        this.fields = data.sort((a, b) => (a.sortOrder ?? 0) - (b.sortOrder ?? 0));
        this.loading = false;
      },
      error: () => this.loading = false
    });
  }

  loadSubmissions() {
    this.formService.getAllSubmissions().subscribe(data => this.submissions = data);
  }

  generateInvitation() {
    if (!this.newCandidateName || !this.newCandidateEmail) return;

    this.formService.createInvitation(this.newCandidateName, this.newCandidateEmail).subscribe({
      next: () => {
        this.newCandidateName = '';
        this.newCandidateEmail = '';
        this.loadSubmissions(); // Refresca la tabla tras generar
      },
      error: (err) => alert('Error al generar: ' + err.message)
    });
  }

  saveField() {
    if (this.optionsText) {
      this.editingField.options = this.optionsText.split(',').map(s => s.trim());
    }
    const obs = this.editingField.id 
      ? this.formService.updateField(this.editingField) 
      : this.formService.saveField(this.editingField);

    obs.subscribe(() => {
      this.loadFormStructure();
      this.editingField = null;
    });
  }

  addNewField() {
    this.editingField = { label: '', type: 'text', required: false, sortOrder: this.fields.length };
    this.optionsText = '';
  }

  editField(field: FieldDefinition) {
    this.editingField = { ...field };
    this.optionsText = field.options ? field.options.join(', ') : '';
  }

  deleteField(id: string) {
    if (confirm('¿Eliminar?')) this.formService.deleteField(id).subscribe(() => this.loadFormStructure());
  }

  copyTokenLink(token: string) {
    const url = `${window.location.origin}/onboarding?token=${token}`;
    navigator.clipboard.writeText(url);
    alert('¡Link copiado!');
  }

  sendEmail(id: string) {
    this.formService.sendOnboardingEmail(id).subscribe(() => alert('Email enviado'));
  }

  moveField(index: number, direction: string) { /* Lógica de orden opcional */ }
}