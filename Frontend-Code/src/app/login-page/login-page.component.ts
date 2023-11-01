import { Component } from '@angular/core';
import { CustomAuthService } from '../service/auth.service';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent {

  username: string = "";
  password: string = "";


  constructor(private customAuthService: CustomAuthService, private router: Router,private fb: FormBuilder) { }

  login(): void {
    this.customAuthService.login(this.username, this.password).subscribe({
      next: (response) => {
        localStorage.setItem('token', response['token']);
        console.log(response['token']);
        this.router.navigate(['/home']);
      },
      error: (error) => {
        console.error('An error occurred in the component:', error);
      },
    });
  }

  logout(): void{
    this.customAuthService.logout();
  }
  
  
}
