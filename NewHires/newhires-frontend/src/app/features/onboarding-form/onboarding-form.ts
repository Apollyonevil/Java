import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { FormService } from '../../core/services/form';
import { FieldDefinition } from '../../shared/models/form.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-onboarding-form',
  templateUrl: './onboarding-form.html',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule] 
})
export class OnboardingFormComponent implements OnInit {
  dynamicForm: FormGroup;
  fields: FieldDefinition[] = [];
  token: string = '';
  fileMap: Map<string, File> = new Map();

  constructor(
    private fb: FormBuilder,
    private formService: FormService,
    private route: ActivatedRoute
  ) { 
    this.dynamicForm = this.fb.group({});
  } 

  ngOnInit() {
    this.token = this.route.snapshot.queryParamMap.get('token') || '';

    this.formService.getFormStructure().subscribe({
      next: (data) => {
        // Normalizamos los datos que vienen del backend
        this.fields = data.map(field => ({
          ...field,
          // Aseguramos minúsculas para que el ngSwitch funcione siempre
          type: field.type.trim().toLowerCase(),
          // Usamos la propiedad correcta del modelo
          isRequired: field.isRequired 
        })).sort((a, b) => (a.sortOrder ?? 0) - (b.sortOrder ?? 0));

        this.buildForm();
      },
      error: (err) => {
        console.error('Error cargando estructura del formulario:', err);
      }
    });
  }

  buildForm() {
    this.fields.forEach(field => {
      const validators = field.isRequired ? [Validators.required] : [];
      // Usamos el ID como string para el nombre del control
      const controlName = field.id.toString();
      this.dynamicForm.addControl(controlName, this.fb.control('', validators));
    });
  }

  onFileChange(event: any, fieldId: string) { 
    const file = event.target.files[0];
    if (file) {
      const idStr = fieldId.toString();
      this.fileMap.set(idStr, file); 
      // Seteamos el nombre del archivo para que el validador 'required' lo dé por válido
      this.dynamicForm.get(idStr)?.setValue(file.name);
    }
  }

  onSubmit() {
    if (this.dynamicForm.valid) {
      const textResponses = Object.keys(this.dynamicForm.value)
        .filter(key => !this.fileMap.has(key)) 
        .map(key => ({ 
          fieldDefinitionId: key, 
          value: this.dynamicForm.value[key] 
        }));

      this.formService.submitForm(this.token, textResponses, this.fileMap)
        .subscribe({
          next: () => alert('¡Documentación enviada correctamente!'),
          error: () => alert('Error al enviar. Revisa el token o los archivos.')
        });
    }
  }
}