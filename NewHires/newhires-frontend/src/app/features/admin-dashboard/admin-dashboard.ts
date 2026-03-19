import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
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
  isGenerating: boolean = false;
  sendingEmails: { [key: string]: boolean } = {};
  editingField: any = null;
  optionsText: string = '';
  newCandidateName: string = '';
  newCandidateEmail: string = '';

  constructor(
    private formService: FormService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.refreshData();
  }

  refreshData() {
    this.loadFormStructure();
    this.loadSubmissions();
  }

  loadFormStructure() {
    this.loading = true;
    this.formService.getFormStructure().subscribe({
      next: (data) => {
        this.fields = [...data].sort((a, b) => (a.sortOrder ?? 0) - (b.sortOrder ?? 0));
        this.loading = false;
        this.cdr.markForCheck();
      },
      error: () => { this.loading = false; }
    });
  }

  loadSubmissions() {
    this.formService.getAllSubmissions().subscribe({
      next: (data) => {
        this.submissions = [...data].sort((a, b) => {
          const dateA = a.submittedAt ? new Date(a.submittedAt).getTime() : 0;
          const dateB = b.submittedAt ? new Date(b.submittedAt).getTime() : 0;
          return dateB - dateA;
        });
        this.cdr.markForCheck();
      }
    });
  }

  generateInvitation() {
    if (!this.newCandidateName || !this.newCandidateEmail || this.isGenerating) return;
    this.isGenerating = true;

    this.formService.createInvitation(this.newCandidateName, this.newCandidateEmail).subscribe({
      next: (newSub) => {
        this.submissions = [newSub, ...this.submissions];
        this.newCandidateName = '';
        this.newCandidateEmail = '';
        this.isGenerating = false;
        this.cdr.markForCheck();
      },
      error: () => { this.isGenerating = false; }
    });
  }

  saveField() {
    if (!this.editingField) return;
    this.loading = true;

    if (this.editingField.type === 'SELECT' && this.optionsText) {
      this.editingField.options = this.optionsText.split(',').map((o: string) => o.trim());
    }

    // Si sortOrder es null, asigna el índice actual
    if (this.editingField.sortOrder == null) {
      const index = this.fields.findIndex(f => f.id === this.editingField.id);
      this.editingField.sortOrder = index !== -1 ? index : this.fields.length;
    }
    
    const request$ = this.editingField.id
      ? this.formService.updateField(this.editingField)
      : this.formService.saveField(this.editingField);

    request$.subscribe({
      next: (savedField) => {
        const index = this.fields.findIndex(f => f.id === savedField.id);
        if (index !== -1) {
          const newFields = [...this.fields];
          newFields[index] = savedField;
          this.fields = newFields;
        } else {
          this.fields = [...this.fields, savedField];
        }
        this.editingField = null;
        this.optionsText = '';
        this.loading = false;
        this.cdr.markForCheck();
      },
      error: (err) => {
        this.loading = false;
        console.error(err);
      }
    });
  }

    deleteSubmission(id: string) {
      if (confirm('¿Eliminar candidato?')) {
        this.formService.deleteSubmission(id).subscribe(() => {
          this.submissions = this.submissions.filter(s => s.id !== id);
          this.cdr.markForCheck();
        });
      }
    }

  editField(field: FieldDefinition) {
    this.editingField = { ...field };
    this.optionsText = field.options ? field.options.join(', ') : '';
  }

  moveField(index: number, direction: 'up' | 'down') {
    const newIndex = direction === 'up' ? index - 1 : index + 1;
    if (newIndex < 0 || newIndex >= this.fields.length) return;

    const list = [...this.fields];
    [list[index], list[newIndex]] = [list[newIndex], list[index]];
    list.forEach((field, i) => {
      field.sortOrder = i;
      this.formService.updateField(field).subscribe();
    });

    this.fields = list;
    this.cdr.markForCheck();
  }

  deleteField(id: string) {
    if (confirm('¿Eliminar?')) {
      this.formService.deleteField(id).subscribe(() => {
        this.fields = this.fields.filter(f => f.id !== id);
        this.cdr.markForCheck();
      });
    }
  }

  sendEmail(id: string) {
    if (this.sendingEmails[id]) return;
    this.sendingEmails[id] = true;
    this.formService.sendOnboardingEmail(id).subscribe({
      next: () => {
        this.sendingEmails[id] = false;
        alert('Email enviado');
        this.cdr.markForCheck();
      },
      error: () => { this.sendingEmails[id] = false; }
    });
  }

  copyTokenLink(token: string) {
    const url = `${window.location.origin}/onboarding?token=${token}`;
    navigator.clipboard.writeText(url);
    alert('Link copiado');
  }

  addNewField() {
    this.editingField = { label: '', type: 'text', required: false, sortOrder: this.fields.length };
    this.optionsText = '';
  }
}