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