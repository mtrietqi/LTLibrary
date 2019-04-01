import { Component, OnInit } from '@angular/core';
import { Http, Response, Headers } from '@angular/http';
import { NgModel } from '@angular/forms';
import 'rxjs/add/operator/map'
import { LIFECYCLE_HOOKS_VALUES } from '@angular/compiler/src/lifecycle_reflector';
import { DomSanitizer } from '@angular/platform-browser';
import { CookieService } from 'ngx-cookie-service';
import { Router } from '@angular/router';
import { error } from 'selenium-webdriver';

declare var $: any;
declare var angular: any;
declare var PNotify: any;

@Component({
  selector: 'app-borrower',
  templateUrl: './borrower.component.html',
  styleUrls: ['./borrower.component.css'],
  providers: [CookieService]

})
export class BorrowerComponent implements OnInit {
  //Server link
  serverLink: string = 'http://localhost:8080/SpringMVCHibernateCRUDExample';

  //Variables for current Librarian
  currentLibLink: string = this.serverLink + '/image/librarian/'
  currentLibId: string;
  currentLibFName: string;
  currentLibLName: string;
  currentLibRole: string;
  libData: any;

  backendLink: string = this.serverLink + '/image/borrowerImages/'
  data: any = null;
  dataEdit: any = [];
  currentBorrowerTypeName: string;

  librariansByCreatedby: string;
  librariansByUpdatedby: string;
  schoolId: string;
  firstName: string;
  lastName: string;
  barCode: string = "";
  gender: boolean;
  email: string;
  phoneNumber: string = "";
  address: string = "";
  avatar: string;
  status: boolean;
  createdDate: string = "";
  updatedDate: string= "";
  borrowerTypeData: any;
  tempIdData: string= "";
  imageFile: any = null;

  borrowerTypeEdit: string= "";
  librariansByCreatedbyEdit: string= "";
  librariansByUpdatedbyEdit: string= "";
  schoolIdEdit: string= "";
  firstNameEdit: string= "";
  lastNameEdit: string= "";
  barCodeEdit: string = "";
  genderEdit: boolean;
  emailEdit: string= "";
  phoneNumberEdit: string = "";
  addressEdit: string = "";
  avatarEdit: string= "";
  statusEdit: boolean;
  createdDateEdit: string= "";
  updatedDateEdit: string= "";

  nearlyDeleteId: string= "";

  createdById;
  updatedById;
  libDataCreated;
  libDataUpdated;

  createdByName;
  updatedByName;
  constructor(private http: Http, private cookieService: CookieService, private router: Router) {
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
    }, 300);
    $('#btnAdd').click(function () {
      $('#modalAdd').modal("show");
    });
    $('#editClick').click(function () {
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
    $('#BorrowerTypeSelectionEdit').select2();
    $('#BorrowerTypeSelectionEditAdd').select2();
  }

  //-----------------------------FORM CHECK METHODS-----------------------------------//
  checkAddForm() {
    if (this.schoolId == null) {
      var deleteSuccessful = new PNotify({
        title: 'Error',
        text: 'School ID cannot be empty',
        type: 'error',
        addclass: "stack-topleft",
        animate: {
          animate: true,
          in_class: 'zoomInLeft',
          out_class: 'zoomOutRight'
        },
        delay: 2000
      });
      return;
    }
    else if (this.firstName == null) {
      var deleteSuccessful = new PNotify({
        title: 'Error',
        text: 'First name cannot be empty',
        type: 'error',
        addclass: "stack-topleft",
        animate: {
          animate: true,
          in_class: 'zoomInLeft',
          out_class: 'zoomOutRight'
        },
        delay: 2000
      });
      return;
    }
    else if (this.lastName == null) {
      var deleteSuccessful = new PNotify({
        title: 'Error',
        text: 'Last name cannot be empty',
        type: 'error',
        addclass: "stack-topleft",
        animate: {
          animate: true,
          in_class: 'zoomInLeft',
          out_class: 'zoomOutRight'
        },
        delay: 2000
      });
      return;
    }
    else if (this.barCode == "") {
      var deleteSuccessful = new PNotify({
        title: 'Error',
        text: 'Bar code cannot be empty',
        type: 'error',
        addclass: "stack-topleft",
        animate: {
          animate: true,
          in_class: 'zoomInLeft',
          out_class: 'zoomOutRight'
        },
        delay: 2000
      });
      return;
    }
    else if (this.email == null) {
      var deleteSuccessful = new PNotify({
        title: 'Error',
        text: 'Email cannot be empty',
        type: 'error',
        addclass: "stack-topleft",
        animate: {
          animate: true,
          in_class: 'zoomInLeft',
          out_class: 'zoomOutRight'
        },
        delay: 2000
      });
      return;
    }
    else if (!this.validateEmail(this.email)) {
      var deleteSuccessful = new PNotify({
        title: 'Error',
        text: 'The email format is wrong, please check it again',
        type: 'error',
        addclass: "stack-topleft",
        animate: {
          animate: true,
          in_class: 'zoomInLeft',
          out_class: 'zoomOutRight'
        },
        delay: 2000
      });
      return;
    }
    else if (this.phoneNumber != "") {
      if (!this.validatePhone(this.phoneNumber)) {
        var deleteSuccessful = new PNotify({
          title: 'Error',
          text: 'The phone number format is wrong, please check it again.',
          type: 'error',
          addclass: "stack-topleft",
          animate: {
            animate: true,
            in_class: 'zoomInLeft',
            out_class: 'zoomOutRight'
          },
          delay: 2000
        });
        return;
      }
      else {
        this.addBorrower();
      }
    }
    else
    {
      this.addBorrower();
    }
  }

  checkEditForm() {
    if (this.schoolIdEdit == null) {
      var deleteSuccessful = new PNotify({
        title: 'Error',
        text: 'School ID cannot be empty',
        type: 'error',
        addclass: "stack-topleft",
        animate: {
          animate: true,
          in_class: 'zoomInLeft',
          out_class: 'zoomOutRight'
        },
        delay: 2000
      });
      return;
    }
    else if (this.firstNameEdit == null) {
      var deleteSuccessful = new PNotify({
        title: 'Error',
        text: 'First name cannot be empty',
        type: 'error',
        addclass: "stack-topleft",
        animate: {
          animate: true,
          in_class: 'zoomInLeft',
          out_class: 'zoomOutRight'
        },
        delay: 2000
      });
      return;
    }
    else if (this.lastNameEdit == null) {
      var deleteSuccessful = new PNotify({
        title: 'Error',
        text: 'Last name cannot be empty',
        type: 'error',
        addclass: "stack-topleft",
        animate: {
          animate: true,
          in_class: 'zoomInLeft',
          out_class: 'zoomOutRight'
        },
        delay: 2000
      });
      return;
    }
    else if (this.barCodeEdit == "" || this.barCodeEdit == null) {
      var deleteSuccessful = new PNotify({
        title: 'Error',
        text: 'Bar code cannot be empty',
        type: 'error',
        addclass: "stack-topleft",
        animate: {
          animate: true,
          in_class: 'zoomInLeft',
          out_class: 'zoomOutRight'
        },
        delay: 2000
      });
      return;
    }
    else if (this.emailEdit == null) {
      var deleteSuccessful = new PNotify({
        title: 'Error',
        text: 'Email cannot be empty',
        type: 'error',
        addclass: "stack-topleft",
        animate: {
          animate: true,
          in_class: 'zoomInLeft',
          out_class: 'zoomOutRight'
        },
        delay: 2000
      });
      return;
    }
    else if (!this.validateEmail(this.emailEdit)) {
      var deleteSuccessful = new PNotify({
        title: 'Error',
        text: 'The email format is wrong, please check it again',
        type: 'error',
        addclass: "stack-topleft",
        animate: {
          animate: true,
          in_class: 'zoomInLeft',
          out_class: 'zoomOutRight'
        },
        delay: 2000
      });
      return;
    }
    else if (this.phoneNumberEdit != "") {
      if (!this.validatePhone(this.phoneNumberEdit)) {
        var deleteSuccessful = new PNotify({
          title: 'Error',
          text: 'The phone number format is wrong, please check it again.',
          type: 'error',
          addclass: "stack-topleft",
          animate: {
            animate: true,
            in_class: 'zoomInLeft',
            out_class: 'zoomOutRight'
          },
          delay: 2000
        });
        return;
      }
      else {
        this.updateBorrower();
      }
    }
    else {
      this.updateBorrower();
    }
  }
  //-----------------------------END FORM CHECK METHODS-----------------------------------//
  //-------------------------------MAIN METHODS-------------------------------//
  private getData() {
    return this.http.get(this.serverLink + '/getAllBorrowersREST')
      .map((res: Response) => res.json())
      .subscribe(data => {
        this.data = data;
        // console.log(this.data);
      });
  }

  private getGender(para_gender: boolean) {
    if (para_gender == false)
      return 'Male';
    else
      return 'Female';
  }

  getBorrowerById(id: string) {
    // alert(id);
    this.http.get(this.serverLink + '/getBorrowersREST/' + id).map((res: Response) => res.json())
      .subscribe(dataEdit => {
        this.dataEdit = dataEdit;
        // console.log(this.dataEdit);
      });
    this.http.get(this.serverLink + '/getAllBorrowerTypeREST')
      .map((res: Response) => res.json())
      .subscribe(data => {
        this.borrowerTypeData = data;
        // console.log(this.borrowerTypeData);
      });
    //Clear all data of selection box

    setTimeout(() => {
      this.schoolIdEdit = this.dataEdit.schoolid;
      this.firstNameEdit = this.dataEdit.firstname;
      this.lastNameEdit = this.dataEdit.lastname;
      this.genderEdit = this.dataEdit.gender;
      this.emailEdit = this.dataEdit.email;
      this.phoneNumberEdit = this.dataEdit.phonenumber;
      this.addressEdit = this.dataEdit.address;
      this.genderEdit = this.dataEdit.gender;
      this.statusEdit = this.dataEdit.status;
      this.createdDateEdit = this.dataEdit.createddate;
      this.updatedDateEdit = this.dataEdit.updateddate;
      this.currentBorrowerTypeName = this.dataEdit.borrowertype.typename;
      this.barCodeEdit = this.dataEdit.barcode;
      this.avatar = this.dataEdit.avatar;
      this.setGenderCheck(this.genderEdit);
      this.librariansByCreatedbyEdit = this.dataEdit.librariansByCreatedby;
      this.librariansByUpdatedbyEdit = this.dataEdit.librariansByUpdatedby;
      $('#BorrowerTypeSelectionEdit').empty().trigger("change");
      var currentIdData = this.dataEdit.borrowertype.borrowertypeid;
      //Initialize borrower selection box
      $.each(this.borrowerTypeData, function (index, value) {
        this.tempIdData = value.borrowertypeid;
        if (this.tempIdData == currentIdData) {
          var newOption = new Option(value.typename, value.borrowertypeid, false, true);
        }
        else {
          var newOption = new Option(value.typename, value.borrowertypeid, false, false);
        }
        $('#BorrowerTypeSelectionEdit').append(newOption).trigger('change');
      });
      this.setStatusCheck(this.statusEdit);


      // alert(this.createdById);
      this.http.get(this.serverLink + '/getLibrariansByIDREST/' + this.librariansByCreatedbyEdit).map((res: Response) => res.json())
        .subscribe(libDataCreated => {
          this.libDataCreated = libDataCreated;
          this.createdByName = this.libDataCreated.firstname + " " + this.libDataCreated.lastname;
          // console.log(this.libDataCreated);
        });

      // alert(this.createdById);
      this.http.get(this.serverLink + '/getLibrariansByIDREST/' + this.librariansByUpdatedbyEdit).map((res: Response) => res.json())
        .subscribe(libDataUpdated => {
          this.libDataUpdated = libDataUpdated;
          // console.log(this.libDataUpdated);
          this.updatedByName = this.libDataUpdated.firstname + " " + this.libDataUpdated.lastname;
        });

    }, 500);
  }

  initializeAddForm() {
    //Reset avatarFile (set it to null)
    $("#avatarFile").val('');

    //Get all BorrowerTypes
    this.http.get(this.serverLink + '/getAllBorrowerTypeREST')
      .map((res: Response) => res.json())
      .subscribe(data => {
        this.borrowerTypeData = data;
        // console.log(this.borrowerTypeData);
      });


    $('#maleRadioAdd').iCheck('check');
    $('#activeRadioAdd').iCheck('check');
    setTimeout(() => {
      $('#BorrowerTypeSelectionEditAdd').empty().trigger("change");
      $.each(this.borrowerTypeData, function (index, value) {
        this.tempIdData = value.borrowertypeid;
        var newOption = new Option(value.typename, value.borrowertypeid, false, false);
        $('#BorrowerTypeSelectionEditAdd').append(newOption).trigger('change');
      });

      let http2 = this.http;
      $('#isbnSelectionAdd').on('change', function (e) {
        this.titleIdAdd = $("#isbnSelectionAdd").val();

        http2.get(this.serverLink + '/getTitlesREST/' + this.titleIdAdd).map((res: Response) => res.json())
          .subscribe(titleByIdAddData => {
            this.titleByIdAddData = titleByIdAddData;
            // console.log(this.titleByIdAddData);
            this.bookTitleAddHint = "<strong class='text-info'>Book title:</strong> " + this.titleByIdAddData.booktitle;
            $('#bookTitleHint').html(this.bookTitleAddHint);
          });
      });
      // alert( $('#BorrowerTypeSelectionEditAdd').val());
    }, 300);
  }

  addBorrower() {
    this.addData(this.librariansByCreatedby, this.librariansByUpdatedby, this.email, this.address,
      this.avatar, this.status, this.lastName, this.firstName, this.schoolId, this.barCode, this.phoneNumber, this.createdDate,
      this.updatedDate, "");
      //Reset borrower data
    setTimeout(() => {
      this.getData();
      this.librariansByCreatedby = "";
      this.librariansByUpdatedby = "";
      this.email = "";
      this.address = "",
        this.avatar = "";
      this.lastName = "";
      this.firstName = "";
      this.schoolId = "";
      this.barCode = "";
      this.phoneNumber = "";
      this.createdDate = "";
      this.updatedDate = "";
    }, 200);
  }

  addData(librariansByCreatedby: String, librariansByUpdatedby: String, email: String, address: String,
    avatar: String, status: boolean, lastname: String, firstname: String, schoolid: string, barcode: string, phonenumber: string, createddate: string,
    updateddate: string, borrowertypeid: string) {
    var withImage = true;
    if (this.imageFile == null) {
      let formData = new FormData();
      this.imageFile = formData;
      withImage = false;
    }
    var currentGender;
    if ($('input[id=maleRadioAdd]:checked').val() == 'male') {
      currentGender = false;
    }
    else {
      currentGender = true;
    }

    var currentStatus;
    if ($('input[id=activeRadioAdd]:checked').val() == 'active') {
      currentStatus = true;
    }
    else {
      currentStatus = false;
    }
    var currentBorrowerTypeId = $("#BorrowerTypeSelectionEditAdd").val();

    this.imageFile.append('email', email);
    this.imageFile.append('address', address);
    this.imageFile.append('lastName', lastname);
    this.imageFile.append('firstName', firstname);
    this.imageFile.append('phoneNumber', phonenumber);
    this.imageFile.append('schoolId', schoolid);
    this.imageFile.append('barCode', barcode);
    this.imageFile.append('status', currentStatus);
    this.imageFile.append('gender', currentGender);
    this.imageFile.append('borrowerTypeId', Number(currentBorrowerTypeId));

    this.imageFile.append('createdDate', this.getSystemDate());
    this.imageFile.append('updatedDate', this.getSystemDate());
    this.imageFile.append('librariansByCreatedby', this.currentLibId);
    this.imageFile.append('librariansByUpdatedby', this.currentLibId);

    // console.log(borrower);
    // console.log(this.imageFile.values());
    if (withImage) {
      this.http.post(this.serverLink + '/addBorrowersREST', this.imageFile).subscribe(
        res => {
          // console.log(res);
        },
        err => {
          console.log(err);
        }
      );
    }
    else {
      this.http.post(this.serverLink + '/addBorrowersWithoutImageREST', this.imageFile).subscribe(
        res => {
          // console.log(res);
        },
        err => {
          console.log(err);
        }
      );
    }

    this.imageFile = null;
    $("#modalAdd").modal("hide");
    var addSuccessful = new PNotify({
      title: 'Add Successfully !!!',
      text: 'You have added a new Borrower <strong>'+this.firstName+' '+this.lastName+'</strong> successfully',
      type: 'success',
      addclass: "stack-topleft",
      animate: {
        animate: true,
        in_class: 'rotateInDownLeft',
        out_class: 'rotateOutUpRight'
      }, delay: 2000
    });
  }

  updateBorrower() {
    var currentGender;
    if ($('input[id=maleRadio]:checked').val() == 'male') {
      currentGender = false;
    }
    else {
      currentGender = true;
    }
    var currentStatus;
    if ($('input[id=activeRadio]:checked').val() == 'active') {
      currentStatus = true;
    }
    else {
      currentStatus = false;
    }

    var withImage = true;
    //Initialize imageFile if it is null
    if (this.imageFile == null) {
      // formData.append('file',elem.files[0]);
      let formData = new FormData();
      this.imageFile = formData;
      withImage = false;
    }
    var currentBorrowerTypeId = $("#BorrowerTypeSelectionEdit").val();
    this.imageFile.append('borrowerId', this.dataEdit.borrowerid);
    this.imageFile.append('email', this.emailEdit);
    this.imageFile.append('address', this.addressEdit);
    this.imageFile.append('lastName', this.lastNameEdit);
    this.imageFile.append('firstName', this.firstNameEdit);
    this.imageFile.append('phoneNumber', this.phoneNumberEdit);
    this.imageFile.append('schoolId', this.schoolIdEdit);
    this.imageFile.append('barCode', this.barCodeEdit);
    this.imageFile.append('status', currentStatus);
    this.imageFile.append('gender', currentGender);
    this.imageFile.append('borrowerTypeId', Number(currentBorrowerTypeId));

    //Will change later
    this.imageFile.append('createdDate', this.dataEdit.createddate);
    this.imageFile.append('updatedDate', this.getSystemDate());
    this.imageFile.append('librariansByCreatedby', this.librariansByCreatedbyEdit);
    this.imageFile.append('librariansByUpdatedby', this.currentLibId);

    // console.log(borrower);
    // console.log(this.imageFile.values());
    if (!withImage) {
      this.http.post(this.serverLink + '/updateBorrowersWithoutImageREST', this.imageFile).subscribe(
        res => {
          // console.log(res);
        },
        err => {
          console.log(err);
        }
      );
    }
    else {
      this.http.post(this.serverLink + '/updateBorrowersREST', this.imageFile).subscribe(
        res => {
          // console.log(res);
        },
        err => {
          console.log(err);
        }
      );
    }
    setTimeout(() => {
      this.getData();
    }, 200);
    this.imageFile = null;
    $("#modalEdit").modal("hide");
    var updateSuccessful = new PNotify({
      title: 'Update Successfully !!!',
      text:'You have updated a new Borrower <strong>'+this.firstNameEdit+' '+this.lastNameEdit+'</strong> successfully',
      type: 'info',
      addclass: "stack-topleft",
      animate: {
        animate: true,
        in_class: 'rotateInDownLeft',
        out_class: 'rotateOutUpRight'
      }, delay: 2000
    });
  }


  nearlyDelete(id: string) {
    this.nearlyDeleteId = id;
  }

  deleteBorrower(id: String) {
    this.http.get(this.serverLink + '/deleteBorrowersREST/' + this.nearlyDeleteId).map((res: Response) => res.json())
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

  //---------------------------END MAIN METHODS-------------------------------//

  //Check the gender (used in update)
  setGenderCheck(gender: boolean) {
    if (gender == false) {
      $('#maleRadio').iCheck('check');
    }
    else {
      $('#femaleRadio').iCheck('check');
    }
  }

  //Check the status (used in update)
  setStatusCheck(status: boolean) {
    if (status == true) {
      $('#activeRadio').iCheck('check');
    }
    else {
      $('#inactiveRadio').iCheck('check');
    }
  }

  //Get the status (used in update and add)
  getStatus(status: boolean) {
    if (status == true) {
      return 'Active';
    }
    else {
      return 'Inactive';
    }
  }

  //Upload file, create a imageFile form data
  uploadFile(event) {
    let elem = event.target;
    if (elem.files.length > 0) {
      // console.log(elem.files[0]);
      // let formData = new FormData();
      // formData.append('file',elem.files[0]);
      let formData = new FormData();
      formData.append('file', elem.files[0]);
      this.imageFile = formData;
    }
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

  //Sign out
  signOut() {
    this.cookieService.delete('loginLibId');
    this.cookieService.delete('loginLibFirstName');
    this.cookieService.delete('loginLibLastName');
    this.cookieService.delete('loginLibRole');
    this.router.navigate(['./Login']);
  }

  //Check email
  validateEmail(Email) {
    var pattern = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;

    return $.trim(Email).match(pattern) ? true : false;
  }

  //Check phone
  validatePhone(Phone) {
    var pattern = /([0-9]{10})|(\([0-9]{3}\)\s+[0-9]{3}\-[0-9]{4})/;
    return $.trim(Phone).match(pattern) ? true : false;
  }

}

//chrome.exe --user-data-dir="C:/Chrome dev session" --disable-web-security
