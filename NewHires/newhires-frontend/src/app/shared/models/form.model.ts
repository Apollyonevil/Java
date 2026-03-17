export interface FieldOption {
  id: number;
  optionValue: string; // Ej: "12 pagas", "XL"
}

export interface FieldDefinition {
  id: number;
  label: string;
  type: string;
  isRequired: boolean;
  options?: string[]; // El signo '?' lo hace opcional, así no rompe nada
}

export interface Submission {
  id: number;
  token: string;
  email: string;             // De la tabla invitations/submissions
  submittedAt: string;
  status: string;            // PENDING, VALIDATED, etc.
}