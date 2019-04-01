import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule} from '@angular/router';
import { HttpModule }    from '@angular/http';
import { FormsModule, ReactiveFormsModule} from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';


import {MyServiceService} from './my-service.service';

import { AppComponent } from './app.component';
import { AuthorComponent } from './author/author.component';
import { BookComponent } from './book/book.component';
import { BookPublisherComponent } from './book-publisher/book-publisher.component';
import { BookTitleComponent } from './book-title/book-title.component';
import { BorrowerComponent } from './borrower/borrower.component';
import { BorrowingComponent } from './borrowing/borrowing.component';
import { BorrowingDetailComponent } from './borrowing-detail/borrowing-detail.component';
import { CategoryComponent } from './category/category.component';
import { LibrarianComponent } from './librarian/librarian.component';
import { ReturnComponent } from './return/return.component';
import { ReturnDetailComponent } from './return-detail/return-detail.component';
import { BorrowerTypeComponent } from './borrower-type/borrower-type.component';
import { DashBoardComponent } from './dash-board/dash-board.component';
import { LoginComponent } from './login/login.component';
import { BookNotYetReturnReportComponent } from './book-not-yet-return-report/book-not-yet-return-report.component';
import { BookNotReturnByBorrowerComponent } from './book-not-return-by-borrower/book-not-return-by-borrower.component';
import { BookAvailableComponent } from './book-available/book-available.component';
import { BookByStatusComponent } from './book-by-status/book-by-status.component';
import { NotFoundPageComponent } from './not-found-page/not-found-page.component';
@NgModule({
  declarations: [
    AppComponent,
    AuthorComponent,
    BookComponent,
    BookPublisherComponent,
    BookTitleComponent,
    BorrowerComponent,
    BorrowingComponent,
    BorrowingDetailComponent,
    CategoryComponent,
    LibrarianComponent,
    ReturnComponent,
    ReturnDetailComponent,
    BorrowerTypeComponent,
    DashBoardComponent,
    LoginComponent,
    BookNotYetReturnReportComponent,
    BookNotReturnByBorrowerComponent,
    BookAvailableComponent,
    BookByStatusComponent,
    NotFoundPageComponent
  ],
  imports: [
    BrowserModule,
    HttpModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule.forRoot([
      
      {
        path: 'Author',
        component: AuthorComponent
      },
      {
        path: 'Book',
        component: BookComponent
      },
      {
        path: 'BookTitle',
        component: BookTitleComponent
      },
      {
        path: 'BookPublisher',
        component: BookPublisherComponent
      },
      {
        path: 'Borrower',
        component: BorrowerComponent
      },
      {
        path: 'BorrowerType',
        component: BorrowerTypeComponent
      },
      {
        path: 'Borrowing',
        component: BorrowingComponent
      },
      {
        path: 'BorrowingDetail/:id',
        component: BorrowingDetailComponent
      },
      {
        path: 'Category',
        component: CategoryComponent
      },
      {
        path: 'Librarian',
        component: LibrarianComponent
      },
      {
        path: 'Returning',
        component: ReturnComponent
      },
      {
        path: 'ReturnDetail/:id',
        component: ReturnDetailComponent
      },
      {
        path: 'DashBoard',
        component: DashBoardComponent
      },
      {
        path: 'Login',
        component: LoginComponent
      },
      {
        path: 'BookNotReturn',
        component: BookNotYetReturnReportComponent
      },
      {
        path: 'BookNotReturnByBorrower/:id',
        component: BookNotReturnByBorrowerComponent
      },
      {
        path: 'AvailableBook',
        component: BookAvailableComponent
      },
      {
        path: 'BookByStatus',
        component: BookByStatusComponent
      },
      {
        path: '**',
        component: NotFoundPageComponent
      }


    ])
  ],
  providers: [MyServiceService],
  bootstrap: [AppComponent]
})
export class AppModule { }
