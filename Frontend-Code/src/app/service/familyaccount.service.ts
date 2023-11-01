import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { FamilyAccountDetails, Pack } from '../model/family-account.model';

@Injectable({
  providedIn: 'root'
})
export class FamilyAccountService {

  familyAccountDetails?: FamilyAccountDetails;
  constructor(private http: HttpClient) { }

  private userAPIUrl = 'http://localhost:8080/api/family-account';

  private billingAPIUrl = 'http://localhost:8080/api/family-subscriptions';

  getFamilyAccountData(): Observable<FamilyAccountDetails> {
    return this.http.get<FamilyAccountDetails>(`${this.userAPIUrl}/details`);
  }

  getMemberDetails(): Observable<FamilyAccountDetails> {
    return this.http.get<FamilyAccountDetails>(`${this.userAPIUrl}/member-details`);
  }

  getBillingCycle(): Observable<any> {
    return this.http.get(`${this.billingAPIUrl}/billing-cycle-ends`);
  }

  createFamilyAccount(): Observable<any> {
    return this.http.post(`${this.userAPIUrl}/create`,{},{responseType:"text"});
  }

  sendOTP(contactNo: string): Observable<any> {
    return this.http.post(`${this.userAPIUrl}/add-member/${contactNo}/validate`,{});
  }

  validateMember(contactNo: string,otp: string): Observable<any> {
    return this.http.post(`${this.userAPIUrl}/add-member/${contactNo}/${otp}`,{},{responseType:'text'});
  }

  removeUser(userId: number){
    return this.http.post(`${this.userAPIUrl}/remove-member/${userId}`,{},{responseType:'text'});
  }

  getAvailablePacks(): Observable<Pack[]>{
    return this.http.get<Pack[]>(`${this.billingAPIUrl}/available-packs`);
  }

  addSubscription(familyAccountId: number, packId:number): Observable<any>{
    return this.http.post(`${this.billingAPIUrl}/${familyAccountId}/add/${packId}`,{},{responseType:'text'});
  }

  removeSubscription(subscriptionId: number): Observable<any>{
    return this.http.post(`${this.billingAPIUrl}/remove/${subscriptionId}`,{},{responseType:'text'});
  }

  activateAccount(familyAccountId: number): Observable<any>{
    return this.http.post(`${this.userAPIUrl}/activate/${familyAccountId}`,{},{responseType:'text'});
  }

  deactivateAccount(familyAccountId: number): Observable<any>{
    return this.http.post(`${this.userAPIUrl}/deactivate-request/${familyAccountId}`,{},{responseType:'text'});
  }
}
