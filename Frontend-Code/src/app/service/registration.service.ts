import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Invoice } from '../model/invoice.model';
import { UserRegistration } from '../model/registration.model';


@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  private apiUrl = 'http://localhost:8080/api/auth'; 

  constructor(private http: HttpClient) {}

  generateOtp(contactNo: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/new-user/${contactNo}/validate`,{});
  }

  registerUser(registrationData: UserRegistration): Observable<any>{
    return this.http.post(`${this.apiUrl}/register`,registrationData,{responseType:"text"});
  }

}