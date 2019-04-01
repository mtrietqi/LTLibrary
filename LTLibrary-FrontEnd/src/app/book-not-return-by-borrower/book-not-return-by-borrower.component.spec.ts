import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BookNotReturnByBorrowerComponent } from './book-not-return-by-borrower.component';

describe('BookNotReturnByBorrowerComponent', () => {
  let component: BookNotReturnByBorrowerComponent;
  let fixture: ComponentFixture<BookNotReturnByBorrowerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BookNotReturnByBorrowerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BookNotReturnByBorrowerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
