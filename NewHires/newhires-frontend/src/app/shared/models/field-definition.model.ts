export interface FieldDefinition {
  id: string;          
  label: string;
  type: string;        
  required: boolean;
  placeholder?: string;
  options?: string[];  
  sortOrder?: number;
}

