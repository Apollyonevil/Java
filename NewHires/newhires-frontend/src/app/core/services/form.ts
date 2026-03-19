import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { FieldDefinition, Submission } from '../../shared/models/form.model';

@Injectable({
  providedIn: 'root'
})
export class FormService {
  // URLs base para separar responsabilidades
  private readonly userUrl = 'http://localhost:8080/api/v1/forms';
  private readonly adminUrl = 'http://localhost:8080/api/admin/forms';

  constructor(private readonly http: HttpClient) {}

  /**
   * MÉTODOS PARA EL CANDIDATO (USER)
   */

  getFormStructure(): Observable<FieldDefinition[]> {
    return this.http.get<FieldDefinition[]>(`${this.userUrl}/structure`);
  }

  submitForm(token: string, textResponses: any[], files: Map<string, File>): Observable<void> {
    const formData = new FormData();
    
    formData.append('token', token);
    formData.append('responses', new Blob([JSON.stringify(textResponses)], {
      type: 'application/json'
    }));

    files.forEach((file, fieldId) => {
      formData.append(fieldId, file, file.name);
    });

    return this.http.post<void>(`${this.userUrl}/submit`, formData);
  }

  // NUEVO: Crea la invitación, genera el token y guarda al candidato como PENDING
  createInvitation(name: string, email: string): Observable<Submission> {
    return this.http.post<Submission>(`${this.adminUrl}/invite`, {
      candidateName: name,
      email: email
    });
  }

  // Obtiene la lista de todos los candidatos (incluidos los pendientes)
  getAllSubmissions(): Observable<Submission[]> {
    return this.http.get<Submission[]>(`${this.adminUrl}/submissions`);
  }

  // Gestión de la estructura del formulario (Editor de campos)
  updateField(field: FieldDefinition): Observable<FieldDefinition> {
    return this.http.put<FieldDefinition>(`${this.adminUrl}/fields/${field.id}`, field);
  }

  saveField(field: FieldDefinition): Observable<FieldDefinition> {
    return this.http.post<FieldDefinition>(this.adminUrl, field);
  }

  deleteField(id: string): Observable<void> {
    return this.http.delete<void>(`${this.adminUrl}/${id}`);
  }

  deleteSubmission(id: string): Observable<void> {
  return this.http.delete<void>(`${this.adminUrl}/submissions/${id}`);
}

  // Envío manual de emails (si decides implementarlo en el back)
  sendOnboardingEmail(employeeId: string): Observable<any> {
    return this.http.post(`${this.adminUrl}/send-email/${employeeId}`, {});
  }
}