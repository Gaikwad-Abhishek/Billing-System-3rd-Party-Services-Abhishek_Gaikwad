import { Component, OnInit } from '@angular/core';
import { FamilyAccountDetails, Pack } from '../model/family-account.model';
import { FamilyAccountService } from '../service/familyaccount.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-family-account-page',
  templateUrl: './family-account-page.component.html',
  styleUrls: ['./family-account-page.component.css']
})
export class FamilyAccountPageComponent implements OnInit {

  familyAccountData?: FamilyAccountDetails;
  addMemberInterface : boolean = false;
  enterNumberInterface : boolean = false;
  enterOTPInterface : boolean = false;
  availablePacksInterface : boolean = false;
  showAccountDetails: boolean = true;
  contactNo?: string;
  availablePacks?: Pack[];
  packAddedNoti: boolean = false;
  showNotification: boolean = false;
  notificationText: string = "Notification";
  billingCycleEnds?: Date;
  otp?: string;

  constructor(private familyAccountService: FamilyAccountService) { }

  ngOnInit(): void {
    this.familyAccountService.getFamilyAccountData().subscribe(data => {
      console.log(data)
      this.familyAccountData = data;
    });
    this.familyAccountService.getBillingCycle().subscribe(data => {
      console.log(data)
      this.billingCycleEnds = data;
    });
    this.familyAccountService.getMemberDetails().subscribe(data => {
      console.log(data)
      this.familyAccountData = data;
    });
  }

  createFamilyAccount() {
    this.familyAccountService.createFamilyAccount().subscribe({
      next: (response) => {
        //account successful
        this.ngOnInit();
      },
      error: (error) => {
        console.error('An error occurred in the component:', error);
      },
    });
  }

  addMember() {
      this.addMemberInterface = true;
      this.enterNumberInterface = true;
      this.showAccountDetails = false;
      this.availablePacksInterface = false;
  }

  sendOTP(){
    this.enterOTPInterface = true;
    this.enterNumberInterface = false;
    if(this.contactNo)
    this.familyAccountService.sendOTP(this.contactNo).subscribe({
      next: (response) => {
        this.otp = response;
      },
      error: (error) => {
        console.error('An error occurred in the component:', error);
      },
    });
  }

  validateMember(){
    this.enterOTPInterface = false;
    this.addMemberInterface = false;
    this.showAccountDetails = true;
    if(this.contactNo && this.otp)
    this.familyAccountService.validateMember(this.contactNo,this.otp).subscribe({
      next: (response) => {
        //account successful
        this.ngOnInit();
        this.addMemberInterface = false;
        this.enterOTPInterface = false;
        this.contactNo = "";
        this.otp = "";
      },
      error: (error) => {
        console.error('An error occurred in the component:', error);
      },
    });
  }

  getAvailablePack() {
    this.availablePacksInterface = true;
    this.addMemberInterface = false;
    this.showAccountDetails = false;
   this.familyAccountService.getAvailablePacks().subscribe({
    next: (response) => {
      this.availablePacks = response;
      console.log(response);
    },
    error: (error) => {
      console.error('An error occurred in the component:', error);
    },
  });
  }

  addSubscription(packId: number){
    if(this.familyAccountData)
    this.familyAccountService.addSubscription(this.familyAccountData.familyAccountId,packId).subscribe({
      next: (response) => {
        //account successful
        this.notificationText = "Subscription Added Successfullly!"
        this.showNotification = true;
        setTimeout(() => {
          this.showNotification = false;
        }, 3000);
        this.ngOnInit();
      },
      error: (error) => {
        console.error('An error occurred in the component:', error);
      },
    });
  }

  removeSubscription(subscriptionId: number){
    if(this.familyAccountData)
    this.familyAccountService.removeSubscription(subscriptionId).subscribe({
      next: (response) => {
        //account successful
        this.notificationText = "Subscription Removed Successfullly!"
        this.showNotification = true;
        setTimeout(() => {
          this.showNotification = false;
        }, 3000);
        this.ngOnInit();
      },
      error: (error) => {
        console.error('An error occurred in the component:', error);
      },
    });
  }

  activateAccount(){
    this.addMemberInterface = false;
    this.availablePacksInterface = false;
    if(this.familyAccountData)
    this.familyAccountService.activateAccount(this.familyAccountData.familyAccountId).subscribe({
      next: (response) => {
        //account successful
        this.notificationText = "Account Activation Successful!"
        this.showNotification = true;
        setTimeout(() => {
          this.showNotification = false;
        }, 3000);
        this.ngOnInit();
      },
      error: (error) => {
        console.error('An error occurred in the component:', error);
      },
    });
  }

  deactivateAccount(): void {
    if(this.familyAccountData)
    this.familyAccountService.deactivateAccount(this.familyAccountData.familyAccountId).subscribe({
      next: (response) => {
        //account successful
        this.notificationText = "Deactivation Request Sent";
        this.showNotification = true;
        setTimeout(() => {
          this.showNotification = false;
        }, 3000);
        this.ngOnInit();
      },
      error: (error) => {
        console.error('An error occurred in the component:', error);
      },
    });
  }

  removeUser(userId: number): void {
    if(this.familyAccountData)
    this.familyAccountService.removeUser(userId).subscribe({
      next: (response) => {
        //account successful
        this.notificationText = "User Removed Successfully";
        this.showNotification = true;
        setTimeout(() => {
          this.showNotification = false;
        }, 3000);
        this.ngOnInit();
      },
      error: (error) => {
        console.error('An error occurred in the component:', error);
      },
    });
  }

  test(){
    
  }

  close(){
    this.showAccountDetails = true;
    this.addMemberInterface = false;
    this.enterOTPInterface = false;
    this.availablePacksInterface = false;
  }
  
}
