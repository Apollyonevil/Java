import { Component, OnInit, ChangeDetectorRef } from '@angular/core'; // 1. Cambiamos NgZone por ChangeDetectorRef
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
    private cdr: ChangeDetectorRef // 2. Inyectamos el detector de cambios
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
        this.cdr.detectChanges(); // 3. FORZAMOS RENDERIZADO
      },
      error: () => { this.loading = false; this.cdr.detectChanges(); }
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
        this.cdr.detectChanges(); // 3. FORZAMOS RENDERIZADO
      }
    });
  }

  generateInvitation() {
    if (!this.newCandidateName || !this.newCandidateEmail || this.isGenerating) return;
    this.isGenerating = true;

    this.formService.createInvitation(this.newCandidateName, this.newCandidateEmail).subscribe({
      next: (newSub) => {
        // Añadimos y forzamos nueva referencia de array
        this.submissions = [newSub, ...this.submissions];
        this.newCandidateName = '';
        this.newCandidateEmail = '';
        this.isGenerating = false;
        this.cdr.detectChanges(); // 4. LA CLAVE: Forzar aquí
      },
      error: () => { this.isGenerating = false; this.cdr.detectChanges(); }
    });
  }


saveField() {
  if (!this.editingField) return;
  this.loading = true;

  // Actualizamos opciones
  if (this.optionsText) {
    this.editingField.options = this.optionsText.split(',').map(s => s.trim());
  }

  const obs = this.editingField.id 
    ? this.formService.updateField(this.editingField) 
    : this.formService.saveField(this.editingField);

  obs.subscribe({
    next: (savedField) => {
      
      this.loadFormStructure(); 
      this.editingField = null;
      this.loading = false;
      this.cdr.detectChanges();
    },
    error: () => {
      this.loading = false;
      this.cdr.detectChanges();
    }
  });
}



editField(field: FieldDefinition) {

  this.editingField = null;
  this.cdr.detectChanges();

  // 2. Clonamos el objeto para el formulario
  // Usamos la técnica del spread o JSON para asegurar que es una copia limpia
  this.editingField = { ...field };
  
  // 3. Cargamos las opciones (si existen)
  this.optionsText = field.options ? field.options.join(', ') : '';

  // 4. Forzamos el renderizado para que el *ngIf="editingField" se active
  this.cdr.detectChanges();
  
  console.log("Editando campo:", this.editingField);
}

moveField(index: number, direction: 'up' | 'down') {
  const newIndex = direction === 'up' ? index - 1 : index + 1;
  
  // 1. Validar límites
  if (newIndex < 0 || newIndex >= this.fields.length) return;

  // 2. Intercambio visual e inmutable
  const list = [...this.fields];
  const itemA = { ...list[index] };
  const itemB = { ...list[newIndex] };

  // 3. Intercambiamos los valores de sortOrder
  const tempOrder = itemA.sortOrder;
  itemA.sortOrder = itemB.sortOrder;
  itemB.sortOrder = tempOrder;

  // 4. Actualizamos la lista local para que el usuario vea el cambio ya
  list[index] = itemB;
  list[newIndex] = itemA;
  this.fields = [...list];
  this.cdr.detectChanges();

  this.formService.updateField(itemA).subscribe({
    next: () => {
      this.formService.updateField(itemB).subscribe({
        next: () => console.log('Orden sincronizado con éxito'),
        error: (err) => console.error('Error al guardar item B', err)
      });
    },
    error: (err) => console.error('Error al guardar item A', err)
  });
}


  deleteField(id: string) {
    if (confirm('¿Eliminar?')) {
      this.formService.deleteField(id).subscribe(() => {
        this.fields = this.fields.filter(f => f.id !== id);
        this.cdr.detectChanges();
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
        this.cdr.detectChanges();
      },
      error: () => { this.sendingEmails[id] = false; this.cdr.detectChanges(); }
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
    this.cdr.detectChanges();
  }
}