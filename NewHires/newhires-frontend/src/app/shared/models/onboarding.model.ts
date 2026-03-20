export interface Invitation {
  token: string;      // El UUID que enviamos por email
  email: string;
  expiryDate: Date;
  isUsed: boolean;
}

