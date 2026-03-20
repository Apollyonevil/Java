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
          let normalizedType = field.type.toLowerCase();
          if (normalizedType === 'pdf' || normalizedType === 'jpg') {
            normalizedType = 'file';
          }
          return {
            ...field,
            type: normalizedType,
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

  onFileChange(event: any, fieldId: string, placeholder: string) {
    const file = event.target.files[0];
    if (file) {
      const renamedFile = this.renameFile(file, placeholder);
      this.fileMap.set(fieldId, renamedFile);
      this.dynamicForm.get(fieldId)?.setValue(renamedFile.name);
    }
  }

  private renameFile(file: File, placeholder: string): File {
    if (!placeholder || !placeholder.startsWith('Formato nombre archivo:')) {
      return file;
    } 

    const afterColon = placeholder.replace('Formato nombre archivo:', '').trim();
    const prefixMatch = afterColon.match(/^(.+?)\s+apellido/i);
    const prefix = prefixMatch ? prefixMatch[1].trim().toUpperCase() : '';

    const nombreCompleto = this.getNombreCompleto();
    const extension = file.name.split('.').pop();

    const nuevoNombre = prefix
      ? `${prefix} ${nombreCompleto}.${extension}`
      : `${nombreCompleto}.${extension}`;

    return new File([file], nuevoNombre, { type: file.type });
  }

  private getNombreCompleto(): string {
    if (this.fields.length === 0) return 'candidato';
    const primerCampoId = this.fields[0].id;
    const valor = this.dynamicForm.get(primerCampoId)?.value;
    return valor ? valor.trim() : 'candidato';
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