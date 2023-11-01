import { Component, OnInit } from '@angular/core';
import { InvoiceService } from '../service/invoice.service';
import { Invoice } from '../model/invoice.model';
import { FamilyAccountService } from '../service/familyaccount.service';
import { CollectionService } from '../service/collection.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-invoice-page',
  templateUrl: './invoice-page.component.html',
  styleUrls: ['./invoice-page.component.css']
})
export class InvoicePageComponent implements OnInit {

  userInvoices?: Invoice[];
  selectedInvoice?: Invoice;
  familyAccountId?: number;
  showPaymentGateway: boolean = false;
  showInvoiceDetails: boolean = false;
  showInvoices: boolean = true;

  showNotification: boolean = false;
  notificationText: string = "Notification";

  cardholderName: string = '';
  cardNumber: string = '';
  expirationDate: string = '';
  cvv: string = '';

  constructor(private invoiceService: InvoiceService, private familyAccountService: FamilyAccountService, private collectionService: CollectionService,private router: Router) {
  }

  ngOnInit(): void {
    this.familyAccountService.getFamilyAccountData().subscribe(response => {
      this.familyAccountId = response.familyAccountId;
      this.getUserInvoice();
    });
  }

  getUserInvoice(): void {
    if (this.familyAccountId)
      this.invoiceService.getUserInvoices(this.familyAccountId).subscribe((invoices: Invoice[]) => {
        this.userInvoices = invoices;
      });
  }

  makePayment(selectedInvoice: Invoice): void {
    this.selectedInvoice = selectedInvoice;
    this.showInvoiceDetails = false;
    this.showInvoices = false;
    this.showPaymentGateway = true;
  }

  confirmPayment(familyAccountId: number, invoiceId: number): void {
    this.collectionService.makePayment(familyAccountId, invoiceId).subscribe({
      next: (response) => {
        //account successful
        this.notificationText = "Payment Successful !"
        this.showNotification = true;
        setTimeout(() => {
          this.showNotification = false;
        }, 3000);
        this.getUserInvoice();
        this.cardholderName = '';
        this.cardNumber = '';
        this.expirationDate = '';
        this.cvv = '';
        this.router.navigate(['/invoice']);
      },
      error: (error) => {
        console.error('An error occurred in the component:', error);
      },
    });
  }

  test() {

  }

  viewInvoiceDetails(selectedInvoice: Invoice) {
    this.showInvoices = false;
    this.showPaymentGateway = false;
    this.showInvoiceDetails = true;
    this.selectedInvoice = selectedInvoice;

  }

  close() {
    this.showInvoices = true;
    this.showPaymentGateway = false;
    this.showInvoiceDetails = false;
  }
}
