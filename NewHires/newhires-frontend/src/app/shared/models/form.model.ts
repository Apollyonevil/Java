export interface FieldOption {
  id: number;
  optionValue: string; // Ej: "12 pagas", "XL"
}

export interface FieldDefinition {
  id: number;
  label: string;
  fieldType: 'TEXT' | 'NUMBER' | 'SELECT' | 'FILE'; // Coincide con tu lógica de Java
  isRequired: boolean;
  accept?: string;           // Para .pdf, .jpg
  options?: FieldOption[];   // Aquí caen los datos de la tabla field_options
}

export interface Submission {
  id: number;
  token: string;
  email: string;             // De la tabla invitations/submissions
  submittedAt: string;
  status: string;            // PENDING, VALIDATED, etc.
}