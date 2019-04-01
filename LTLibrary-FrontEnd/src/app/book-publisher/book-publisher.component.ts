import { Component, OnInit } from '@angular/core';
import { Http, Response, Headers } from '@angular/http';
import { NgModel } from '@angular/forms';
import 'rxjs/add/operator/map';
import { CookieService } from 'ngx-cookie-service';
import { Router } from '@angular/router';


declare var $: any;
declare var angular: any;
declare var PNotify: any;
@Component({
  selector: 'app-book-publisher',
  templateUrl: './book-publisher.component.html',
  styleUrls: ['./book-publisher.component.css'],
  providers: [CookieService]
})
export class BookPublisherComponent implements OnInit {
  //Server link
  serverLink: string = 'http://localhost:8080/SpringMVCHibernateCRUDExample';

  //Variables for current Librarian
  currentLibLink: string = this.serverLink + '/image/librarian/'
  currentLibId: string;
  currentLibFName: string;
  currentLibLName: string;
  currentLibRole: string;
  libData: any;


  data: any = null;
  dataEdit: any = [];

  publisherId: string;
  publisherName: string ="";
  publisherNameEdit: string ="";
  nearlyDeleteId: string = "";

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
      $('#tbl').DataTable();
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
    }, 100);
  }

  private getData() {
    return this.http.get(this.serverLink + '/getAllPublishersREST')
      .map((res: Response) => res.json())
      .subscribe(data => {
        this.data = data;
        // console.log(this.data);
      });
  }

  addData() {
    let publisher =
      {
        titleses: null,
        pubname: this.publisherName
      };
    this.http.post(this.serverLink + '/addPublishersREST', publisher).subscribe(
      res => {
        // console.log(res);
        var addSuccessful = new PNotify({
          title: 'Add Successfully !!!',
          text: 'You have added a Publisher <strong>'+this.publisherName+'</strong> successfully',
          type: 'success',
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
        console.log(err);
      }
    );
    setTimeout(() => {
      this.getData();
      this.publisherName = "";
    }, 200);
    $("#modalAdd").modal("hide");
  }

  getPublisherById(id: String) {
    this.http.get(this.serverLink + '/getPublishersByIdREST/' + id).map((res: Response) => res.json())
      .subscribe(dataEdit => {
        this.dataEdit = dataEdit;
        // console.log(this.dataEdit);
      });

    setTimeout(() => {
      this.publisherNameEdit = this.dataEdit.pubname;
    }, 200);
  }

  updatePublisher() {
    let publisher =
      {
        titleses: null,
        pubid: this.dataEdit.pubid,
        pubname: this.publisherNameEdit
      };
    this.http.post(this.serverLink + '/updatePublishersREST', publisher).subscribe(
      res => {
        // console.log(res);
        //PNotify announce
        var updateSuccessful = new PNotify({
          title: 'Update Successfully !!!',
          text: 'You have updated <strong>'+this.publisherNameEdit+'</strong> successfully',
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
    setTimeout(() => {
      this.getData();
      this.publisherName = "";
    }, 200);
    $("#modalEdit").modal("hide");
  }

  nearlyDelete(id: string) {
    this.nearlyDeleteId = id;
  }

  deletePublisherById() {
    this.http.get(this.serverLink + '/deletePublishersREST/' + this.nearlyDeleteId).map((res: Response) => res.json())
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
          },
          delay: 2000
        });
      })
      err=>
      {
        var deleteSuccessful = new PNotify({
          title: 'Error',
          text: 'Cannot delete that item',
          type: 'error',
          addclass: "stack-topleft",
          animate: {
            animate: true,
            in_class: 'zoomInLeft',
            out_class: 'zoomOutRight'
          },
          delay: 2000
        });
      };
   
  }

  checkAddForm() {
    if (this.publisherName == "")
    {
      var deleteSuccessful = new PNotify({
        title: 'Error',
        text: 'Publisher name cannot be empty',
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
    this.addData();
  }

  checkEditForm() {
    if (this.publisherNameEdit == "")
    {
      var deleteSuccessful = new PNotify({
        title: 'Error',
        text: 'Publisher name cannot be empty',
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
    this.updatePublisher();
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

//chrome.exe --user-data-dir="C:/Chrome dev session" --disable-web-security
