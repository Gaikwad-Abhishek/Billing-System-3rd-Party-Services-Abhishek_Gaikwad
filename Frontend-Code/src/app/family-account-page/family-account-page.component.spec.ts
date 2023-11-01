import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FamilyAccountPageComponent } from './family-account-page.component';

describe('FamilyAccountPageComponent', () => {
  let component: FamilyAccountPageComponent;
  let fixture: ComponentFixture<FamilyAccountPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FamilyAccountPageComponent]
    });
    fixture = TestBed.createComponent(FamilyAccountPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
