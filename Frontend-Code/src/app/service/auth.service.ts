import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CustomAuthService {

  constructor(private http: HttpClient) { }

  private gatewayUrl = 'http://localhost:8080';


  login(username: string, password: string): Observable<any> {

    // return this.http.post<any>(`${this.gatewayUrl}/api/auth/token`, { username: username, password: password }).pipe(

    //   tap(res => {

    //     localStorage.setItem('token', res['token']);
    //     console.log(res['token']);
        
    //   })

    // );

      return this.http.post<any>(`${this.gatewayUrl}/api/auth/token`, { username: username, password: password });

  }

  logout(): void {
    localStorage.setItem('token', '');
    // localStorage.setItem('username', '');
  }

}
