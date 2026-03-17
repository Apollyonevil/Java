export interface FieldOption {
  id: number;
  optionValue: string; // Ej: "12 pagas", "XL"
}

export interface FieldDefinition {
  id: string; // 
  label: string;
  type: string;
  isRequired: boolean;
  placeholder?: string;
  options?: string[];
  sortOrder: number; 
}
export interface Submission {
  id: number;
  token: string;
  email: string;             // De la tabla invitations/submissions
  submittedAt: string;
  status: string;            // PENDING, VALIDATED, etc.
}