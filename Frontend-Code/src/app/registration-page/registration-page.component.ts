import { Component } from '@angular/core';
import { UserRegistration } from '../model/registration.model';
import { RegistrationService } from '../service/registration.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-registration-page',
  templateUrl: './registration-page.component.html',
  styleUrls: ['./registration-page.component.css']
})
export class RegistrationPageComponent {

  registrationData: UserRegistration = new UserRegistration; 


  constructor(private registrationService: RegistrationService, private router: Router) { }

  onSubmit() {
    this.registrationService.registerUser(this.registrationData).subscribe({
      next: (response) => {
        this.router.navigate(['/login']);
      },
      error: (error) => {
        console.error('An error occurred in the component:', error);
      },
    });
  }

  generateOtp(){
    if(this.registrationData.contactNo)
    this.registrationService.generateOtp(this.registrationData.contactNo).subscribe({
      next: (response) => {
        this.registrationData.otp=response;
      },
      error: (error) => {
        console.error('An error occurred in the component:', error);
      },
    });
  
  }

}
