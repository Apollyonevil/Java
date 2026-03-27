export interface Invitation {
  token: string;   
  email: string;
  expiryDate: Date;
  isUsed: boolean;
}

