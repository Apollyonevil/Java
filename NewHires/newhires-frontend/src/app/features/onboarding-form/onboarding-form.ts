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
    next: (res) => {
      console.log('Campos recibidos del servidor:', res);
      
      // Ajustamos los datos que vienen del servidor a lo que espera el Front
      this.fields = res.map((field: any) => ({
        ...field,
        // Pasamos 'TEXT' a 'text', 'PDF' a 'file', etc.
        type: field.type.toLowerCase() === 'pdf' ? 'file' : field.type.toLowerCase(),
        // Si el back manda 'required', lo asignamos a 'isRequired'
        isRequired: field.required !== undefined ? field.required : field.isRequired
      }));

      this.buildForm();
    },
    error: (err) => {
      console.error('El servidor no responde o hay error de CORS:', err);
    }
  });
}

  buildForm() {
    this.fields.forEach(field => {
      // Usamos field.isRequired (como está en tu modelo de base de datos)
      const validators = field.isRequired ? [Validators.required] : [];
      // Importante: usamos field.id.toString() porque los nombres de control deben ser strings
      this.dynamicForm.addControl(field.id.toString(), this.fb.control('', validators));
    });
  }

  onFileChange(event: any, fieldId: number) {
    const file = event.target.files[0];
    if (file) {
      this.fileMap.set(fieldId.toString(), file);
      // Marcamos el control como sucio para que la validación sepa que hay algo
      this.dynamicForm.get(fieldId.toString())?.setValue(file.name);
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