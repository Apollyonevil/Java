export interface FieldOption {
  id: number;
  optionValue: string; // Ej: "12 pagas", "XL", "Gasolina"
  displayOrder: number;
}

export interface FieldDefinition {
  id: number;
  label: string;      // Ej: "Talla de camiseta"
  fieldType: string;  // "TEXT", "NUMBER", "SELECT", "FILE"
  isRequired: boolean;
  accept?: string;    // Ej: ".pdf,.jpg"
  options?: FieldOption[]; // Carga los datos de 'field_options'
}