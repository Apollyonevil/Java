export interface FieldOption {
  id: number;
  optionValue: string; 
}

export interface FieldDefinition {
  id: string;
  label: string;
  type: string;
  required: boolean;    
  sortOrder?: number;  
  placeholder?: string; 
  options?: string[]; 
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
