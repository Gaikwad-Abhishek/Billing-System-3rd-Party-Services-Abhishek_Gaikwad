// user.model.ts
export interface User {
    userId: number;
    username: string;
    email: string;
    firstName: string;
    lastName: string;
    contactNo: string;
  }
  
  // pack.model.ts
  export interface Pack {
    id: number;
    providerName: string;
    price: number;
    description: string;
  }
  
  // subscription.model.ts
  export interface Subscription {
    id: number;
    userId: number;
    familyAccountId: number;
    pack: Pack;
  }
  
  // user-subscription-details.model.ts
  export interface UserSubscriptionDetails {
    userId: number;
    username: string;
    firstName: string;
    lastName: string;
    subscriptions: Subscription[];
  }
  
  // family-account-details.model.ts
  export interface FamilyAccountDetails {
    familyAccountId: number;
    accountStatus: string;
    deactivationRequestStatus: boolean;
    primaryUser: User;
    secondaryUsers: UserSubscriptionDetails[];
  }
  