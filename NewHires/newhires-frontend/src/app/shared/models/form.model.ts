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
  id: string;
  employeeId: string;
  candidateName: string;
  email: string;
  token: string;
  status: string;
  submittedAt?: string | Date;
}
