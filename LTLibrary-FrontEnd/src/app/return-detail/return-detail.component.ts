import { Component, OnInit } from '@angular/core';
import { Http, Response, Headers } from '@angular/http';
import { NgModel } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterModule, Router } from '@angular/router';
import 'rxjs/add/operator/map'
declare var $: any;
declare var PNotify: any;
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-return-detail',
  templateUrl: './return-detail.component.html',
  styleUrls: ['./return-detail.component.css'],
  providers: [CookieService]
})
export class ReturnDetailComponent implements OnInit {
  //Server link
  serverLink: string = 'http://localhost:8080/SpringMVCHibernateCRUDExample';

  //Variables for current Librarian
  currentLibLink: string = this.serverLink + '/image/librarian/'
  currentLibId: string;
  currentLibFName: string;
  currentLibLName: string;
  currentLibRole: string;
  libData: any;

  returningId: number;
  private sub: any;

  returningDetailData;
  dataById;
  bookNotYetReturnData;
  bordetailByIdData;
  bordetailByIdDataEdit;
  bordetailByIdDataEditCurrent;
  bordetailByIdDataDeleteCurrent;
  returningByIdData;
  // borrowerData;

  returnDetailStatus;
  returnDetailDescription: string = " ";

  descriptionAdd: string = "";
  descriptionEdit: string = " ";
  bookIdEdit;
  returnDetailIdEdit;
  currentBordetailId;
  nearlyDeleteId;

  ReturnerName;
  ReturningDate;

  borrowingDetailIdAddHint;
  bookByIdHintData;
  bookAddHint;

  borrowingDetailIdEditHint;
  bookByIdHintEditData;
  bookEditHint;
  constructor(private router: Router, private route: ActivatedRoute, private http: Http, private cookieService: CookieService) {
    this.sub = this.route.params.subscribe(params => {
      this.returningId = +params['id'];
    });
    this.getReturndetailsFromReturningId();
    this.getReturningById(this.returningId.toString());
  }

  ngOnInit() {
    //Check valid access
    if (!this.cookieService.check('loginLibId') || !this.cookieService.check('loginLibLastName')
      || !this.cookieService.check('loginLibFirstName')) {
      this.router.navigate(['./Login']);
    }
    else {
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
    }, 1000);
    $('#btnAdd').click(function () {
      $('#modalAdd').modal("show");
    });
    $('#editClick').click(function () {
      $('#modalEdit').modal("show");
    });
    $('#moreInfoClick').click(function () {
      $('#modalInfo').modal("show");
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
    $('#ReturningStatus').select2();
    $('#ReturningStatusEdit').select2();
  }

  getReturndetailsFromReturningId() {
    // alert(id);
    this.http.get(this.serverLink + '/getReturndetailsFromReturningId/' + this.returningId).map((res: Response) => res.json())
      .subscribe(data => {
        this.returningDetailData = data;
        // console.log(this.returningDetailData);
      });
  }

  private getReturndetailbyId(id: string) {
    this.http.get(this.serverLink + '/getReturnDetailsREST/' + id).map((res: Response) => res.json())
      .subscribe(dataEdit => {
        this.dataById = dataEdit;
        // console.log(this.dataById);
      });
    setTimeout(() => {
      // this.bookIdEdit= this.dataById.borrowingdetails.books.bookid;
      if (this.dataById.returnstatus == 0) {
        this.returnDetailStatus = "Good Reserve";
      } else if (this.dataById.returnstatus == 1) {
        this.returnDetailStatus = "Bad Reserve";
      } else {
        this.returnDetailStatus = "Lost";
      }
      this.currentBordetailId = this.dataById.borrowingdetails.bordetailid;
      this.returnDetailDescription = this.dataById.description;
      // alert(this.currentBordetailId);
    }, 1000);
  }

  initializeAddForm() {
    this.http.get(this.serverLink + '/getAllBookNotYetReturnREST')
      .map((res: Response) => res.json())
      .subscribe(data => {
        this.bookNotYetReturnData = data;
      });

    var returnDetailStatusAddData: any = ["Good Reserve", "Bad Reserve", "Lost"];

    setTimeout(() => {
      $('#BookBarcodeAdd').empty().trigger("change");
      $.each(this.bookNotYetReturnData, function (index, value) {
        var newOption = new Option(value.barCode, value.bordetailId, false, false);
        $('#BookBarcodeAdd').append(newOption).trigger('change');
      });
      this.borrowingDetailIdAddHint = $("#BookBarcodeAdd").val();

      this.http.get(this.serverLink + '/getBorrowingDetailsREST/' + this.borrowingDetailIdAddHint).map((res: Response) => res.json())
        .subscribe(bookByIdHintData => {
          this.bookByIdHintData = bookByIdHintData;
          // console.log(this.bookByIdHintData);
          this.bookAddHint = "<strong class='text-info'>Book Title: </strong> " + this.bookByIdHintData.books.titles.booktitle;
          $('#bookAddHint').html(this.bookAddHint);
        });

      let http2 = this.http;
      let serverLink2 = this.serverLink;
      $('#BookBarcodeAdd').on('change', function (e) {
        this.borrowingDetailIdAddHint = $("#BookBarcodeAdd").val();
        http2.get(serverLink2 + '/getBorrowingDetailsREST/' + this.borrowingDetailIdAddHint)
          .map((res: Response) => res.json())
          .subscribe(bookByIdHintData => {
            this.bookByIdHintData = bookByIdHintData;
            // console.log(this.bookByIdHintData);
            this.bookAddHint = "<strong class='text-info'>Book Title: </strong> " + this.bookByIdHintData.books.titles.booktitle;
            $('#bookAddHint').html(this.bookAddHint);
          });
      });

      // $('#BorrowerBarcodeAdd').empty().trigger("change");
      // $.each(this.borrowerData, function (index, value) {
      //     this.tempIdData = value.borrowerid;
      //       var newOption = new Option(value.barcode, value.borrowerid, false, false);          
      //     $('#BorrowerBarcodeAdd').append(newOption).trigger('change');
      //   });

      $('#ReturningStatus').empty().trigger("change");
      $.each(returnDetailStatusAddData, function (index, value) {
        var newOption = new Option(value, index, false, false);
        $('#ReturningStatus').append(newOption).trigger('change');
      });


    }, 1000);
  }

  private addReturningDetail() {
    var formData = new FormData();
    formData.append("bordetailId", $("#BookBarcodeAdd").val());
    formData.append("returnId", this.returningId.toString());
    formData.append("returnStatus", $("#ReturningStatus").val());
    formData.append("description", this.descriptionAdd);

    this.http.post(this.serverLink + '/addReturnDetailsREST', formData)
      .map((res: Response) => res.json())
      .subscribe(
      res2 => {
        // console.log(res2);
      },
      err => {
        console.log(err);
      }
      );
    this.http.get(this.serverLink + '/getBorrowingDetailsREST/' + $("#BookBarcodeAdd").val())
      .map((res: Response) => res.json())
      .subscribe(bordetailByIdData => {
        this.bordetailByIdData = bordetailByIdData;
        // console.log(this.bordetailByIdData);
      });


    setTimeout(() => {

      var formData2 = new FormData();
      formData2.append("isReturn", "true");
      formData2.append("borrowingId", this.bordetailByIdData.borrowings.borrowingid);
      formData2.append("bookId", this.bordetailByIdData.books.bookid);
      formData2.append("bordetailId", this.bordetailByIdData.bordetailid);

      this.http.post(this.serverLink + '/updateBorrowingDetailsREST', formData2)
        .map((res: Response) => res.json())
        .subscribe(
        res2 => {
          // console.log(res2);
        },
        err => {
          console.log(err);
        });
      this.getReturndetailsFromReturningId();
      $("#modalAdd").modal('hide');
      var addSuccessful = new PNotify({
        title: 'Add Successfully !!!',
        text: 'You have added a new Returning Detail successfully',
        type: 'success',
        addclass: "stack-topleft",
        animate: {
          animate: true,
          in_class: 'rotateInDownLeft',
          out_class: 'rotateOutUpRight'
        }, delay: 2000
      });

    }, 1000);
  }

  initializeEditForm(id: string) {
    this.getReturndetailbyId(id);
    // alert(this.currentBordetailId);
    this.http.get(this.serverLink + '/getAllBookNotYetReturnREST')
      .map((res: Response) => res.json())
      .subscribe(data => {
        this.bookNotYetReturnData = data;
      });
    var returnDetailStatusEditData: any = ["Good Reserve", "Bad Reserve", "Lost"];

    setTimeout(() => {
      $('#BookBarcodeEdit').empty().trigger("change");
      for (let value of this.bookNotYetReturnData) {
        var newOption = new Option(value.barCode, value.bordetailId, false, false);
        $('#BookBarcodeEdit').append(newOption).trigger('change');
      }

      $('#ReturningStatusEdit').empty().trigger("change");
      $.each(returnDetailStatusEditData, function (index, value) {
        var newOption = new Option(value, index, false, false);
        $('#ReturningStatusEdit').append(newOption).trigger('change');
      });
      this.descriptionEdit = this.returnDetailDescription;

      this.borrowingDetailIdEditHint = $("#BookBarcodeEdit").val();
      this.http.get(this.serverLink + '/getBorrowingDetailsREST/' + this.borrowingDetailIdEditHint).map((res: Response) => res.json())
        .subscribe(bookByIdHintEditData => {
          this.bookByIdHintEditData = bookByIdHintEditData;
          // console.log(this.bookByIdHintEditData);
          this.bookEditHint = "<strong class='text-info'>Book Title: </strong> " + this.bookByIdHintEditData.books.titles.booktitle;
          $('#bookEditHint').html(this.bookEditHint);
        });

      let http2 = this.http;
      let serverLink2 = this.serverLink;
      $('#BookBarcodeEdit').on('change', function (e) {
        this.borrowingDetailIdEditHint = $("#BookBarcodeEdit").val();

        http2.get(serverLink2 + '/getBorrowingDetailsREST/' + this.borrowingDetailIdEditHint).map((res: Response) => res.json())
          .subscribe(bookByIdHintEditData => {
            this.bookByIdHintEditData = bookByIdHintEditData;
            // console.log(this.bookByIdHintEditData);
            this.bookEditHint = "<strong class='text-info'>Book Title: </strong> " + this.bookByIdHintEditData.books.titles.booktitle;
            $('#bookEditHint').html(this.bookEditHint);
          });
      });
    }, 1000);
    this.returnDetailIdEdit = id;
  }

  updateReturningDetail() {
    var formData = new FormData();
    formData.append("bordetailId", $("#BookBarcodeEdit").val());
    formData.append("returnId", this.returningId.toString());
    formData.append("returnStatus", $("#ReturningStatusEdit").val());
    formData.append("description", this.descriptionEdit);
    formData.append("returnDetailid", this.returnDetailIdEdit);

    this.http.post(this.serverLink + '/updateReturnDetailsREST', formData)
      .map((res: Response) => res.json())
      .subscribe(
      res2 => {
        // console.log(res2);
      },
      err => {
        console.log(err);
      }
      );

    this.http.get(this.serverLink + '/getBorrowingDetailsREST/' + $("#BookBarcodeEdit").val())
      .map((res: Response) => res.json())
      .subscribe(bordetailByIdDataEdit => {
        this.bordetailByIdDataEdit = bordetailByIdDataEdit;
        // console.log(this.bordetailByIdDataEdit);
      });
    this.http.get(this.serverLink + '/getBorrowingDetailsREST/' + this.currentBordetailId)
      .map((res: Response) => res.json())
      .subscribe(bordetailByIdDataEditCurrent => {
        this.bordetailByIdDataEditCurrent = bordetailByIdDataEditCurrent;
        // console.log(this.bordetailByIdDataEditCurrent);
      });

    setTimeout(() => {

      var formData2 = new FormData();
      formData2.append("isReturn", "true");
      formData2.append("borrowingId", this.bordetailByIdDataEdit.borrowings.borrowingid);
      formData2.append("bookId", this.bordetailByIdDataEdit.books.bookid);
      formData2.append("bordetailId", this.bordetailByIdDataEdit.bordetailid);

      this.http.post(this.serverLink + '/updateBorrowingDetailsREST', formData2)
        .map((res: Response) => res.json())
        .subscribe(
        res2 => {
          // console.log(res2);
        },
        err => {
          console.log(err);
        });

      var formData3 = new FormData();
      formData3.append("isReturn", "false");
      formData3.append("borrowingId", this.bordetailByIdDataEditCurrent.borrowings.borrowingid);
      formData3.append("bookId", this.bordetailByIdDataEditCurrent.books.bookid);
      formData3.append("bordetailId", this.bordetailByIdDataEditCurrent.bordetailid);

      this.http.post(this.serverLink + '/updateBorrowingDetailsREST', formData3)
        .map((res: Response) => res.json())
        .subscribe(
        res2 => {
          // console.log(res2);
        },
        err => {
          console.log(err);
        });
      this.getReturndetailsFromReturningId();
      $("#modalEdit").modal('hide');
      var updateSuccessful = new PNotify({
        title: 'Update Successfully !!!',
        text: 'You have updated successfully',
        type: 'info',
        addclass: "stack-topleft",
        animate: {
          animate: true,
          in_class: 'rotateInDownLeft',
          out_class: 'rotateOutUpRight'
        }, delay: 2000
      });
    }, 1000);


  }

  nearlyDelete(id: string) {
    this.getReturndetailbyId(id);
    this.nearlyDeleteId = id;
  }
  deleteReturningDetail() {
    this.http.get(this.serverLink + '/deleteReturnDetailsREST/' + this.nearlyDeleteId).map((res: Response) => res.json())
      .subscribe(data => {
        // console.log(this.returningDetailData);
      });
    this.http.get(this.serverLink + '/getBorrowingDetailsREST/' + this.currentBordetailId)
      .map((res: Response) => res.json())
      .subscribe(bordetailByIdDataDeleteCurrent => {
        this.bordetailByIdDataDeleteCurrent = bordetailByIdDataDeleteCurrent;
        // console.log(this.bordetailByIdDataDeleteCurrent);
      });

    setTimeout(() => {
      var formData = new FormData();
      formData.append("isReturn", "false");
      formData.append("borrowingId", this.bordetailByIdDataDeleteCurrent.borrowings.borrowingid);
      formData.append("bookId", this.bordetailByIdDataDeleteCurrent.books.bookid);
      formData.append("bordetailId", this.bordetailByIdDataDeleteCurrent.bordetailid);

      this.http.post(this.serverLink + '/updateBorrowingDetailsREST', formData)
        .map((res: Response) => res.json())
        .subscribe(
        res2 => {
          // console.log(res2);
          this.getReturndetailsFromReturningId();
          $("#modalDelete").modal("hide");
          var deleteSuccessful = new PNotify({
            title: 'Delete Successfully !!!',
            text: 'You have deleted successfully',
            type: 'error',
            addclass: "stack-topleft",
            animate: {
              animate: true,
              in_class: 'zoomInLeft',
              out_class: 'zoomOutRight'
            }, delay: 2000
          });
        },
        err => {
          console.log(err);
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

    }, 1500);
  }

  getPenalty(borDate: string, returnDate: string, penaltyPerDay: number, maxDayIssue:number) {
    var borDateTemp = new Date(borDate);
    var returnDateTemp = new Date(returnDate);
    var maxDayIssueTemp= maxDayIssue ;

    var timeDiff = Math.abs(borDateTemp.getTime() - returnDateTemp.getTime());
    var diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24));

    if(diffDays<=maxDayIssue)
    {
      return 0;
    }else{
      return this.addCommas((diffDays-maxDayIssue) * penaltyPerDay);
    }

   
  }

  // doSomething(){
  //   alert(this.descriptionAdd);
  // }

  getReturningById(id: string) {
    this.http.get(this.serverLink + '/getReturningsREST/' + id).map((res: Response) => res.json())
      .subscribe(returningByIdData => {
        this.returningByIdData = returningByIdData;
        // console.log(this.returningByIdData);
      });

    setTimeout(() => {
      this.ReturnerName = this.returningByIdData.borrowers.firstname + " " + this.returningByIdData.borrowers.lastname;
      this.ReturningDate = this.returningByIdData.returndate;
    }, 500);
  }
  done() {
    this.router.navigate(['/Returning']);
  }

  //Sign out
  signOut() {
    this.cookieService.delete('loginLibId');
    this.cookieService.delete('loginLibFirstName');
    this.cookieService.delete('loginLibLastName');
    this.cookieService.delete('loginLibRole');
    this.router.navigate(['./Login']);
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


}
