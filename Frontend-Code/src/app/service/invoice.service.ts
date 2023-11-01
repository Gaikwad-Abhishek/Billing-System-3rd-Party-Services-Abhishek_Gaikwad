import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Invoice } from '../model/invoice.model';


@Injectable({
  providedIn: 'root'
})
export class InvoiceService {

  private apiUrl = 'http://localhost:8080/api/invoice'; 

  constructor(private http: HttpClient) {}

  getUserInvoices(familyAccountId: number): Observable<Invoice[]> {
    return this.http.get<Invoice[]>(`${this.apiUrl}/family-account/${familyAccountId}`,);
  }

}