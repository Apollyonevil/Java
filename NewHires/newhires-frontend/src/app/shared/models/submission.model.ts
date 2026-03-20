export interface FieldResponse {
  fieldDefinitionId: string;
  value: string;
}

export interface FormSubmissionRequest {
  token: string;
  responses: FieldResponse[];
}

export interface Submission {
  id?: string;
  candidateName: string;
  email: string;
  employeeId?: string;
  submittedAt?: string;
  token?: string;
  status: 'PENDING' | 'VALIDATED' | 'REJECTED';
}