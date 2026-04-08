export interface FieldOption {
  id: number;
  optionValue: string; 
}

export interface FieldDefinition {
  id: string;
  label: string;
  type: string;
  originalType?: string;
  required: boolean;
  placeholder?: string;
  options?: string[];
  sortOrder?: number;
}

export interface Submission {
  id?: string;
  candidateName: string;
  email: string;
  employee_id?: string;
  status: string;
  submittedAt?: string;
  expiresAt?: string;  
  token?: string;
}
