export interface Invitation {
  token: string;      // El UUID que enviamos por email
  email: string;
  expiryDate: Date;
  isUsed: boolean;
}

export interface Submission {
  id?: number;
  invitationToken: string;
  submittedAt: Date;
  status: 'PENDING' | 'VALIDATED' | 'REJECTED';
}