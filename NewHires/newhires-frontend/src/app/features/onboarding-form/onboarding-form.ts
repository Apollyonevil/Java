import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { FormService } from '../../core/services/form';
import { FieldDefinition } from '../../shared/models/form.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-onboarding-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './onboarding-form.html',
  styleUrls: ['./onboarding-form.css']
})
export class OnboardingFormComponent implements OnInit {
  dynamicForm: FormGroup;
  fields: FieldDefinition[] = [];
  token: string = '';
  fileMap: Map<string, File> = new Map();
  fileErrors: { [key: string]: string } = {};
  loading: boolean = true;
  isSubmitted: boolean = false;

  constructor(
    private readonly fb: FormBuilder,
    private readonly formService: FormService,
    private readonly route: ActivatedRoute,
    private readonly cdr: ChangeDetectorRef
  ) {
    this.dynamicForm = this.fb.group({});
  }

  ngOnInit() {
    this.token = this.route.snapshot.queryParamMap.get('token') || '';
    this.loadStructure();
  }

  private loadStructure() {
    this.formService.getFormStructure().subscribe({
      next: (data) => {
        this.fields = data.map(field => {
          const originalType = field.type.toLowerCase();
          let normalizedType = originalType;
          if (normalizedType === 'pdf' || normalizedType === 'jpg') {
            normalizedType = 'file';
          }
          return {
            ...field,
            type: normalizedType,
            originalType: originalType,
            options: field.options || []
          };
        }).sort((a, b) => (a.sortOrder ?? 0) - (b.sortOrder ?? 0));

        this.buildForm();
        this.loading = false;
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Error cargando la estructura:', err);
        this.loading = false;
        this.cdr.markForCheck();
      }
    });
  }

  private buildForm() {
    const group: any = {};
    this.fields.forEach(field => {
      group[field.id] = [
        '',
        field.required ? [Validators.required] : []
      ];
    });
    this.dynamicForm = this.fb.group(group);
  }

  onFileChange(event: any, fieldId: string, placeholder: string, originalType: string) {
    const file = event.target.files[0];
    if (!file) return;

    const validationError = this.validateFileType(file, originalType);
    if (validationError) {
      this.fileErrors[fieldId] = validationError;
      event.target.value = '';
      this.dynamicForm.get(fieldId)?.setValue('');
      this.fileMap.delete(fieldId);
      this.cdr.markForCheck();
      return;
    }

    delete this.fileErrors[fieldId];
    const renamedFile = this.renameFile(file, placeholder);
    this.fileMap.set(fieldId, renamedFile);
    this.dynamicForm.get(fieldId)?.setValue(renamedFile.name);
    this.cdr.markForCheck();
  }

  private validateFileType(file: File, originalType: string): string | null {
    const fileName = file.name.toLowerCase();

    if (originalType === 'pdf') {
      if (!fileName.endsWith('.pdf') && file.type !== 'application/pdf') {
        return 'Solo se permiten archivos PDF (.pdf)';
      }
    }

    if (originalType === 'jpg') {
      if (!fileName.endsWith('.jpg') && !fileName.endsWith('.jpeg')
          && file.type !== 'image/jpeg') {
        return 'Solo se permiten imágenes JPG (.jpg, .jpeg)';
      }
    }

    return null;
  }

  getAcceptType(originalType: string): string {
    if (originalType === 'pdf') return '.pdf,application/pdf';
    if (originalType === 'jpg') return '.jpg,.jpeg,image/jpeg';
    return '*';
  }

  private renameFile(file: File, placeholder: string): File {
    if (!placeholder || !placeholder.startsWith('Formato nombre archivo:')) {
      return file;
    }

    const afterColon = placeholder.replace('Formato nombre archivo:', '').trim();
    const prefixMatch = afterColon.match(/^(.+?)\s+apellido/i);
    const prefix = prefixMatch ? prefixMatch[1].trim().toUpperCase() : '';

    const { nombre, apellidos } = this.getNombreYApellidos();
    const nombreFormateado = apellidos && nombre
      ? `${apellidos}, ${nombre}`
      : (apellidos || nombre || 'candidato');
    const extension = file.name.split('.').pop();

    const nuevoNombre = prefix
      ? `${prefix} ${nombreFormateado}.${extension}`
      : `${nombreFormateado}.${extension}`;

    return new File([file], nuevoNombre, { type: file.type });
  }

  private getNombreYApellidos(): { nombre: string, apellidos: string } {
    const nombreFieldId = 'bafb0b5c-212e-11f1-8314-a6ac6c94ec55';
    const apellidosFieldId = '5400ca90-5376-433d-9977-416786a83cac';

    const nombre = this.dynamicForm.get(nombreFieldId)?.value?.trim() || '';
    const apellidos = this.dynamicForm.get(apellidosFieldId)?.value?.trim() || '';

    return { nombre, apellidos };
  }

  onSubmit() {
    if (this.dynamicForm.valid) {
      this.loading = true;

      const textResponses = Object.keys(this.dynamicForm.value)
        .filter(key => !this.fileMap.has(key))
        .map(key => ({
          fieldDefinitionId: key,
          value: this.dynamicForm.value[key]
        }));

      this.formService.submitForm(this.token, textResponses, this.fileMap).subscribe({
        next: () => {
          this.isSubmitted = true;
          this.loading = false;
          this.cdr.markForCheck();
        },
        error: (err) => {
          console.error('Error al enviar:', err);
          this.loading = false;
          this.cdr.markForCheck();
          alert('Error al enviar. El enlace podría haber caducado.');
        }
      });
    } else {
      this.dynamicForm.markAllAsTouched();
    }
  }
}