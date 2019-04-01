import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BorrowerTypeComponent } from './borrower-type.component';

describe('BorrowerTypeComponent', () => {
  let component: BorrowerTypeComponent;
  let fixture: ComponentFixture<BorrowerTypeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BorrowerTypeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BorrowerTypeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
