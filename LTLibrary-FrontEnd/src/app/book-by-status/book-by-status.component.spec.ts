import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BookByStatusComponent } from './book-by-status.component';

describe('BookByStatusComponent', () => {
  let component: BookByStatusComponent;
  let fixture: ComponentFixture<BookByStatusComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BookByStatusComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BookByStatusComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
