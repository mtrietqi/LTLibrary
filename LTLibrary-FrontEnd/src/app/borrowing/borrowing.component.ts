import { Component, OnInit } from '@angular/core';
import { Http, Response, Headers } from '@angular/http';
import { NgModel } from '@angular/forms';
import 'rxjs/add/operator/map'
import { ReturnComponent } from '../return/return.component';
import { RouterModule, Router } from '@angular/router';
declare var $: any;
declare var PNotify: any;
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-borrowing',
  templateUrl: './borrowing.component.html',
  styleUrls: ['./borrowing.component.css'],
  providers: [CookieService]
})
export class BorrowingComponent implements OnInit {
  //Server link
  serverLink: string = 'http://localhost:8080/SpringMVCHibernateCRUDExample';

  //Variables for current Librarian
  currentLibLink: string = this.serverLink+'/image/librarian/'
  currentLibId: string;
  currentLibFName: string;
  currentLibLName: string;
  currentLibRole: string;
  libData: any;

  data: any = null;
  bordates = [];
  expiredDates = [];

  addDateShow = new Date();

  dataById;
  updatedDateById;
  createdDateById;
  updatedBy;
  createdBy;

  librarianData;
  borrowerData;

  borrowerIdAdd;
  librariansByCreatedbyAdd = this.currentLibId;
  librariansByUpdatedbyAdd = this.currentLibId;
  borDateAdd = "2017-12-12";
  createdDateAdd = new Date().getFullYear() + "-" + (new Date().getMonth() + 1) + "-" + new Date().getDate();
  updatedDateAdd = new Date().getFullYear() + "-" + (new Date().getMonth() + 1) + "-" + new Date().getDate();
  borrowingDateEdit: string;
  createdDateEdit: string;
  borrowingIdEdit: string;
  borrowingCreatedBy: string;

  nearlyDeleteId: string;

  borrowingAddId;

  borrowerIdAddHint;
  borrowerAddHint;
  borrowerIdEditHint;
  borrowerEditHint;
  borrowerByIdHintData;
  borrowerByIdHintDataEdit;

  createdById;
  updatedById;
  libDataCreated;
  libDataUpdated;

  createdByName;
  updatedByName;

  constructor(private router: Router, private http: Http, private cookieService: CookieService) {
    this.getData();

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
    $('input[name="BorrowingDate"]').daterangepicker({
      singleDatePicker: true,
      showDropdowns: true,
      locale: {
        format: 'YYYY-MM-DD'
      },
      startDate: this.addDateShow
      // function(start) { 
      //   this.borDateAdd=start;
      //  }
    });

    $('#BorrowerBarcodeAdd').select2();
    $('#BorrowerBarcodeEdit').select2();
  }

  private getData() {
    this.http.get(this.serverLink+'/getAllBorrowingsREST')
      .map((res: Response) => res.json())
      .subscribe(data => {
        this.data = data;
        // console.log(this.data);
      });
    setTimeout(() => {
      for (let value of this.data) {
        // console.log(Date.parse(value.bordate));
        var dateTemp = new Date(value.bordate);
        var dateTemp2 = new Date(value.bordate);
        dateTemp2.setDate(dateTemp2.getDate() + value.borrowers.borrowertype.maxdayissue);
        this.bordates.push(dateTemp.getFullYear() + "-" + (dateTemp.getMonth() + 1) + "-" + dateTemp.getDate());
        this.expiredDates.push(dateTemp2.getFullYear() + "-" + (dateTemp2.getMonth() + 1) + "-" + dateTemp2.getDate());
      }
    }, 500);
  }

  getBorrowingById(id: string) {
    this.http.get(this.serverLink+'/getBorrowingsREST/' + id).map((res: Response) => res.json())
      .subscribe(dataById => {
        this.dataById = dataById;
        // console.log(this.dataById);
        this.createdDateById = this.dataById.createddate;
        this.updatedDateById = this.dataById.updateddate;
        this.borrowingCreatedBy = this.dataById.librariansByCreatedby;
        // alert(this.createdById);
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

  private addData(borrowerIdAdd: string, librariansByCreatedbyAdd: string, librariansByUpdatedbyAdd: string, borDateAdd: string, createdDateAdd: string, updatedDateAdd: string) {
    var formData = new FormData();
    formData.append('borrowerId', $('#BorrowerBarcodeAdd').val());
    formData.append('librariansByCreatedby', this.currentLibId);
    formData.append('librariansByUpdatedby', this.currentLibId);
    formData.append('borDate', $('#bordateAdd').val());
    formData.append('createdDate', this.getSystemDate());
    formData.append('updatedDate', this.getSystemDate());
    // let borrowing=
    // {
    //   borrowerId: 22,
    //   librariansByCreatedby: 1,
    //   librariansByUpdatedby: 1,
    //   borDate: "2017-12-31",
    //   createdDate: "2017-12-31",
    //   updatedDate: "2017-12-31"
    // };

    this.http.post(this.serverLink+'/addBorrowingsRESTNormal', formData).map((res: Response) => res.json())
      .subscribe(
      res => {
        // console.log(res);
        this.borrowingAddId = res.borrowingid;

      },
      err => {
        console.log(err);
      }
      );
  }

  initializeAddForm() {
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

      this.borrowerIdAddHint = $("#BorrowerBarcodeAdd").val();
      this.http.get(this.serverLink+'/getBorrowersREST/' + this.borrowerIdAddHint).map((res: Response) => res.json())
        .subscribe(borrowerByIdHintData => {
          this.borrowerByIdHintData = borrowerByIdHintData;
          // console.log(this.borrowerByIdHintData);
          this.borrowerAddHint = "<strong class='text-info'>Borrower Name:</strong> " + this.borrowerByIdHintData.firstname +
            " " + this.borrowerByIdHintData.lastname;
          $('#borrowerHint').html(this.borrowerAddHint);
        });

      let http2 = this.http;
      let serverLink2 = this.serverLink;
      let that = this;
      $('#BorrowerBarcodeAdd').on('change', function (e) {
        that.borrowerIdAddHint = $("#BorrowerBarcodeAdd").val();

        http2.get(serverLink2+'/getBorrowersREST/' + that.borrowerIdAddHint).map((res: Response) => res.json())
          .subscribe(borrowerByIdHintData => {
            that.borrowerByIdHintData = borrowerByIdHintData;
            // console.log(that.borrowerByIdHintData);
            that.borrowerAddHint = "<strong class='text-info'>Borrower Name: </strong> " + that.borrowerByIdHintData.firstname +
              " " + that.borrowerByIdHintData.lastname;
            $('#borrowerHint').html(that.borrowerAddHint);
          });
      });
    }, 100);
  }

  addBorrowing() {
    this.addData(this.borrowerIdAdd, this.librariansByCreatedbyAdd, this.librariansByUpdatedbyAdd, this.borDateAdd, this.createdDateAdd, this.updatedDateAdd);
    setTimeout(() => {
      this.getData();
      this.borrowerIdAdd = "";
      this.borDateAdd = "";
      $("#modalAdd").modal("hide");
      var addSuccessful = new PNotify({
        title: 'Add Successfully !!!',
        text: 'You have added a new Borrowing successfully',
        type: 'success',
        addclass: "stack-topleft",
        animate: {
          animate: true,
          in_class: 'rotateInDownLeft',
          out_class: 'rotateOutUpRight'
        },delay:2000
      });
      this.router.navigate(['/BorrowingDetail', this.borrowingAddId]);

    }, 500);

  }

  deleteBorrowing() {
    this.http.get(this.serverLink+'/deleteBorrowingsREST/' + this.nearlyDeleteId).map((res: Response) => res.json())
      .subscribe(data => {
        this.data = data;
        // console.log(this.data);
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
          },delay:2000
        });
    },err=>
    {
      $("#modalDelete").modal("hide");
      var deleteSuccessful = new PNotify({
        title: 'Error',
        text: 'Cannot delete this borrowing information',
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

  initializeEditForm(id: string) {
    this.getBorrowingById(id);
    this.http.get(this.serverLink+'/getAllBorrowersREST')
      .map((res: Response) => res.json())
      .subscribe(data => {
        this.borrowerData = data;
      });

    setTimeout(() => {
      this.borrowingIdEdit = this.dataById.borrowingid;
      this.borrowingDateEdit = this.dataById.bordate;
      this.createdDateEdit = this.dataById.createddate;

      var currentBorrowerId = this.dataById.borrowers.borrowerid;

      $('#BorrowerBarcodeEdit').empty().trigger("change");
      $.each(this.borrowerData, function (index, value) {
        var newOption
        if (currentBorrowerId == value.borrowerid) {
          newOption = new Option(value.barcode, value.borrowerid, false, true);
        }
        else {
          newOption = new Option(value.barcode, value.borrowerid, false, false);
        }
        $('#BorrowerBarcodeEdit').append(newOption).trigger('change');
      });

      this.borrowerIdEditHint = $("#BorrowerBarcodeEdit").val();

      this.http.get(this.serverLink+'/getBorrowersREST/' + this.borrowerIdEditHint).map((res: Response) => res.json())
        .subscribe(borrowerByIdHintDataEdit => {
          this.borrowerByIdHintDataEdit = borrowerByIdHintDataEdit;
          // console.log(this.borrowerByIdHintDataEdit);
          this.borrowerEditHint = "<strong class='text-info'>Borrower Name:</strong> " + this.borrowerByIdHintDataEdit.firstname +
            " " + this.borrowerByIdHintDataEdit.lastname;
          $('#borrowerEditHint').html(this.borrowerEditHint);
        });

      let http2 = this.http;
      let serverLink2 = this.serverLink;
      let that = this;
      $('#BorrowerBarcodeEdit').on('change', function (e) {
        this.borrowerIdEditHint = $("#BorrowerBarcodeEdit").val();
        http2.get(serverLink2+'/getBorrowersREST/' + that.borrowerIdEditHint).map((res: Response) => res.json())
          .subscribe(borrowerByIdHintDataEdit => {
            that.borrowerByIdHintDataEdit = borrowerByIdHintDataEdit;
            // console.log(that.borrowerByIdHintDataEdit);
            that.borrowerEditHint = "<strong class='text-info'>Borrower Name:</strong> " + that.borrowerByIdHintDataEdit.firstname +
              " " + that.borrowerByIdHintDataEdit.lastname;
            $('#borrowerEditHint').html(this.borrowerEditHint);
          });
      });
    }, 700);
  }

  updateBorrowing() {
    var formData = new FormData();
    formData.append('borrowerId', $('#BorrowerBarcodeEdit').val());
    formData.append('borrowingId', this.borrowingIdEdit);
    formData.append('librariansByCreatedby', this.borrowingCreatedBy);
    formData.append('librariansByUpdatedby', this.currentLibId);
    formData.append('borDate', $('#borDateEdit').val());
    formData.append('createdDate', this.createdDateEdit);
    formData.append('updatedDate', this.getSystemDate());
    // let borrowing=
    // {
    //   borrowerId: 22,
    //   librariansByCreatedby: 1,
    //   librariansByUpdatedby: 1,
    //   borDate: "2017-12-31",
    //   createdDate: "2017-12-31",
    //   updatedDate: "2017-12-31"
    // };

    this.http.post(this.serverLink+'/updateBorrowingsREST', formData).subscribe(
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
      var updateSuccessful = new PNotify({
        title: 'Update Successfully !!!',
        text: 'You have updated the Borrowing successfully',
        type: 'info',
        addclass: "stack-topleft",
        animate: {
          animate: true,
          in_class: 'rotateInDownLeft',
          out_class: 'rotateOutUpRight'
        },delay:2000
      });
    }, 700);
  }

  nearlyDelete(id: string) {
    this.nearlyDeleteId = id;
  }

  getSystemDate() {
    var d = new Date();

    var month = d.getMonth() + 1;
    var day = d.getDate();

    var output = d.getFullYear() + '-' +
      (month < 10 ? '0' : '') + month + '-' +
      (day < 10 ? '0' : '') + day;
    return output;
  }

  addDateTime(borDate: string, maxdayissue: number) {
    var dateTemp = new Date(borDate);
    dateTemp.setDate(dateTemp.getDate() + maxdayissue);
    return dateTemp.getFullYear() + '-' +
      ((dateTemp.getMonth() + 1) < 10 ? '0' : '') + (dateTemp.getMonth() + 1) + '-' +
      (dateTemp.getDate() < 10 ? '0' : '') + dateTemp.getDate();
  }


  //Sign out
  signOut() {
    this.cookieService.delete('loginLibId');
    this.cookieService.delete('loginLibFirstName');
    this.cookieService.delete('loginLibLastName');
    this.cookieService.delete('loginLibRole');
    this.router.navigate(['./Login']);
  }

}