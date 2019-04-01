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
declare var PNotify :any;
@Component({
  selector: 'app-librarian',
  templateUrl: './librarian.component.html',
  styleUrls: ['./librarian.component.css'],
  providers: [CookieService]
})

export class LibrarianComponent implements OnInit {

  //Server link
  serverLink: string = 'http://localhost:8080/SpringMVCHibernateCRUDExample';

  //Variables for current Librarian
  currentLibLink:string = this.serverLink+'/image/librarian/'
  currentLibId:string;
  currentLibFName:string;
  currentLibLName:string;
  currentLibRole:string;
  libData:any;


  backendLink: string = this.serverLink+'/image/librarian/';
  data: any = null;
  dataEdit: any = [];
  imageFile:any;

  librariansByUpdatedby:string = "";
  librariansByCreatedby:string = "";
  gender:boolean;
  email:string = "";
  address:string= "";
  avatar:string = "";
  username:string= "";
  password:string= "";
  passwordRetype:string= "";
  status:boolean;
  firstname:string= "";
  lastname:string= "";
  createddate:string= "";
  updateddate:string= "";
  phonenumber:string= "";
  libid:string= "";
  libRole:string= "";

  librariansByUpdatedbyEdit:string= "";
  librariansByCreatedbyEdit:string= "";
  genderEdit:boolean;
  emailEdit:string= "";
  addressEdit:string= "";
  avatarEdit:string= "";
  usernameEdit:string= "";
  passwordEdit:string= "";
  passwordEditRetype:string= "";
  statusEdit:boolean;
  firstnameEdit:string= "";
  lastnameEdit:string= "";
  createddateEdit:string= "";
  updateddateEdit:string= "";
  phonenumberEdit:string= "";
  libidEdit:string= "";
  libRoleEdit:string= "";

  nearlyDeleteId:string;

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
}, 3000);
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
    $('input[name="daterange"]').daterangepicker({
      singleDatePicker: true,
      showDropdowns: true
      });
    $('#roleSelectionAdd').select2();
    $('#roleSelectionEdit').select2();
  }

  checkAddForm()
  {
      if (this.firstname == "")
      {
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
        return ;
      }
      else if (this.lastname == "")
      {
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
        return ;
      }
      else if (this.email == "")
      {
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
        return ;
      }
      else if (!this.validateEmail(this.email))
      {
        var deleteSuccessful = new PNotify({
          title: 'Error',
          text: 'Email format is invalid. Please check it again',
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
      else if (this.phonenumber != "")
      {
          if (!this.validatePhone(this.phonenumber))
          {
            var deleteSuccessful = new PNotify({
              title: 'Error',
              text: 'Phone number is invalid. Please check it again',
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
      }
      if (this.username == "")
      {
        var deleteSuccessful = new PNotify({
          title: 'Error',
          text: 'User name cannot be empty',
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
      else if (this.password == "")
      {
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
        return ;
      }
      else if (this.password.length < 6)
      {
        var deleteSuccessful = new PNotify({
          title: 'Error',
          text: 'Password has to be more than 6 characters',
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
      else if (this.password != this.passwordRetype)
      {
        var deleteSuccessful = new PNotify({
          title: 'Error',
          text: 'Password and confirm password has to be the same',
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
        for (let value of this.data)
        {
          if (this.username == value.username)
          {
            var deleteSuccessful = new PNotify({
              title: 'Error',
              text: 'The username has already exists in the database already',
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
        }
        this.addLibrarian();
      }
  }

  checkEditForm()
  {
      if (this.firstnameEdit == "")
      {
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
        return ;
      }
      else if (this.lastnameEdit == "")
      {
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
        return ;
      }
      else if (this.emailEdit == "")
      {
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
        return ;
      }
      else if (!this.validateEmail(this.emailEdit))
      {
        var deleteSuccessful = new PNotify({
          title: 'Error',
          text: 'Email format is invalid. Please check it again',
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
      else if (this.phonenumberEdit != "")
      {
          if (!this.validatePhone(this.phonenumberEdit))
          {
            var deleteSuccessful = new PNotify({
              title: 'Error',
              text: 'Phone number is invalid. Please check it again',
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
      }
      if (this.usernameEdit == "")
      {
        var deleteSuccessful = new PNotify({
          title: 'Error',
          text: 'User name cannot be empty',
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
      else if (this.passwordEdit == "")
      {
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
        return ;
      }
      else if (this.passwordEdit.length < 6)
      {
        var deleteSuccessful = new PNotify({
          title: 'Error',
          text: 'Password has to be more than 6 characters',
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
      else if (this.passwordEdit != this.passwordEditRetype)
      {
        var deleteSuccessful = new PNotify({
          title: 'Error',
          text: 'Password and confirm password has to be the same',
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

        this.updateLibrarian();
      }
  }


  getData()
  {
    return this.http.get(this.serverLink+'/getAllLibrariansREST')
    .map((res: Response) => res.json())
    .subscribe(data => {
      this.data = data;
      // console.log(this.data);
    });
  }

  getLibrarianById(id:string)
  {
    this.http.get(this.serverLink+'/getLibrariansByIDREST/' + id).map((res: Response) => res.json())
    .subscribe(dataEdit => {
      this.dataEdit = dataEdit;
      // console.log(this.dataEdit);
     
      this.createdById= dataEdit.librariansByCreatedby;
      this.updatedById=dataEdit.librariansByUpdatedby;
      // alert(this.createdById);
     
    });
    setTimeout(() => {
      // alert(this.createdById);
      this.http.get(this.serverLink+'/getLibrariansByIDREST/' + this.createdById).map((res: Response) => res.json())
      .subscribe(libDataCreated => {
        this.libDataCreated = libDataCreated;
        // console.log(this.libDataCreated);
      });
    },800);

    setTimeout(() => {
      // alert(this.createdById);
      this.http.get(this.serverLink+'/getLibrariansByIDREST/' + this.updatedById).map((res: Response) => res.json())
      .subscribe(libDataUpdated => {
        this.libDataUpdated = libDataUpdated;
        // console.log(this.libDataUpdated);
      });
    },600);
    

    setTimeout(() => {
      this.librariansByUpdatedbyEdit = this.dataEdit.librariansByUpdatedby;
      this.librariansByCreatedbyEdit = this.dataEdit.librariansByCreatedby;
      this.genderEdit = this.dataEdit.gender;
      this.emailEdit = this.dataEdit.email;
      this.addressEdit = this.dataEdit.address;
      this.usernameEdit = this.dataEdit.username;
      this.passwordEdit = this.dataEdit.password;
      this.passwordEditRetype = this.passwordEdit;
      this.statusEdit = this.dataEdit.status;
      this.firstnameEdit = this.dataEdit.firstname;
      this.lastnameEdit = this.dataEdit.lastname;
      this.createddateEdit = this.dataEdit.createddate;
      this.updateddateEdit = this.dataEdit.updateddate;
      this.phonenumberEdit = this.dataEdit.phonenumber;
      this.libidEdit = this.dataEdit.libid;
      this.avatarEdit = this.dataEdit.avatar;
      this.createdByName= this.libDataCreated.firstname+" "+this.libDataCreated.lastname;
      this.updatedByName= this.libDataUpdated.firstname+" "+this.libDataUpdated.lastname;
    },1000);
  }

  addLibrarian()
  {
    var currentGender;
    if (this.imageFile == null)
    {
      let formData = new FormData();
      this.imageFile = formData;
    }

    if($('input[id=maleRadioAdd]:checked').val()=='male')
    {
      currentGender = true;
    }
    else{
      currentGender = false;
    }


    var currentStatus;
    if($('input[id=activeRadioAdd]:checked').val()=='active')
    {
      currentStatus = true;
    }
    else{
      currentStatus = false;
    }

    this.imageFile.append('userName',this.username);

    this.imageFile.append('password',this.password);
    this.imageFile.append('librariansByCreatedby',this.currentLibId);
    this.imageFile.append('librariansByUpdatedby',this.currentLibId);
    this.imageFile.append('firstName',this.firstname);
    this.imageFile.append('lastName',this.lastname);
    this.imageFile.append('gender',currentGender);
    this.imageFile.append('email',this.email);
    this.imageFile.append('phoneNumber',this.phonenumber);
    this.imageFile.append('address',this.address);

    this.imageFile.append('status',currentStatus);
    this.imageFile.append('createdDate',this.getSystemDate());
    this.imageFile.append('updatedDate',this.getSystemDate());
    this.imageFile.append('libRole',$("#roleSelectionAdd").val());
    
    this.http.post(this.serverLink+'/addLibrariansREST',this.imageFile).subscribe(
      res => {
        // console.log(res);
      },
      err => {
        console.log(err);
      }
    );
    var addSuccessful= new PNotify({
      title: 'Add Successfully !!!',
      text: 'You have added the librarian <strong>'+this.firstname+' '+this.lastname+'</strong> successfully',
      type: 'success',
      addclass: "stack-topleft",
      animate: {
          animate: true,
          in_class: 'rotateInDownLeft',
          out_class: 'rotateOutUpRight'
      },delay:2000
    });

    setTimeout(() => {
      this.getData();
      this.librariansByUpdatedby = "";
      this.librariansByCreatedby= "";
      this.email= "";
      this.passwordRetype = "";
      this.address= "";
      this.avatar= "";
      this.username= "";
      this.password= "";
      this.firstname= "";
      this.lastname= "";
      this.createddate= "";
      this.updateddate= "";
      this.phonenumber= "";
      this.libid= "";
      this.libRole= "";
      $("#modalAdd").modal("hide");
      this.imageFile = null;
    }, 500);
  }

  initializeUploadForm(id:string)
  {
    this.getLibrarianById(id);
    setTimeout(() => {
        this.setGenderCheck(this.genderEdit);
        this.setStatusCheck(this.statusEdit);
    }, 1000);
  }

  updateLibrarian()
  {
    var currentGender;
    if (this.imageFile == null)
    {
      let formData = new FormData();
      this.imageFile = formData;
    }

    if($('input[id=maleRadioEdit]:checked').val()=='male')
    {
      currentGender = true;
    }
    else{
      currentGender = false;
    }

    var currentStatus;
    if($('input[id=activeRadioEdit]:checked').val()=='active')
    {
      currentStatus = true;
    }
    else{
      currentStatus = false;
    }
    this.imageFile.append('libId',this.libidEdit);
    this.imageFile.append('userName',this.usernameEdit);
    this.imageFile.append('password',this.passwordEdit);
    this.imageFile.append('librariansByCreatedby',this.librariansByCreatedbyEdit);
    this.imageFile.append('librariansByUpdatedby',this.currentLibId);
    this.imageFile.append('firstName',this.firstnameEdit);
    this.imageFile.append('lastName',this.lastnameEdit);
    this.imageFile.append('gender',currentGender);
    this.imageFile.append('email',this.emailEdit);
    this.imageFile.append('phoneNumber',this.phonenumberEdit);
    this.imageFile.append('address',this.addressEdit);
    this.imageFile.append('status',currentStatus);
    this.imageFile.append('createdDate',this.createddateEdit);
    this.imageFile.append('updatedDate',this.getSystemDate());
    this.imageFile.append('libRole',$("#roleSelectionEdit").val());
    
    this.http.post(this.serverLink+'/updateLibrariansREST',this.imageFile).subscribe(
      res => {
        // console.log(res);
      },
      err => {
        console.log(err);
      }
    );

    var updateSuccessful= new PNotify({
      title: 'Update Successfully !!!',
      text: 'You have updated the book <strong>'+this.firstnameEdit+' '+this.lastnameEdit+'</strong> successfully',
      type: 'info',
      addclass: "stack-topleft",
      animate: {
          animate: true,
          in_class: 'rotateInDownLeft',
          out_class: 'rotateOutUpRight'
      },delay:2000
    });

    setTimeout(() => {
      this.getData();
      this.librariansByUpdatedby = "";
      this.librariansByCreatedby= "";
      this.email= "";
      this.address= "";
      this.avatar= "";
      this.username= "";
      this.password= "";
      this.firstname= "";
      this.lastname= "";
      this.createddate= "";
      this.updateddate= "";
      this.phonenumber= "";
      this.libid= "";
      this.libRole= "";
      $("#modalEdit").modal("hide");
      this.imageFile = null;
    }, 500);
  }

  nearlyDelete(id:string)
  {
    this.nearlyDeleteId = id;
  }

  deleteLibrarian(){
    this.http.get(this.serverLink+'/deleteLibrariansREST/'+this.nearlyDeleteId).map((res: Response) => res.json())
    .subscribe(data => {
           this.data = data;
          //  console.log(this.data);
   });
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
  }

  uploadFile(event)
  {
    let elem = event.target;
    if (elem.files.length > 0)
    {
      // console.log(elem.files[0]);
      // let formData = new FormData();
      // formData.append('file',elem.files[0]);
      let formData = new FormData();
      formData.append('avatar',elem.files[0]);
      this.imageFile = formData;
    }
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


  setGenderCheck(gender: boolean) {
    if (gender == true) {
      $('#maleRadioEdit').iCheck('check');
    }
    else {
      $('#femaleRadioEdit').iCheck('check');
    }
  }

  setStatusCheck(status: boolean) {
    if (status == true) {
      $('#activeRadioEdit').iCheck('check');
    }
    else {
      $('#inactiveRadioEdit').iCheck('check');
    }
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

    validateEmail(Email) {
      var pattern = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
  
      return $.trim(Email).match(pattern) ? true : false;
    }
  
    validatePhone(Phone) {
      var pattern = /([0-9]{10})|(\([0-9]{3}\)\s+[0-9]{3}\-[0-9]{4})/;
      return $.trim(Phone).match(pattern) ? true : false;
    }

}
