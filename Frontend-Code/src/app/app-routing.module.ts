import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginPageComponent } from './login-page/login-page.component';
import { FamilyAccountPageComponent } from './family-account-page/family-account-page.component';
import { InvoicePageComponent } from './invoice-page/invoice-page.component';
import { LandingPageComponent } from './landing-page/landing-page.component';
import { HomePageComponent } from './home-page/home-page.component';
import { RegistrationPageComponent } from './registration-page/registration-page.component';

const routes: Routes = [
  { path: '', redirectTo: '/streamline', pathMatch: 'full' },
  { path: 'streamline', component: LandingPageComponent },
  { path: 'login', component: LoginPageComponent },
  { path: 'register', component: RegistrationPageComponent},
  { path: 'home', component: HomePageComponent },
  { path: 'family-account', component: FamilyAccountPageComponent },
  { path: 'invoice', component: InvoicePageComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
