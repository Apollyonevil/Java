export interface FieldResponse {
  fieldDefinitionId: string;
  value: string;
}

export interface FormSubmissionRequest {
  token: string;
  responses: FieldResponse[];
}