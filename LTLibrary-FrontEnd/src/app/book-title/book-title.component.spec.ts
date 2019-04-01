import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BookTitleComponent } from './book-title.component';

describe('BookTitleComponent', () => {
  let component: BookTitleComponent;
  let fixture: ComponentFixture<BookTitleComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BookTitleComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BookTitleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
