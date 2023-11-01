import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Invoice } from '../model/invoice.model';


@Injectable({
  providedIn: 'root'
})
export class CollectionService {

  constructor(private http: HttpClient) {}

  private apiUrl = 'http://localhost:8080/api/collection'; 
  
  makePayment(familyAccountId:number, invoiceId:number):Observable<any>{
    return this.http.post(`${this.apiUrl}/family-account/${familyAccountId}/invoice/${invoiceId}/pay`,{},{responseType:'text'});
  }

}
