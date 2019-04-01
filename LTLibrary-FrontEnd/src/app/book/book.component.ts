import { Component, OnInit } from '@angular/core';
import { Http, Response, Headers } from '@angular/http';
import { NgModel } from '@angular/forms';
import 'rxjs/add/operator/map'
import { LIFECYCLE_HOOKS_VALUES } from '@angular/compiler/src/lifecycle_reflector';
import { DomSanitizer } from '@angular/platform-browser';
import { CookieService } from 'ngx-cookie-service';
import { Router } from '@angular/router';

declare var $: any;
declare var angular: any;
declare var $: any;
declare var PNotify :any;
@Component({
  selector: 'app-book',
  templateUrl: './book.component.html',
  styleUrls: ['./book.component.css'],
  providers: [CookieService]
})

export class BookComponent implements OnInit {
  //Server link
  serverLink: string = 'http://localhost:8080/SpringMVCHibernateCRUDExample';
  
  //Variables for current Librarian
  currentLibLink:string = this.serverLink+'/image/librarian/'
  currentLibId:string;
  currentLibFName:string;
  currentLibLName:string;
  currentLibRole:string;
  libData:any;

  data: any = null;
  dataEdit: any = [];
  titleData: any = [];

  bookId: string = "";
  barCode: string = "";
  realPrice: string = "";
  librariansByCreatedby: string = "";
  librariansByUpdatedby: string = "";
  bookStatus: string = "";
  note: string = "";
  createdDate: string = "";
  updatedDate: string = "";
  isbn: string = "";

  bookIdEdit: string = "";
  titleIdEdit: string = "";
  barCodeEdit: string = "";
  realPriceEdit: string = "";
  librariansByCreatedbyEdit: string = "";
  librariansByUpdatedbyEdit: string = "";
  bookStatusEdit: string = "";
  bookStatusEditMeaning: string = "";
  noteEdit: string = "";
  updatedDateEdit: string = "";
  createdDateEdit: string = "";

  nearlyDeleteId: string = "";

  titleIdAdd;
  titleIdEditSelection;
  titleByIdAddData;
  titleByIdEditData;
  bookTitleAddHint;
  bookTitleEditHint;

  createdById;
  updatedById;
  libDataCreated;
  libDataUpdated;

  createdByName;
  updatedByName;

  constructor(private http: Http,private cookieService: CookieService , private router: Router) {
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
      $('#tbl tfoot th').each(function () {
        var title = $(this).text();
        $(this).html('<input type="text" placeholder="Search ' + title + '" />');
      });

      var table = $('#tbl').DataTable();

      table.columns().every(function () {
        var that = this;

        $('input', this.footer()).on('keyup change', function () {
          if (that.search() !== this.value) {
            that
              .search(this.value)
              .draw();
          }
        });
      });
      //end datatable
      $('#btnAdd').click(function () {
        $('#modalAdd').modal("show");
      });
      $('#editClick').click(function () {
        $('#modalEdit').modal("show");
      });
      $('#moreInfoClick').click(function () {
        $('#modalInfo').modal("show");
      });
      $('#isbnSelectionAdd').select2();
      $('#isbnSelectionEdit').select2();
      $('#statusSelectionAdd').select2();
      $('#statusSelectionEdit').select2();
    }, 500);
  }

  //-------------------------------MAIN METHODS-------------------------------//
  //Get all book data
  private getData() {
    return this.http.get(this.serverLink+'/getAllBooksREST')
      .map((res: Response) => res.json())
      .subscribe(data => {
        this.data = data;
        // console.log(this.data);
      });
  }

  //Get all book titles
  getAllTitle() {
    // alert(id);
    this.http.get(this.serverLink+'/getAllTitlesREST/').map((res: Response) => res.json())
      .subscribe(dataEdit => {
        this.titleData = dataEdit;
        // console.log(this.titleData);
      });
  }

  //Get a book by id
  getBookById(id: string) {
    // alert(id);
    
    this.http.get(this.serverLink+'/getBooksREST/' + id).map((res: Response) => res.json())
      .subscribe(dataEdit => {
        this.dataEdit = dataEdit;
        // console.log(this.dataEdit);
      });

    setTimeout(() => {
      this.bookIdEdit = this.dataEdit.bookid;
      this.barCodeEdit = this.dataEdit.barcode;
      this.realPriceEdit = this.dataEdit.realprice;
      this.librariansByCreatedbyEdit = this.dataEdit.librariansByCreatedby;
      this.librariansByUpdatedbyEdit = this.dataEdit.librariansByUpdatedby;
      this.bookStatusEdit = this.dataEdit.bookstatus;
      this.titleIdEdit = this.dataEdit.titles.titleid;

      if (this.bookStatusEdit == '0') {
        this.bookStatusEditMeaning = "New";
      }
      else if (this.bookStatusEdit == '1') {
        this.bookStatusEditMeaning = "Second-hand";
      }
      else {
        this.bookStatusEditMeaning = "Liquidated";
      }
      this.noteEdit = this.dataEdit.note;
      this.updatedDateEdit = this.dataEdit.updateddate;
      this.createdDateEdit = this.dataEdit.createddate;

   
        // alert(this.createdById);
        this.http.get(this.serverLink+'/getLibrariansByIDREST/' + this.librariansByCreatedbyEdit).map((res: Response) => res.json())
        .subscribe(libDataCreated => {
          this.libDataCreated = libDataCreated;
          this.createdByName= this.libDataCreated.firstname+" "+this.libDataCreated.lastname;
          // console.log(this.libDataCreated);
        });
 
  
  
        // alert(this.createdById);
        this.http.get(this.serverLink+'/getLibrariansByIDREST/' + this.librariansByUpdatedbyEdit).map((res: Response) => res.json())
        .subscribe(libDataUpdated => {
          this.libDataUpdated = libDataUpdated;
          // console.log(this.libDataUpdated);
          this.updatedByName= this.libDataUpdated.firstname+" "+this.libDataUpdated.lastname;
        });

    }, 500);
  }

  //Initialize add form
  initializeAddForm() {
    $("isbnSelectionAdd").val('');
    this.getAllTitle();
    setTimeout(() => {
      //Add data to select box
      $('#isbnSelectionAdd').empty().trigger("change");
      for (let value of this.titleData) {
        var newOption = new Option(value.isbn, value.titleid, false, false);
        $('#isbnSelectionAdd').append(newOption).trigger('change'); []
      }

      this.titleIdAdd=$("#isbnSelectionAdd").val();
      this.http.get(this.serverLink+'/getTitlesREST/' +this.titleIdAdd).map((res: Response) => res.json())
        .subscribe(titleByIdAddData => {
          this.titleByIdAddData = titleByIdAddData;
          // console.log(this.titleByIdAddData);
          this.bookTitleAddHint="<strong class='text-info'>Book title:</strong> " + this.titleByIdAddData.booktitle;
          $('#bookTitleHint').html(this.bookTitleAddHint);
        });



      let http2 = this.http;
      let serverLink2 = this.serverLink;
      var that=this;
      $('#isbnSelectionAdd').on('change', function (e) {
        that.titleIdAdd=$("#isbnSelectionAdd").val();
        http2.get(serverLink2+'/getTitlesREST/' +that.titleIdAdd).map((res: Response) => res.json())
        .subscribe(titleByIdAddData => {
          that.titleByIdAddData = titleByIdAddData;
          // console.log(that.titleByIdAddData);
          that.bookTitleAddHint="<strong class='text-info'>Book title:</strong> " + that.titleByIdAddData.booktitle;
          $('#bookTitleHint').html(that.bookTitleAddHint);
        });
      });
      
    }, 500);
  }

  //Add form validation
  checkAddForm() {
    if (this.barCode == "")
    {
      var deleteSuccessful = new PNotify({
        title: 'Error',
        text: 'Barcode name cannot be empty',
        type: 'error',
        addclass: "stack-topleft",
        animate: {
          animate: true,
          in_class: 'zoomInLeft',
          out_class: 'zoomOutRight'
        },
        delay: 2000
      });
      return ;
    }
    else if (isNaN(Number(this.realPrice)) || Number(this.realPrice)<0) {
      var deleteSuccessful = new PNotify({
        title: 'Error',
        text: 'Real price has to be number and has to be positive',
        type: 'error',
        addclass: "stack-topleft",
        animate: {
          animate: true,
          in_class: 'zoomInLeft',
          out_class: 'zoomOutRight'
        },
        delay: 2000
      });
      return ;
    }
    else if (this.realPrice == "") {
      var deleteSuccessful = new PNotify({
        title: 'Error',
        text: 'Real price cannot be empty',
        type: 'error',
        addclass: "stack-topleft",
        animate: {
          animate: true,
          in_class: 'zoomInLeft',
          out_class: 'zoomOutRight'
        },
        delay: 2000
      });
      return ;
    }
    else
    {
      this.addBook();
    }
  }

  //Edit form validation
  checkEditForm() {
    if (this.barCodeEdit == "")
    {
      var deleteSuccessful = new PNotify({
        title: 'Error',
        text: 'Barcode name cannot be empty',
        type: 'error',
        addclass: "stack-topleft",
        animate: {
          animate: true,
          in_class: 'zoomInLeft',
          out_class: 'zoomOutRight'
        },
        delay: 2000
      });
      return ;
    }
    else if (isNaN(Number(this.realPriceEdit)) || Number(this.realPriceEdit)<0) {
      var deleteSuccessful = new PNotify({
        title: 'Error',
        text: 'Real price has to be number and has to be positive',
        type: 'error',
        addclass: "stack-topleft",
        animate: {
          animate: true,
          in_class: 'zoomInLeft',
          out_class: 'zoomOutRight'
        },
        delay: 2000
      });
      return ;
    }
    else if (this.realPriceEdit == "") {
      var deleteSuccessful = new PNotify({
        title: 'Error',
        text: 'Real price cannot be empty',
        type: 'error',
        addclass: "stack-topleft",
        animate: {
          animate: true,
          in_class: 'zoomInLeft',
          out_class: 'zoomOutRight'
        },
        delay: 2000
      });
      return ;
    }
    this.updateBook();
  }

  //Add a book
  addBook() {
    var formData = new FormData();
    formData.append('titleId', $("#isbnSelectionAdd").val());

    formData.append('librariansByCreatedby', this.currentLibId);
    formData.append('librariansByUpdatedby', this.currentLibId);

    formData.append('barCode', this.barCode);
    formData.append('bookStatus', $("#statusSelectionAdd").val());
    formData.append('note', this.note);
    formData.append('realPrice', this.realPrice);

    formData.append('createdDate', this.getSystemDate());
    formData.append('updatedDate', this.getSystemDate());

    this.barCode = "";
    this.note = "";
    this.realPrice = "";
    this.http.post(this.serverLink+'/addBooksREST', formData)
      .map((res: Response) => res.json())
      .subscribe(
      res2 => {
        // console.log(res2);
        var addSuccessful= new PNotify({
          title: 'Add Successfully !!!',
          text: 'You have added the book <strong>'+this.titleByIdAddData.booktitle+'</strong> successfully',
          type: 'success',
          addclass: "stack-topleft",
          animate: {
              animate: true,
              in_class: 'rotateInDownLeft',
              out_class: 'rotateOutUpRight'
          },delay:2000
        });
      },
      err => {
        console.log(err);
      }
      );

    setTimeout(() => {
      this.getData();
      $("#modalAdd").modal('hide');
    }, 1000);
  }

  //Initialize edit form
  initializeEditForm(id: string) {
    this.getBookById(id);
    this.getAllTitle();

    setTimeout(() => {
      $('#isbnSelectionEdit').empty().trigger("change");
      for (let value of this.titleData) {
        if (value.titleid == this.titleIdEdit) {
          var newOption = new Option(value.isbn, value.titleid, false, true);
          $('#isbnSelectionEdit').append(newOption).trigger('change');
        }
        else {
          var newOption = new Option(value.isbn, value.titleid, false, false);
          $('#isbnSelectionEdit').append(newOption).trigger('change');
        }
      }
      $('#statusSelectionEdit').val(this.bookStatusEdit);
      $('#statusSelectionEdit').trigger('change');

      this.titleIdEditSelection=$("#isbnSelectionEdit").val();
      this.http.get(this.serverLink+'/getTitlesREST/' +this.titleIdEditSelection).map((res: Response) => res.json())
      .subscribe(titleByIdEditData => {
        this.titleByIdEditData = titleByIdEditData;
        // console.log(this.titleByIdEditData);
        this.bookTitleEditHint="<strong class='text-info'>Book title:</strong> " + this.titleByIdEditData.booktitle;
        $('#bookTitleHintEdit').html(this.bookTitleEditHint);
      });

      let http2 = this.http;
      let serverLink2 = this.serverLink;
      var that=this;
      $('#isbnSelectionEdit').on('change', function (e) {
        that.titleIdEditSelection=$("#isbnSelectionEdit").val();
        http2.get(serverLink2+'/getTitlesREST/' +that.titleIdEditSelection).map((res: Response) => res.json())
        .subscribe(titleByIdEditData => {
          that.titleByIdEditData = titleByIdEditData;
          // console.log(that.titleByIdEditData);
          that.bookTitleEditHint="<strong class='text-info'>Book title:</strong> " + that.titleByIdEditData.booktitle;
          $('#bookTitleHintEdit').html(that.bookTitleEditHint);
        });
      });
    }, 500);

  }

  //Update a book
  updateBook() {
    var formData = new FormData();
    formData.append('titleId', $("#isbnSelectionEdit").val());

    formData.append('librariansByCreatedby',this.librariansByCreatedbyEdit);
    formData.append('librariansByUpdatedby', "1");

    formData.append('bookId', this.bookIdEdit);
    formData.append('barCode', this.barCodeEdit);
    formData.append('bookStatus', $("#statusSelectionEdit").val());
    formData.append('note', this.noteEdit);
    formData.append('realPrice', this.realPriceEdit);

    formData.append('createdDate', this.createdDateEdit);
    formData.append('updatedDate', this.getSystemDate());

    
    this.http.post(this.serverLink+'/updateBooksREST', formData)
      .map((res: Response) => res.json())
      .subscribe(
      res => {
        // console.log(res);
        var updateSuccessful= new PNotify({
          title: 'Update Successfully !!!',
          text: 'You have updated the book <strong>'+this.titleByIdEditData.booktitle+'</strong> successfully',
          type: 'info',
          addclass: "stack-topleft",
          animate: {
              animate: true,
              in_class: 'rotateInDownLeft',
              out_class: 'rotateOutUpRight'
          },delay:2000
        });
      },
      err => {
        console.log(err);
      }
      );
    setTimeout(() => {
      this.getData();
      $("#modalEdit").modal('hide');
    }, 500);
  }

  nearlyDelete(id: string) {
    this.nearlyDeleteId = id;
  }

  //Delete a book
  deleteBook() {
    this.http.get(this.serverLink+'/deleteBooksREST/' + this.nearlyDeleteId).map((res: Response) => res.json())
      .subscribe(data => {
        this.data = data;
        // console.log(this.data);
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
        })
      },err=>
      {
        $("#modalDelete").modal("hide");
        var deleteSuccessful = new PNotify({
          title: 'Error',
          text: 'Cannot delete this book',
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

  //-------------------------------END MAIN METHODS-------------------------------//
  //Get system data
  getSystemDate() {
    var d = new Date();

    var month = d.getMonth() + 1;
    var day = d.getDate();

    var output = d.getFullYear() + '-' +
      (month < 10 ? '0' : '') + month + '-' +
      (day < 10 ? '0' : '') + day;
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

  //Add deciamal separator
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
  getBookStatus(status:string){
    if (status == '0') {
      return "New";
    }
    else if (status == '1') {
      return "Second-hand";
    }
    else {
      return "Liquidated";
    }
  }


}