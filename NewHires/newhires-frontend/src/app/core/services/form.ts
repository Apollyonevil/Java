import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { FieldDefinition, Submission } from '../../shared/models/form.model';


@Injectable({
  providedIn: 'root'
})
export class FormService {
  private readonly apiUrl = `${environment.apiUrl}/forms`; // Apunta a http://localhost:8080/api/forms

  constructor(private http: HttpClient) {}

  // 1. Obtener los campos dinámicos
  getFormStructure(): Observable<FieldDefinition[]> {
    return this.http.get<FieldDefinition[]>(this.apiUrl);
  }

  // 2. Enviar el formulario (Texto + Archivos)
  submitForm(token: string, textResponses: any[], files: Map<string, File>): Observable<void> {
    const formData = new FormData();
    
    // El token es vital para la seguridad que pusimos en el Back
    formData.append('token', token);
    
    // Pasamos las respuestas de texto como un String JSON (tal cual espera el Back)
    formData.append('responses', JSON.stringify(textResponses));

    // Añadimos cada archivo al FormData
    files.forEach((file, fieldId) => {
      formData.append('files', file, file.name);
      // Nota: El Back espera recibir esto y mapearlo por el ID del campo
    });

    return this.http.post<void>(`${this.apiUrl}/submit`, formData);
   }

    getAllSubmissions(): Observable<Submission[]> {
      return this.http.get<Submission[]>(`${environment.apiUrl}/admin/forms/submissions`);
    }

    updateField(field: FieldDefinition): Observable<FieldDefinition> {
    return this.http.put<FieldDefinition>(`${this.apiUrl}/fields/${field.id}`, field);
  }

  
}