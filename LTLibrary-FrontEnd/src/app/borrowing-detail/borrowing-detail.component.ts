import { Component, OnInit } from '@angular/core';
import { Http, Response, Headers } from '@angular/http';
import { NgModel } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import 'rxjs/add/operator/map';
import {RouterModule,Router} from '@angular/router';
declare var $ :any;
declare var PNotify :any;
import { CookieService } from 'ngx-cookie-service';


@Component({
  selector: 'app-borrowing-detail',
  templateUrl: './borrowing-detail.component.html',
  styleUrls: ['./borrowing-detail.component.css'],
  providers: [CookieService]
})
export class BorrowingDetailComponent implements OnInit {
  //Server link
  serverLink: string = 'http://localhost:8080/SpringMVCHibernateCRUDExample';

  //Variables for current Librarian
  currentLibLink:string = this.serverLink+'/image/librarian/'
  currentLibId:string;
  currentLibFName:string;
  currentLibLName:string;
  currentLibRole:string;
  libData:any;


  borrowingId: number;
  private sub: any;
  borrowingByIdData;
  borrowingDetailData;
  bookData;
  dataEdit;
  bookIdEdit;
  borDetailIdEdit;
  isReturnEdit;

  nearlyDeleteId;

  borrowerName;
  borrowingDate;

  bookIdAddHint;
  bookByIdHintData;
  bookAddHint;

  bookIdEditHint;
  bookByIdHintEditData;
  bookEditHint;

  
  constructor(private router : Router,private route: ActivatedRoute,private http: Http,private cookieService: CookieService) {
    this.sub = this.route.params.subscribe(params => {
      this.borrowingId = +params['id']; 
   });
    this.getBorrowingDetailByBorrowingId();
    this.getBorrowingById(this.borrowingId.toString());
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
   $('#btnAdd').click(function(){
      $('#modalAdd').modal("show");
      });
   $('#editClick').click(function(){
      $('#modalEdit').modal("show");
      });
    $('input').iCheck({
      checkboxClass: 'icheckbox_square-blue',
      radioClass: 'iradio_square-blue',
      increaseArea: '20%' // optional
      });
    $('input[name="daterange"]').daterangepicker({
      singleDatePicker: true,
      showDropdowns: true
      });
    $('#BookBarcodeAdd').select2();
    $('#BookBarcodeEdit').select2();
    

    
  //  alert(this.borrowingId);
  }

  getBorrowingDetailByBorrowingId(){
    // alert(id);
    this.http.get(this.serverLink+'/getBorrowingDetailsFromBorrowingId/'+this.borrowingId).map((res: Response) => res.json())
    .subscribe(data => {
           this.borrowingDetailData = data;
          //  console.log(this.borrowingDetailData);  
   });
  }
  initializeAddForm()
  {
    this.http.get(this.serverLink+'/getAllAvailableBookREST')
    .map((res: Response) => res.json())
    .subscribe(data => {
      this.bookData = data;
    });

    setTimeout(() => {
    $('#BookBarcodeAdd').empty().trigger("change");
    $.each(this.bookData, function (index, value) {
        this.tempIdData = value.bookid;
          var newOption = new Option(value.barcode, value.bookid, false, false);          
        $('#BookBarcodeAdd').append(newOption).trigger('change');
      });

      this.bookIdAddHint=$("#BookBarcodeAdd").val();
    
      this.http.get(this.serverLink+'/getBooksREST/' +this.bookIdAddHint).map((res: Response) => res.json())
        .subscribe(bookByIdHintData => {
          this.bookByIdHintData = bookByIdHintData;
          // console.log(this.bookByIdHintData);
          this.bookAddHint="<strong class='text-info'>Book Title: </strong> " + this.bookByIdHintData.titles.booktitle;
          $('#bookAddHint').html(this.bookAddHint);
        });

      let http2 = this.http;
      let serverLink2 = this.serverLink;
      $('#BookBarcodeAdd').on('change', function (e) {
        this.bookIdAddHint=$("#BookBarcodeAdd").val();
    
        http2.get(serverLink2+'/getBooksREST/' +this.bookIdAddHint).map((res: Response) => res.json())
        .subscribe(bookByIdHintData => {
          this.bookByIdHintData = bookByIdHintData;
          // console.log(this.bookByIdHintData);
          this.bookAddHint="<strong class='text-info'>Book Title: </strong> " + this.bookByIdHintData.titles.booktitle;
          $('#bookAddHint').html(this.bookAddHint);
        });
      });
    },1000); 
  }

  private addBorrowingDetail(){
    var formData= new FormData();
    formData.append("isReturn","false");
    formData.append("borrowingId",this.borrowingId.toString());
    formData.append("bookId",$("#BookBarcodeAdd").val());

    this.http.post(this.serverLink+'/addBorrowingDetailsREST', formData)
      .map((res: Response) => res.json())
      .subscribe(
      res2 => {
        // console.log(res2);
      },
      err => {
        console.log(err);
      }
      );
      setTimeout(() => {
        this.getBorrowingDetailByBorrowingId();
        //Show Pnotify announcement
        $("#modalAdd").modal('hide');
        var addSuccessful= new PNotify({
          title: 'Add Successfully !!!',
          text: 'You have added a new Borrowing Detail successfully',
          type: 'success',
          addclass: "stack-topleft",
          animate: {
              animate: true,
              in_class: 'rotateInDownLeft',
              out_class: 'rotateOutUpRight'
          },delay:2000
        });

      }, 1000);
  }

  private getBorrowingDetailbyId(id:string){
    this.http.get(this.serverLink+'/getBorrowingDetailsREST/' + id).map((res: Response) => res.json())
      .subscribe(dataEdit => {
        this.dataEdit = dataEdit;
        // console.log(this.dataEdit);
      });
      setTimeout(() => {
        this.bookIdEdit= this.dataEdit.books.bookid;
        this.getIsReturn();
    },100);
  }
  getIsReturn(){
    setTimeout(() => {
    if(this.dataEdit.isreturn===false)
    {
      $( "#noReturn" ).iCheck('check');
    }else{
      $( "#yesReturn" ).iCheck('check');
    }
    },100);
  }

  initializeEditForm(id: string) {
    this.getBorrowingDetailbyId(id);
    this.http.get(this.serverLink+'/getAllBooksREST')
    .map((res: Response) => res.json())
    .subscribe(data => {
      this.bookData = data;
    });

    setTimeout(() => {
      $('#BookBarcodeEdit').empty().trigger("change");
      for (let value of this.bookData) {
        if (value.bookid == this.bookIdEdit) {
          var newOption = new Option(value.barcode, value.bookid, false, true);
          $('#BookBarcodeEdit').append(newOption).trigger('change');
        }
        else {
          var newOption = new Option(value.barcode, value.bookid, false, false);
          $('#BookBarcodeEdit').append(newOption).trigger('change');
        }
      }

      this.bookIdEditHint=$("#BookBarcodeEdit").val();
      this.http.get(this.serverLink+'/getBooksREST/' +this.bookIdEditHint).map((res: Response) => res.json())
      .subscribe(bookByIdHintEditData => {
        this.bookByIdHintEditData = bookByIdHintEditData;
        // console.log(this.bookByIdHintEditData);
        this.bookEditHint="<strong class='text-info'>Book Title: </strong> " + this.bookByIdHintEditData.titles.booktitle;
        $('#bookEditHint').html(this.bookEditHint);
      });

      let http2 = this.http;
      let serverLink2 = this.serverLink;
      $('#BookBarcodeEdit').on('change', function (e) {
        this.bookIdEditHint=$("#BookBarcodeEdit").val();
    
        http2.get(serverLink2+'/getBooksREST/' +this.bookIdEditHint).map((res: Response) => res.json())
        .subscribe(bookByIdHintEditData => {
          this.bookByIdHintEditData = bookByIdHintEditData;
          // console.log(this.bookByIdHintEditData);
          this.bookEditHint="<strong class='text-info'>Book Title: </strong> " + this.bookByIdHintEditData.titles.booktitle;
          $('#bookEditHint').html(this.bookEditHint);
        });
      });
    }, 500);
    this.borDetailIdEdit=id;

  }
  updateBorrowingDetail(){
      if($('input[name=iCheckEdit]:checked').val()=='Yes')
      {
        this.isReturnEdit='true';
      }
      else{
        this.isReturnEdit='false';
      }
      var formData= new FormData();
      formData.append("isReturn",this.isReturnEdit);
      formData.append("borrowingId",this.borrowingId.toString());
      formData.append("bookId",$("#BookBarcodeEdit").val());
      formData.append("bordetailId",this.borDetailIdEdit);

      this.http.post(this.serverLink+'/updateBorrowingDetailsREST', formData)
      .map((res: Response) => res.json())
      .subscribe(
      res2 => {
        // console.log(res2);
      },
      err => {
        console.log(err);
      }
      );
      setTimeout(() => {
        this.getBorrowingDetailByBorrowingId();
        $("#modalEdit").modal('hide');
        var updateSuccessful= new PNotify({
          title: 'Update Successfully !!!',
          text: 'You have updated successfully',
          type: 'info',
          addclass: "stack-topleft",
          animate: {
              animate: true,
              in_class: 'rotateInDownLeft',
              out_class: 'rotateOutUpRight'
          },delay:2000
        });
      }, 1000);
  }

  nearlyDelete(id: string) {
    this.nearlyDeleteId = id;
  }
  deleteBorrowingDetail() {
    this.http.get(this.serverLink+'/deleteBorrowingDetailsREST/'+this.nearlyDeleteId).map((res: Response) => res.json())
      .subscribe(data => {
          //  console.log(this.borrowingDetailData); 
           this.getBorrowingDetailByBorrowingId();
           $("#modalDelete").modal("hide");
           var deleteSuccessful= new PNotify({
             title: 'Delete Successfully !!!',
             text: 'You have deleted successfully',
             type: 'error',
             addclass: "stack-topleft",
             animate: {
                 animate: true,
                 in_class: 'zoomInLeft',
                 out_class: 'zoomOutRight'
             },delay:2000
           }); 
      },err=>
      {
        $("#modalDelete").modal("hide");
        //Show Pnotify announcement
        var deleteSuccessful = new PNotify({
          title: 'Error',
          text: 'Cannot delete this borrowing',
          type: 'error',
          addclass: "stack-topleft",
          animate: {
            animate: true,
            in_class: 'zoomInLeft',
            out_class: 'zoomOutRight'
          },
          delay: 2000
        });
      });

  }
  getBorrowingById(id:string){
    this.http.get(this.serverLink+'/getBorrowingsREST/'+id).map((res: Response) => res.json())
    .subscribe(borrowingByIdData => {
           this.borrowingByIdData = borrowingByIdData;
          //  console.log(this.borrowingByIdData);
   });
   setTimeout(() => {
    this.borrowerName= this.borrowingByIdData.borrowers.firstname+" "+this.borrowingByIdData.borrowers.lastname;
    this.borrowingDate=this.borrowingByIdData.bordate;
    }, 500);
  }
  done(){
    //Navigate to Borrowing
    this.router.navigate(['/Borrowing']);
  }

  addCommas(nStr) {
    nStr += '';
    var x = nStr.split('.');
    var x1 = x[0];
    var x2 = x.length > 1 ? '.' + x[1] : '';
    var rgx = /(\d+)(\d{3})/;
    while (rgx.test(x1)) {
            x1 = x1.replace(rgx, '$1' + ',' + '$2');
    }
    return x1 + x2;
  }

  

  //Sign out
  signOut()
  {
    this.cookieService.delete('loginLibId');
    this.cookieService.delete('loginLibFirstName');
    this.cookieService.delete('loginLibLastName');
    this.cookieService.delete('loginLibRole');
    this.router.navigate(['./Login']);
  }
}
