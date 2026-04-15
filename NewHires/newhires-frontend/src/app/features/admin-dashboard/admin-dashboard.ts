import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { FormService } from '../../core/services/form';
import { FieldDefinition } from '../../shared/models/form.model';
import { Submission } from '../../shared/models/submission.model';
import { AuthService } from '../../core/services/auth';

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule, DatePipe],
  templateUrl: 'admin-dashboard.html',
  styleUrls: ['admin-dashboard.css']
})
export class AdminDashboardComponent implements OnInit {
  fields: FieldDefinition[] = [];
  submissions: Submission[] = [];
  adminUsers: any[] = [];
  formVersions: any[] = [];
  loading: boolean = false;
  isGenerating: boolean = false;
  sendingEmails: { [key: string]: boolean } = {};
  editingField: any = null;
  editingUser: any = null;
  optionsText: string = '';
  newCandidateName: string = '';
  newCandidateEmail: string = '';
  newVersionDescription: string = '';
  activeTab: string = 'candidatos';
  rejectingSubmissionId: string | null = null;
  rejectReason: string = '';
  selectedSubmission: any = null;
  submissionDetail: any = null;
  
  currentUser: string = '';
  currentRole: string = '';

  errorMessage: string = '';
  showErrorModal: boolean = false;
  showSuccessMessage: boolean = false;

  constructor(
    private formService: FormService,
    private cdr: ChangeDetectorRef,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.currentUser = sessionStorage.getItem('username') || '';
    this.currentRole = sessionStorage.getItem('role') || '';
    
    if (!this.currentUser) {
      this.authService.logout();
      return;
    }

    this.refreshData();
  }

  isAdminPrincipal(): boolean {
    return this.currentRole === 'ADMIN' || this.currentUser === 'admin';
  }

  refreshData() {
    this.loadFormStructure();
    this.loadSubmissions();
    
    if (this.isAdminPrincipal()) {
      this.loadAdminUsers();
    }
    this.loadFormVersions();
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
          const dateA = a.submittedAt ? new Date(a.submittedAt).getTime() : new Date(a.createdAt || 0).getTime();
          const dateB = b.submittedAt ? new Date(b.submittedAt).getTime() : new Date(b.createdAt || 0).getTime();
          return dateB - dateA;
        });
        this.cdr.markForCheck();
      }
    });
  }

  loadAdminUsers() {
    this.formService.getAdminUsers().subscribe({
      next: (data) => {
        this.adminUsers = data;
        this.cdr.markForCheck();
      }
    });
  }

  loadFormVersions() {
    this.formService.getFormVersions().subscribe({
      next: (data) => {
        this.formVersions = data;
        this.cdr.markForCheck();
      }
    });
  }

  generateInvitation() {
    if (!this.newCandidateName || !this.newCandidateEmail || this.isGenerating) return;
    this.isGenerating = true;

    this.formService.createInvitation(
      this.newCandidateName, 
      this.newCandidateEmail
    ).subscribe({
      next: () => {
        setTimeout(() => {
          this.loadSubmissions();
          this.newCandidateName = '';
          this.newCandidateEmail = '';
          this.isGenerating = false;
          this.cdr.markForCheck();
        }, 500);
      },
      error: () => { 
        this.isGenerating = false;
        alert('Error al generar la invitación.');
      }
    });
  }

  approveSubmission(id: string) {
    if (confirm('¿Confirmar aprobación de la documentación?')) {
      this.formService.approveSubmission(id).subscribe({
        next: (updated) => {
          const index = this.submissions.findIndex(s => s.id === id);
          if (index !== -1) {
            const newSubmissions = [...this.submissions];
            newSubmissions[index] = updated;
            this.submissions = newSubmissions;
          }
          this.submissionDetail = null;
          this.cdr.markForCheck();
          alert('Candidato aprobado correctamente');
        },
        error: (err) => console.error('Error al aprobar:', err)
      });
    }
  }

  openRejectModal(id: string) {
    this.rejectingSubmissionId = id;
    this.rejectReason = '';
  }

  confirmReject() {
    if (!this.rejectingSubmissionId || !this.rejectReason) return;

    this.formService.rejectSubmission(this.rejectingSubmissionId, this.rejectReason).subscribe({
      next: (updated) => {
        const index = this.submissions.findIndex(s => s.id === this.rejectingSubmissionId);
        if (index !== -1) {
          const newSubmissions = [...this.submissions];
          newSubmissions[index] = updated;
          this.submissions = newSubmissions;
        }
        this.rejectingSubmissionId = null;
        this.rejectReason = '';
        this.cdr.markForCheck();
      }
    });
  }

  saveField() {
    if (!this.editingField) return;
    this.loading = true;

    if (this.editingField.type === 'SELECT' && this.optionsText) {
      this.editingField.options = this.optionsText.split(',').map((o: string) => o.trim());
    }

    const request$ = this.editingField.id
      ? this.formService.updateField(this.editingField)
      : this.formService.saveField(this.editingField);

    request$.subscribe({
      next: (savedField) => {
        this.loadFormStructure();
        this.editingField = null;
        this.optionsText = '';
        this.loading = false;
        this.loadFormVersions();
        this.cdr.markForCheck();
      },
      error: () => { this.loading = false; }
    });
  }

  editField(field: FieldDefinition) {
    this.editingField = { ...field };
    this.optionsText = field.options ? field.options.join(', ') : '';
  }

toggleField(id: string) {
  this.formService.toggleField(id).subscribe({
    next: (response) => {
      // 1. Mapeamos el array creando objetos TOTALMENTE nuevos
      // Esto fuerza a Angular a re-evaluar todo
      this.fields = this.fields.map(f => {
        if (f.id === id) {
          return { ...f, active: !f.active }; // Copia con el estado invertido
        }
        return { ...f }; // Copia exacta
      });

      console.log('Array actualizado:', this.fields);
      this.cdr.detectChanges(); // Forzamos la detección de cambios
    }
  });
}

get activeFields(): FieldDefinition[] {
  return this.fields.filter(f => 
    (f as any).active == true || (f as any).active == 1 || (f as any).active === undefined
  );
}

get inactiveFields(): FieldDefinition[] {
  return this.fields.filter(f => 
    (f as any).active == false || (f as any).active == 0
  );
}


  moveField(index: number, direction: 'up' | 'down') {
  const newIndex = direction === 'up' ? index - 1 : index + 1;
  if (newIndex < 0 || newIndex >= this.activeFields.length) return;


  const active = [...this.activeFields];
  [active[index], active[newIndex]] = [active[newIndex], active[index]];

  active.forEach((field, i) => {
    const updated = { ...field, sortOrder: i };
    this.formService.updateField(updated).subscribe();
  });

  this.loadFormStructure();
  this.cdr.markForCheck();
}

  deleteField(id: string) {
  if (!confirm('¿Estás seguro de que deseas eliminar este campo definitivamente?')) return;

  this.formService.deleteField(id).subscribe({
    next: () => {
      this.loadFormStructure();
      alert("Campo eliminado con éxito");
      this.cdr.markForCheck();
    },
    error: (err) => {
      console.error('Error completo recibido:', err); // Para que lo veas en la consola (F12)

      // Intentamos sacar el mensaje de varias formas según cómo responda Spring
      let mensajeFinal = "Error desconocido al eliminar";

      if (err.error && typeof err.error === 'object' && err.error.message) {
        // Caso 1: Spring envía un JSON estándar con campo "message"
        mensajeFinal = err.error.message;
      } else if (typeof err.error === 'string') {
        // Caso 2: El servidor envió solo texto plano
        mensajeFinal = err.error;
      } else if (err.message) {
        // Caso 3: Error de la propia petición HTTP
        mensajeFinal = err.message;
      }

      this.errorMessage = mensajeFinal;
      alert("Atención: " + mensajeFinal);
      this.cdr.markForCheck();
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

  viewDetail(id: string) {
    this.formService.getSubmissionDetail(id).subscribe({
      next: (detail) => {
        this.selectedSubmission = id;
        this.submissionDetail = detail;
        this.cdr.markForCheck();
      }
    });
  }

  downloadFile(fileResourceId: string) {
    const credentials = this.authService.getCredentials();
    const url = `http://localhost:8080/api/documents/download/${fileResourceId}`;

    fetch(url, {
      headers: { 'Authorization': `Basic ${credentials}` }
    })
    .then(response => response.blob())
    .then(blob => {
      const link = document.createElement('a');
      link.href = URL.createObjectURL(blob);
      link.download = fileResourceId;
      link.click();
      URL.revokeObjectURL(link.href);
    });
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


  copyTokenLink(token: string | undefined | null) {
  console.log('Intentando copiar token:', token);

  if (!token) {
    alert('El servidor no ha enviado ningún token para este candidato. Revisa el DTO en el Backend.');
    return;
  }

  const url = `${window.location.origin}/onboarding?token=${token}`;


  if (navigator.clipboard && window.isSecureContext) {
    navigator.clipboard.writeText(url)
      .then(() => alert('¡Link copiado al portapapeles!'))
      .catch(err => {
        console.error('Error con navigator.clipboard:', err);
        this.fallbackCopyTextToClipboard(url);
      });
  } else {

    this.fallbackCopyTextToClipboard(url);
  }
}


private fallbackCopyTextToClipboard(text: string) {
  const textArea = document.createElement("textarea");
  textArea.value = text;
  document.body.appendChild(textArea);
  textArea.focus();
  textArea.select();
  try {
    document.execCommand('copy');
    alert('Link copiado (vía fallback)');
  } catch (err) {
    alert('No se pudo copiar el link. Por favor, hazlo manualmente.');
  }
  document.body.removeChild(textArea);
}


  isExpired(expiresAt: string | null | undefined): boolean {
    if (!expiresAt) return false;
    return new Date(expiresAt) < new Date();
  }

  renewToken(id: string) {
    this.formService.renewToken(id).subscribe({
      next: (updated) => {
        const index = this.submissions.findIndex(s => s.id === id);
        if (index !== -1) {
          const newSubmissions = [...this.submissions];
          newSubmissions[index] = updated;
          this.submissions = newSubmissions;
          this.cdr.markForCheck();
          alert('Token renovado y email enviado');
        }
      }
    });
  }

  addNewField() {
    this.editingField = { label: '', type: 'TEXT', required: false, sortOrder: this.fields.length };
    this.optionsText = '';
  }

  addNewUser() {
    this.editingUser = { username: '', password: '', role: 'EMPLOYEE' };
  }

  editUser(user: any) {
    this.editingUser = { ...user, password: '' };
  }

  saveUser() {
    if (!this.editingUser.username) return;

    const request$ = this.editingUser.id
      ? this.formService.updateAdminUser(this.editingUser.id, this.editingUser.username, this.editingUser.password, this.editingUser.role)
      : this.formService.createAdminUser(this.editingUser.username, this.editingUser.password, this.editingUser.role);

    request$.subscribe({
      next: () => {
        this.editingUser = null;
        this.loadAdminUsers();
        this.cdr.markForCheck();
      }
    });
  }

  deleteUser(id: string) {
    if (confirm('¿Eliminar administrador?')) {
      this.formService.deleteAdminUser(id).subscribe(() => {
        this.adminUsers = this.adminUsers.filter(u => u.id !== id);
        this.cdr.markForCheck();
      });
    }
  }

  createVersion() {
    if (!this.newVersionDescription) return;
    this.formService.createFormVersion(this.currentUser, this.newVersionDescription).subscribe({
      next: () => {
        this.newVersionDescription = '';
        this.loadFormVersions();
        this.cdr.markForCheck();
      }
    });
  }

  activateVersion(id: string) {
    if (confirm('¿Activar esta versión?')) {
      this.formService.activateFormVersion(id).subscribe({
        next: () => {
          this.loadFormVersions();
          this.loadFormStructure();
          this.cdr.markForCheck();
        }
      });
    }
  }

  deleteVersion(id: string) {
    if (confirm('¿Eliminar esta versión?')) {
      this.formService.deleteFormVersion(id).subscribe({
        next: () => {
          this.formVersions = this.formVersions.filter(v => v.id !== id);
          this.cdr.markForCheck();
        },
        error: () => {
          alert('No se puede eliminar la versión activa');
        }
      });
    }
  }

  logout() {
    this.authService.logout();
  }
}