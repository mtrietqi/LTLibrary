import { Component, OnInit } from '@angular/core';
import { Http, Response, Headers } from '@angular/http';
import { NgModel } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import 'rxjs/add/operator/map';
import {RouterModule,Router} from '@angular/router';
import { CookieService } from 'ngx-cookie-service';

declare var $ :any;

@Component({
  selector: 'app-book-not-return-by-borrower',
  templateUrl: './book-not-return-by-borrower.component.html',
  styleUrls: ['./book-not-return-by-borrower.component.css'],
  providers: [CookieService]
})
export class BookNotReturnByBorrowerComponent implements OnInit {
  //Server link
  serverLink: string = 'http://localhost:8080/SpringMVCHibernateCRUDExample';
  
  private sub: any;
  borrowerId: number;

  borrowerName;

  data;
  borrowerByIdData;

   //Variables for current Librarian
   currentLibLink:string = this.serverLink+'/image/librarian/'
   currentLibId:string;
   currentLibFName:string;
   currentLibLName:string;
   currentLibRole:string;
   libData:any;

  constructor(private router : Router,private route: ActivatedRoute,private http: Http,private cookieService: CookieService ) {
    this.sub = this.route.params.subscribe(params => {
      this.borrowerId = +params['id']; 
   });
   this.getBorrowerById(this.borrowerId.toString());
   this.getData(this.borrowerId.toString());
 
  }
  ngOnInit() {
     //Check valid access
 if(!this.cookieService.check('loginLibId') || !this.cookieService.check('loginLibLastName')
 || !this.cookieService.check('loginLibFirstName'))
 {
   this.router.navigate(['./Login']);
 }
 else{
   this.currentLibId = this.cookieService.get('loginLibId');
   this.currentLibFName = this.cookieService.get('loginLibFirstName');
   this.currentLibLName = this.cookieService.get('loginLibLastName');
   this.currentLibRole = this.cookieService.get('loginLibRole');
 } 
    setTimeout(() => {
      // DataTable

		$('#tbl tfoot th').each( function () {
      var title = $(this).text();
      $(this).html( '<input type="text" placeholder="Search '+title+'" />' );
  } );

      var table = $('#tbl').DataTable();

    table.columns().every( function () {
      var that = this;

      $( 'input', this.footer() ).on( 'keyup change', function () {
          if ( that.search() !== this.value ) {
              that
                  .search( this.value )
                  .draw();
          }
      } );
  } );
//end datatable
    }, 1000);
    
  }
  getBorrowerById(id:string){
    this.http.get(this.serverLink+'/getBorrowersREST/'+id)
    .map((res: Response) => res.json())
    .subscribe(borrowerByIdData => {
           this.borrowerByIdData = borrowerByIdData;
          //  console.log(this.borrowerByIdData);
           setTimeout(() => {
            this.borrowerName= this.borrowerByIdData.firstname+" "+this.borrowerByIdData.lastname;
            }, 500);
   });
  }

  getData(id:string){
    this.http.get(this.serverLink+'/getBookNotYetReturnByBorrowerREST/'+id)
    .map((res: Response) => res.json())
    .subscribe(data => {
           this.data = data;
          //  console.log(this.data);
   });
  }
  done(){
    this.router.navigate(['/BookNotReturn']);
  }

  signOut() {
    this.cookieService.delete('loginLibId');
    this.cookieService.delete('loginLibFirstName');
    this.cookieService.delete('loginLibLastName');
    this.cookieService.delete('loginLibRole');
    this.router.navigate(['./Login']);
  }
}
