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
  private readonly usersUrl = 'http://localhost:8080/api/admin/users';
  private readonly versionsUrl = 'http://localhost:8080/api/admin/versions';

  private getAdminHeaders(): { headers: HttpHeaders } {
    const credentials = this.authService.getCredentials();
    return {
      headers: new HttpHeaders({
        'Authorization': `Basic ${credentials}`
      })
    };
  }

  // --- MÉTODOS USUARIO (CANDIDATO) ---

  getFormStructure(): Observable<FieldDefinition[]> {
    return this.http.get<FieldDefinition[]>(`${this.userUrl}/structure`);
  }

  submitForm(token: string, textResponses: any[], files: Map<string, File>): Observable<void> {
    const formData = new FormData();
    formData.append('token', token);
    // IMPORTANTE: Mantenemos el Blob para el JSON de respuestas
    formData.append('responses', new Blob([JSON.stringify(textResponses)], {
      type: 'application/json'
    }));
    
    files.forEach((file, fieldId) => {
      formData.append(fieldId, file, file.name);
    });
    
    return this.http.post<void>(`${this.userUrl}/submit`, formData);
  }

  // --- MÉTODOS ADMINISTRACIÓN ---

  createInvitation(name: string, email: string): Observable<Submission> {
    return this.http.post<Submission>(`${this.adminUrl}/invite`, {
      candidateName: name,
      email: email
    }, this.getAdminHeaders());
  }

  getAllSubmissions(): Observable<Submission[]> {
    return this.http.get<Submission[]>(`${this.adminUrl}/submissions`, this.getAdminHeaders());
  }

  getSubmissionDetail(id: string): Observable<any> {
    return this.http.get(`${this.adminUrl}/submissions/${id}/detail`, this.getAdminHeaders());
  }

  getAdminUrl(): string { return this.adminUrl; }

  updateField(field: any): Observable<FieldDefinition> {
    return this.http.put<FieldDefinition>(`${this.adminUrl}/fields/${field.id}`, field, this.getAdminHeaders());
  }

  saveField(field: any): Observable<FieldDefinition> {
    return this.http.post<FieldDefinition>(`${this.adminUrl}/fields`, field, this.getAdminHeaders());
  }

  deleteField(id: string): Observable<void> {
    return this.http.delete<void>(`${this.adminUrl}/fields/${id}`, this.getAdminHeaders());
  }

  approveSubmission(id: string): Observable<any> {
    return this.http.post(`${this.adminUrl}/submissions/${id}/approve`, {}, this.getAdminHeaders());
  }

  rejectSubmission(id: string, reason: string): Observable<any> {
    return this.http.post(`${this.adminUrl}/submissions/${id}/reject`, { reason }, this.getAdminHeaders());
  }

  sendOnboardingEmail(employeeId: string): Observable<any> {
    return this.http.post(`${this.adminUrl}/send-email/${employeeId}`, {}, this.getAdminHeaders());
  }

  renewToken(id: string): Observable<Submission> {
    return this.http.post<Submission>(`${this.adminUrl}/submissions/${id}/renew`, {}, this.getAdminHeaders());
  }

  deleteSubmission(id: string): Observable<void> {
    return this.http.delete<void>(`${this.adminUrl}/submissions/${id}`, this.getAdminHeaders());
  }

  // --- GESTIÓN DE USUARIOS ---

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

  // --- GESTIÓN DE VERSIONES (CORREGIDO) ---

  getFormVersions(): Observable<any[]> { 
    return this.http.get<any[]>(this.versionsUrl, this.getAdminHeaders()); 
  }

  // Restaurado a 2 argumentos para que coincida con tu AdminDashboardComponent
  createFormVersion(createdBy: string, description: string): Observable<any> { 
    return this.http.post(this.versionsUrl, { createdBy, description }, this.getAdminHeaders()); 
  }

  activateFormVersion(id: string): Observable<any> { 
    return this.http.post(`${this.versionsUrl}/${id}/activate`, {}, this.getAdminHeaders()); 
  }

  deleteFormVersion(id: string): Observable<void> { 
    return this.http.delete<void>(`${this.versionsUrl}/${id}`, this.getAdminHeaders()); 
  }
}