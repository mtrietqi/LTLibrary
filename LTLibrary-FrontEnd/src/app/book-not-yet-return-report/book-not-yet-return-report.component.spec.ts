import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BookNotYetReturnReportComponent } from './book-not-yet-return-report.component';

describe('BookNotYetReturnReportComponent', () => {
  let component: BookNotYetReturnReportComponent;
  let fixture: ComponentFixture<BookNotYetReturnReportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BookNotYetReturnReportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BookNotYetReturnReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
