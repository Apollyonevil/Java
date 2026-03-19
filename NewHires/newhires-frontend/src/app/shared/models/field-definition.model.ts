export interface FieldDefinition {
  id: string;           // UUID string, no number
  label: string;
  type: string;         // "TEXT", "NUMBER", "SELECT", "PDF", "JPG"
  required: boolean;
  placeholder?: string;
  options?: string[];   // el backend devuelve string[], no objetos
  sortOrder?: number;
}

export interface Submission {
  id?: string;
  candidateName: string;
  email: string;
  employeeId?: string;
  status: string;
  submittedAt?: string;
  token?: string;
}