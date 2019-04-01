import { Component, OnInit } from '@angular/core';
import { Http, Response, Headers } from '@angular/http';
import { NgModel } from '@angular/forms';
import 'rxjs/add/operator/map';
import { CookieService } from 'ngx-cookie-service';
import { Router } from '@angular/router';

declare var $: any;
declare var PNotify: any;
@Component({
  selector: 'app-author',
  templateUrl: './author.component.html',
  styleUrls: ['./author.component.css'],
  providers: [CookieService]
})
export class AuthorComponent implements OnInit {
  //Server link
  serverLink: string = 'http://localhost:8080/SpringMVCHibernateCRUDExample';
  //Variables for current Librarian
  currentLibLink: string = this.serverLink+'/image/librarian/'
  currentLibId: string;
  currentLibFName: string;
  currentLibLName: string;
  currentLibRole: string;

  data: any = null;
  dataEdit: any = [];
  firstname: string = "";
  lastname: string = "";
  gender: string = "";
  country: string = "";
  titleauthorses: string = "";

  firstNameEdit: string = "";
  lastNameEdit: string = "";
  genderEdit: string = "";
  countryEdit: string = "";
  nearlyDeleteId;
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
    }, 1000);

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
    $('#statusSelectionAdd').select2();
    $('#statusSelectionEdit').select2();
    $('#BorrowerTypeSelectionAdd').select2();
    $('#BorrowerTypeSelectionEdit').select2();

  }

  //Check add form
  checkAddForm()
  {
    this.firstname, this.lastname, this.gender, this.country
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
    else
    {
      this.addAuthor();
    }
  }

  //Edit form validation
  checkEditForm()
  {
    if (this.firstNameEdit == "")
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
    else if (this.lastNameEdit == "")
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
    else
    {
      this.updateAuthor();
    }
  }

  //Get author data
  private getData() {
    return this.http.get(this.serverLink+'/getAllAuthorsREST')
      .map((res: Response) => res.json())
      .subscribe(data => {
        this.data = data;
        // console.log(this.data);
      });
  }

  //Add author
  private addData(firstname: string, lastname: string, gender: string, country: string) {
    let author =
      {
        gender: gender,
        country: country,
        titleauthorses: null,
        firstname: firstname,
        lastname: lastname
      };

    this.http.post(this.serverLink+'/addAuthorsREST', author).subscribe(
      res => {
        // console.log(res);
      },
      err => {
        console.log("Error occured");
      }
    );
    var addSuccessful =new PNotify({
      title: 'Add Successfully !!!',
      text: 'You have added Author ' + "<strong>" + firstname + " " + lastname + "</strong>" + ' successfully',
      type: 'success',
      addclass: "stack-topleft",
      animate: {
        animate: true,
        in_class: 'rotateInDownLeft',
        out_class: 'rotateOutUpRight'
      },
      delay: 2000
    });
  }

  //Config to add author
  addAuthor() {
    // alert($('input[name=iCheck]:checked').val());
    if ($('input[name=iCheck]:checked').val() == 'male') {
      this.gender = 'false';
    }
    else {
      this.gender = 'true';
    }
    this.addData(this.firstname, this.lastname, this.gender, this.country);
    setTimeout(() => {
      this.getData();
      this.firstname = "";
      this.lastname = "";
      this.gender = "";
      this.country = "";
      $("#modalAdd").modal("hide");
    }, 100);

  }

  //Nearly delete an item
  nearlyDelete(id: string) {
    this.nearlyDeleteId = id;
  }

  //Delete author
  deleteAuthor() {
    var success = false;
    this.http.get(this.serverLink+'/deleteAuthorsREST/' + this.nearlyDeleteId).map((res: Response) => res.json())
      .subscribe(data => {
        this.data = data;
        // console.log(this.data);
        $("#modalDelete").modal("hide");
        var deleteSuccessful = new PNotify({
          title: 'Delete Successfully !!!',
          text: 'You have deleted author successfully',
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
      },
      err => {
        $("#modalDelete").modal("hide");
        var deleteSuccessful = new PNotify({
          title: 'Error',
          text: 'Cannot delete this author',
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

  //Get author by id
  getAuthorById(id: string) {
    // alert(id);
    this.http.get(this.serverLink+'/getAuthorsByIDREST/' + id).map((res: Response) => res.json())
      .subscribe(dataEdit => {
        this.dataEdit = dataEdit;
      });

    setTimeout(() => {
      this.firstNameEdit = this.dataEdit.firstname;
      this.lastNameEdit = this.dataEdit.lastname;
      this.countryEdit = this.dataEdit.country;
      this.getGender();
    }, 100);
  }

  //Set gender check box
  getGender() {
    setTimeout(() => {
      if (this.dataEdit.gender === false) {
        $("#1male").iCheck('check');
      } else {
        $("#2female").iCheck('check');
      }
    }, 100);
  }

  //Update author
  updateData(id: string, firstname: string, lastname: string, gender: string, country: string) {
    let authorEdit =
      {
        auid: id,
        gender: gender,
        country: country,
        titleauthorses: null,
        firstname: firstname,
        lastname: lastname
      };
    this.http.post(this.serverLink+'/updateAuthorsREST', authorEdit).subscribe(
      res => {
        // console.log(res);
        var updateSuccessful = new PNotify({
          title: 'Update Successfully !!!',
          text: 'You have updated Author <strong>'+firstname+" "+lastname+" </strong> succesfully",
          type: 'info',
          addclass: "stack-topleft",
          animate: {
            animate: true,
            in_class: 'rotateInDownLeft',
            out_class: 'rotateOutUpRight'
          },
          delay: 2000
        });
      },
      err => {
        console.log("Error occured");
      }
    );

  }

  //Prepare to update author
  updateAuthor() {
    // alert($('input[name=iCheckEdit]:checked').val());
    if ($('input[name=iCheckEdit]:checked').val() == 'male') {
      this.genderEdit = 'false';
    }
    else {
      this.genderEdit = 'true';
    }
    this.updateData(this.dataEdit.auid, this.firstNameEdit, this.lastNameEdit, this.genderEdit, this.countryEdit);
    setTimeout(() => {
      this.getData();
    }, 1000);
    $('#modalEdit').modal("hide");
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


