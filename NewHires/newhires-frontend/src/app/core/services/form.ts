import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { FieldDefinition, Submission } from '../../shared/models/form.model';
import { AuthService } from './auth';

@Injectable({
  providedIn: 'root'
})
export class FormService {
  private readonly http = inject(HttpClient);
  private readonly authService = inject(AuthService);
  
  private readonly userUrl = 'http://localhost:8080/api/v1/forms';
  private readonly adminUrl = 'http://localhost:8080/api/admin/forms';

private getAdminHeaders(): { headers: HttpHeaders } {
  const credentials = this.authService.getCredentials();
  return {
    headers: new HttpHeaders({
      'Authorization': `Basic ${credentials}`
    })
  };
}

  // MÉTODOS PARA EL CANDIDATO (USER)

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

  // MÉTODOS PARA EL ADMIN

  createInvitation(name: string, email: string): Observable<Submission> {
    return this.http.post<Submission>(`${this.adminUrl}/invite`, {
      candidateName: name,
      email: email
    }, this.getAdminHeaders());
  }

  getAllSubmissions(): Observable<Submission[]> {
    return this.http.get<Submission[]>(`${this.adminUrl}/submissions`, this.getAdminHeaders());
  }

  updateField(field: any): Observable<FieldDefinition> {
    const body = {
      label: field.label,
      type: field.type,
      required: field.required,
      placeholder: field.placeholder,
      options: field.options,
      sortOrder: field.sortOrder
    };
    return this.http.put<FieldDefinition>(`${this.adminUrl}/fields/${field.id}`, body, this.getAdminHeaders());
  }

  saveField(field: any): Observable<FieldDefinition> {
    const body = {
      label: field.label,
      type: field.type,
      required: field.required,
      placeholder: field.placeholder,
      options: field.options,
      sortOrder: field.sortOrder
    };
    return this.http.post<FieldDefinition>(`${this.adminUrl}/fields`, body, this.getAdminHeaders());
  }

  deleteField(id: string): Observable<void> {
    return this.http.delete<void>(`${this.adminUrl}/fields/${id}`, this.getAdminHeaders());
  }

  deleteSubmission(id: string): Observable<void> {
    return this.http.delete<void>(`${this.adminUrl}/submissions/${id}`, this.getAdminHeaders());
  }

  sendOnboardingEmail(employeeId: string): Observable<any> {
    return this.http.post(`${this.adminUrl}/send-email/${employeeId}`, {}, this.getAdminHeaders());
  }

  //MÉTODO PARA CREAR/EDITAR ADMINS
    private readonly usersUrl = 'http://localhost:8080/api/admin/users';

  getAdminUsers(): Observable<any[]> {
    return this.http.get<any[]>(this.usersUrl, this.getAdminHeaders());
  }

  createAdminUser(username: string, password: string): Observable<any> {
    return this.http.post(this.usersUrl, { username, password }, this.getAdminHeaders());
  }

  updateAdminUser(id: string, username: string, password: string): Observable<any> {
    return this.http.put(`${this.usersUrl}/${id}`, { username, password }, this.getAdminHeaders());
  }

  deleteAdminUser(id: string): Observable<void> {
    return this.http.delete<void>(`${this.usersUrl}/${id}`, this.getAdminHeaders());
  }
  }