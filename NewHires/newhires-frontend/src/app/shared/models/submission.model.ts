export interface FieldResponse {
  fieldDefinitionId: string;
  value: string;
}

export interface FormSubmissionRequest {
  token: string;
  responses: FieldResponse[];
}

export interface Submission {
    id: string;
    candidateName: string;
    email: string;
    createdAt: string;   // <--- AÑADIR (si quieres mostrar cuándo se envió la invitación)
    status: string;
    submittedAt?: string; // <--- Se llena cuando el estado es 'SUBMITTED'
    employeeFullName?: string; // El nombre del admin que invitó
    token?: string; 
   expiresAt?: string;
}