import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CustomAuthService } from '../service/auth.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {

  constructor(private customAuthService: CustomAuthService, private router: Router) { }

  logout(): void{
    this.customAuthService.logout();
    this.router.navigate(['/streamline']);
  }
}