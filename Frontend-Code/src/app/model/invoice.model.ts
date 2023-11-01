export interface Invoice {
    invoiceId: number;
    familyAccountId: number;
    totalAmount: number;
    invoiceDate: Date;
    dueDate: Date;
    paymentStatus: string;
    userSubscriptions: UserSubscriptionRecord[];
  
  }
  
  export interface UserSubscriptionRecord {
    id: number;
    invoice: Invoice;
    userId: number;
    packId: number;
    providerName: string;
    price: number;
  }
  