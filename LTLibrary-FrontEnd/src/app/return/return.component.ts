import { Component, OnInit } from '@angular/core';
import { Http, Response, Headers } from '@angular/http';
import { NgModel } from '@angular/forms';
import 'rxjs/add/operator/map';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';


declare var $ :any;
declare var PNotify :any;
@Component({
  selector: 'app-return',
  templateUrl: './return.component.html',
  styleUrls: ['./return.component.css'],
  providers: [CookieService]
})
export class ReturnComponent implements OnInit {
  //Server link
  serverLink: string = 'http://localhost:8080/SpringMVCHibernateCRUDExample';

  //Variables for current Librarian
  currentLibLink:string = this.serverLink+'/image/librarian/'
  currentLibId:string;
  currentLibFName:string;
  currentLibLName:string;
  currentLibRole:string;
  libData:any;

  data;
  dataById;
  librarianData;
  borrowerData;

  addDateShow= new Date();

  updatedDateById;
  createdDateById;
  updatedBy;
  createdBy;

  returningIdEdit;
  returningDateEdit;
  createdDateEdit;
  
  returningAddId;

  nearlyDeleteId;

  borrowerIdAddHint;
  borrowerByIdHintData;
  borrowerAddHint;

  borrowerIdEditHint;
  borrowerByIdHintDataEdit;
  borrowerEditHint;

  createdById;
  updatedById;
  libDataCreated;
  libDataUpdated;

  createdByName;
  updatedByName;
  returningCreatedBy;

  constructor(private router : Router,private http: Http,private cookieService: CookieService) {
    this.getData();
 
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
  $('#moreInfoClick').click(function(){
      $('#modalInfo').modal("show");
      });
    $('input').iCheck({
      checkboxClass: 'icheckbox_square-blue',
      radioClass: 'iradio_square-blue',
      increaseArea: '20%' // optional
      });
      $('input[name="ReturningDate"]').daterangepicker({
        singleDatePicker: true,
        showDropdowns: true,
        locale: {
          format: 'YYYY-MM-DD'
        },
        startDate: this.addDateShow
        });
    $('#BorrowerBarcodeAdd').select2();
    $('#BorrowerBarcodeEdit').select2();
  }

  private getData() {
    this.http.get(this.serverLink+'/getAllReturningsREST')
                .map((res: Response) => res.json())
                 .subscribe(data => {
                        this.data = data;
                        // console.log(this.data);
                });       
  }

  getReturningById(id:string){

    this.http.get(this.serverLink+'/getReturningsREST/' + id).map((res: Response) => res.json())
    .subscribe(dataById => {
      this.dataById = dataById;
      // console.log(this.dataById);
      this.createdDateById = this.dataById.createddate;
      this.updatedDateById = this.dataById.updateddate;
      this.returningCreatedBy= this.dataById.librariansByCreatedby;
      this.http.get(this.serverLink+'/getLibrariansByIDREST/' + this.dataById.librariansByCreatedby).map((res: Response) => res.json())
        .subscribe(libDataCreated => {
          this.libDataCreated = libDataCreated;
          this.createdByName = this.libDataCreated.firstname + " " + this.libDataCreated.lastname;
          // console.log(this.libDataCreated);
        });

      // alert(this.createdById);
      this.http.get(this.serverLink+'/getLibrariansByIDREST/' +this.dataById.librariansByUpdatedby).map((res: Response) => res.json())
        .subscribe(libDataUpdated => {
          this.libDataUpdated = libDataUpdated;
          // console.log(this.libDataUpdated);
          this.updatedByName = this.libDataUpdated.firstname + " " + this.libDataUpdated.lastname;
        });
    });


  }

  private addReturning(){
    var formData= new FormData();
    formData.append('borrowerId',$('#BorrowerBarcodeAdd').val());
    formData.append('librariansByCreatedby',this.currentLibId);
    formData.append('librariansByUpdatedby',this.currentLibId);
    formData.append('returnDate',$('#ReturningDateAdd').val());
    formData.append('createdDate',this.getSystemDate());
    formData.append('updatedDate',this.getSystemDate());

    this.http.post(this.serverLink+'/addReturningsREST',formData).map((res: Response) => res.json())
    .subscribe(
      res => {
        // console.log(res);
        this.returningAddId=res.returnid;
      },
      err => {
        console.log(err);
      }
    );

    setTimeout(() => {
      this.getData();
      $("#modalAdd").modal("hide");
      var addSuccessful= new PNotify({
        title: 'Add Successfully !!!',
        text: 'You have added a new Returning successfully',
        type: 'success',
        addclass: "stack-topleft",
        animate: {
            animate: true,
            in_class: 'rotateInDownLeft',
            out_class: 'rotateOutUpRight'
        },delay:2000
      });
      this.router.navigate(['/ReturnDetail',this.returningAddId]);

    },500); 
  }
  
  initializeAddForm()
  {
    this.http.get(this.serverLink+'/getAllBorrowersREST')
    .map((res: Response) => res.json())
    .subscribe(data => {
      this.borrowerData = data;
    });
    setTimeout(() => {
    $('#BorrowerBarcodeAdd').empty().trigger("change");
    $.each(this.borrowerData, function (index, value) {
        this.tempIdData = value.borrowerid;
          var newOption = new Option(value.barcode, value.borrowerid, false, false);          
        $('#BorrowerBarcodeAdd').append(newOption).trigger('change');
      });

      this.borrowerIdAddHint=$("#BorrowerBarcodeAdd").val();
    
      this.http.get(this.serverLink+'/getBorrowersREST/' +this.borrowerIdAddHint).map((res: Response) => res.json())
        .subscribe(borrowerByIdHintData => {
          this.borrowerByIdHintData = borrowerByIdHintData;
          // console.log(this.borrowerByIdHintData);
          this.borrowerAddHint="<strong class='text-info'>Returner Name: </strong> " + this.borrowerByIdHintData.firstname+
          " "+ this.borrowerByIdHintData.lastname;
          $('#returnerHint').html(this.borrowerAddHint);
        });

      let http2 = this.http;
      let serverLink2 = this.serverLink;
      $('#BorrowerBarcodeAdd').on('change', function (e) {
        this.borrowerIdAddHint=$("#BorrowerBarcodeAdd").val();
    
        http2.get(serverLink2+'/getBorrowersREST/' +this.borrowerIdAddHint).map((res: Response) => res.json())
        .subscribe(borrowerByIdHintData => {
          this.borrowerByIdHintData = borrowerByIdHintData;
          // console.log(this.borrowerByIdHintData);
          this.borrowerAddHint="<strong class='text-info'>Returner Name: </strong> " + this.borrowerByIdHintData.firstname+
          " "+ this.borrowerByIdHintData.lastname;
          $('#returnerHint').html(this.borrowerAddHint);
        });
      });
    },100); 
  }

  initializeEditForm(id:string)
  {
      this.getReturningById(id);
      this.http.get(this.serverLink+'/getAllBorrowersREST')
      .map((res: Response) => res.json())
      .subscribe(data => {
        this.borrowerData = data;
      });

      setTimeout(() => {
        this.returningIdEdit = this.dataById.returnid;
        this.returningDateEdit = this.dataById.returndate;
        this.createdDateEdit = this.dataById.createddate;

        var currentBorrowerId = this.dataById.borrowers.borrowerid;
  
        $('#BorrowerBarcodeEdit').empty().trigger("change");
        $.each(this.borrowerData, function (index, value) 
        {
              var newOption
              if (currentBorrowerId == value.borrowerid)
              {
                newOption = new Option(value.barcode, value.borrowerid, false, true);                                          
              }
              else{
                newOption = new Option(value.barcode, value.borrowerid, false, false);                          
              }
            $('#BorrowerBarcodeEdit').append(newOption).trigger('change');
          
          });
          // inititialize returner name hint
          this.borrowerIdEditHint=$("#BorrowerBarcodeEdit").val();
          this.http.get(this.serverLink+'/getBorrowersREST/' +this.borrowerIdEditHint).map((res: Response) => res.json())
            .subscribe(borrowerByIdHintDataEdit => {
              this.borrowerByIdHintDataEdit = borrowerByIdHintDataEdit;
              // console.log(this.borrowerByIdHintDataEdit);
              this.borrowerEditHint="<strong class='text-info'>Returner Name:</strong> " + this.borrowerByIdHintDataEdit.firstname+
              " "+ this.borrowerByIdHintDataEdit.lastname;
              $('#returnerEditHint').html(this.borrowerEditHint);
            });
           // Change returner name hint
          let http2 = this.http;
          let serverLink2 = this.serverLink;
          $('#BorrowerBarcodeEdit').on('change', function (e) {
            this.borrowerIdEditHint=$("#BorrowerBarcodeEdit").val();
        
            http2.get(serverLink2+'/getBorrowersREST/' +this.borrowerIdEditHint).map((res: Response) => res.json())
            .subscribe(borrowerByIdHintDataEdit => {
              this.borrowerByIdHintDataEdit = borrowerByIdHintDataEdit;
              // console.log(this.borrowerByIdHintDataEdit);
              this.borrowerEditHint="<strong class='text-info'>Returner Name:</strong> " + this.borrowerByIdHintDataEdit.firstname+
              " "+ this.borrowerByIdHintDataEdit.lastname;
              $('#returnerEditHint').html(this.borrowerEditHint);
            });
          });
      },700); 
  }

  updateReturning()
  {
    var formData = new FormData();
    formData.append('borrowerId',$('#BorrowerBarcodeEdit').val());
    formData.append('returnId',this.returningIdEdit);
    formData.append('librariansByCreatedby',this.currentLibId);
    formData.append('librariansByUpdatedby',this.returningCreatedBy);
    formData.append('returnDate',$('#returnDateEdit').val());
    formData.append('createdDate',this.createdDateEdit);
    formData.append('updatedDate',this.getSystemDate());

    this.http.post(this.serverLink+'/updateReturningsREST',formData).subscribe(
      res => {
        // console.log(res);
      },
      err => {
        console.log(err);
      }
    );

    setTimeout(() => {
      this.getData();
      $("#modalEdit").modal("hide");
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
    },700); 
  }


  nearlyDelete(id:string)
  {
    this.nearlyDeleteId = id;
  }
  deleteReturning(){
    this.http.get(this.serverLink+'/deleteReturningsREST/'+this.nearlyDeleteId).map((res: Response) => res.json())
    .subscribe(data => {
           this.data = data;
          //  console.log(this.data);
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
     var deleteSuccessful = new PNotify({
       title: 'Error',
       text: 'Cannot delete this borrower',
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


  getSystemDate()
  {
    var d = new Date();
    
    var month = d.getMonth()+1;
    var day = d.getDate();
    
    var output = d.getFullYear() + '-' +
        (month<10 ? '0' : '') + month + '-' +
        (day<10 ? '0' : '') + day;
    return output;
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
