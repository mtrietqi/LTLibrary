import { Component, OnInit } from '@angular/core';
import { Http, Response, Headers } from '@angular/http';
import { NgModel } from '@angular/forms';
import 'rxjs/add/operator/map';
import { CookieService } from 'ngx-cookie-service';
import { Router } from '@angular/router';


declare var $: any;
declare var angular: any;
declare var PNotify :any;
@Component({
  selector: 'app-borrower-type',
  templateUrl: './borrower-type.component.html',
  styleUrls: ['./borrower-type.component.css'],
  providers: [CookieService]
})
export class BorrowerTypeComponent implements OnInit {
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
  typeName: string = "";
  maxBookIssue: string = "";
  maxDayIssue: string = "";
  penaltyPerDay: string = "";
  nearlyDeleteId:string = "";
  
  typeNameEdit: string = "";
  maxBookIssueEdit: string = "";
  maxDayIssueEdit: string = "";
  penaltyPerDayEdit: string = "";


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
    }, 200);
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
    $('#select2').select2();
  }

  private getData() {
    return this.http.get(this.serverLink+'/getAllBorrowerTypeREST')
      .map((res: Response) => res.json())
      .subscribe(data => {
        this.data = data;
        // console.log(this.data);
      });
  }

  getBorrowerTypeById(id: string) {
    // alert(id);
    this.http.get(this.serverLink+'/getBorrowerTypeByIdREST/' + id).map((res: Response) => res.json())
      .subscribe(dataEdit => {
        this.dataEdit = dataEdit;
        // console.log(this.dataEdit);
      });

    setTimeout(() => {
      this.typeNameEdit = this.dataEdit.typename;
      this.maxBookIssueEdit = this.dataEdit.maxbookissue;
      this.maxDayIssueEdit = this.dataEdit.maxdayissue;
      this.penaltyPerDayEdit = this.dataEdit.penaltyperday;
    }, 200);
  }

  updateBorrowerType(){
    this.updateData(this.dataEdit.borrowertypeid, this.typeNameEdit, this.maxBookIssueEdit, this.maxDayIssueEdit, this.penaltyPerDayEdit);
    setTimeout(() => {
      this.getData();
    },500);    
  }

  updateData(borrowerTypeId:string, typename:string, maxbookissue:string, maxdayissue:string, penaltyperday:string)
  {
    let borrowerTypeEdit=
    {
      borrowerses: null,
      borrowertypeid: borrowerTypeId,
      typename: typename,
      maxbookissue: maxbookissue,
      maxdayissue: maxdayissue,
      penaltyperday: penaltyperday
    };
    

    this.http.post(this.serverLink+'/updateBorrowerTypeREST',borrowerTypeEdit).subscribe(
      res => {
        // console.log(res);
      },
      err => {
        console.log(err);
      }
    );  
    $("#modalEdit").modal("hide");
    //Update announce
    var updateSuccessful= new PNotify({
      title: 'Update Successfully !!!',
      text: 'You have updated the borrower type <strong>'+this.typeNameEdit+'</strong> successfully',
      type: 'info',
      addclass: "stack-topleft",
      animate: {
          animate: true,
          in_class: 'rotateInDownLeft',
          out_class: 'rotateOutUpRight'
      },delay:2000
    });
  }

  nearlyDelete(id:string)
  {
    this.nearlyDeleteId = id;
  }

  deleteBorrowerType(id:String){
    this.http.get(this.serverLink+'/deleteBorrowerTypeREST/'+this.nearlyDeleteId).map((res: Response) => res.json())
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

  addBorrowerType(){
    this.addData(this.typeName, this.maxBookIssue, this.maxDayIssue, this.penaltyPerDay);
    setTimeout(() => {
      this.getData();
      this.typeName = "";
      this.maxBookIssue = "";
      this.maxDayIssue = "";
      this.penaltyPerDay = "";
    },500);
  }

  addData(typeName:String, maxBookIssue:String, maxDayIssue:String, penaltyPerDay:String)
  {
    let borrowerType=
    {
      typename:typeName,
      maxbookissue:maxBookIssue,
      maxdayissue:maxDayIssue,
      penaltyperday:penaltyPerDay
    };
    this.http.post(this.serverLink+'/addBorrowerTypeREST',borrowerType).subscribe(
      res => {
        // console.log(res);
      },
      err => {
        console.log("Error occured");
      }
    );
    $("#modalAdd").modal("hide");
    var addSuccessful= new PNotify({
      title: 'Add Successfully !!!',
      text: 'You have added the borrower type <strong>'+this.typeName+'</strong> successfully',
      type: 'success',
      addclass: "stack-topleft",
      animate: {
          animate: true,
          in_class: 'rotateInDownLeft',
          out_class: 'rotateOutUpRight'
      },delay:2000
    });

  }

  checkAddForm()
  {
    if (this.typeName == "")
    {
      var deleteSuccessful= new PNotify({
        title: 'Error',
        text: 'Type name cannot be empty',
        type: 'error',
        addclass: "stack-topleft",
        animate: {
            animate: true,
            in_class: 'zoomInLeft',
            out_class: 'zoomOutRight'
        },delay:2000
      })
    }
    else if (this.maxBookIssue == "")
    {
      var deleteSuccessful= new PNotify({
        title: 'Error',
        text: 'Max book issue cannot be empty',
        type: 'error',
        addclass: "stack-topleft",
        animate: {
            animate: true,
            in_class: 'zoomInLeft',
            out_class: 'zoomOutRight'
        },delay:2000
      })
    }
    else if (isNaN(Number(this.maxBookIssue)) || Number(this.maxBookIssue)<0) {
      var deleteSuccessful = new PNotify({
        title: 'Error',
        text: 'Max book issue has to be number and has to be positive',
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
    else if (this.maxDayIssue == "")
    {
      //Show Pnotify announcement
      var deleteSuccessful= new PNotify({
        title: 'Error',
        text: 'Max day issue cannot be empty',
        type: 'error',
        addclass: "stack-topleft",
        animate: {
            animate: true,
            in_class: 'zoomInLeft',
            out_class: 'zoomOutRight'
        },delay:2000
      })
    }
    else if (isNaN(Number(this.maxDayIssue)) || Number(this.maxDayIssue)<0) {
      var deleteSuccessful = new PNotify({
        title: 'Error',
        text: 'Max day issue has to be number and has to be positive',
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
    else if (this.penaltyPerDay == "")
    {
      var deleteSuccessful= new PNotify({
        title: 'Error',
        text: 'Penalty per day cannot be empty',
        type: 'error',
        addclass: "stack-topleft",
        animate: {
            animate: true,
            in_class: 'zoomInLeft',
            out_class: 'zoomOutRight'
        },delay:2000
      })
    }
    else if (isNaN(Number(this.penaltyPerDay)) || Number(this.penaltyPerDay)<0) {
      var deleteSuccessful = new PNotify({
        title: 'Error',
        text: 'Penalty per day has to be number and has to be positive',
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
      this.addBorrowerType();
    }
  }

  checkEditForm()
  {
    if (this.typeNameEdit == "")
    {
      var deleteSuccessful= new PNotify({
        title: 'Error',
        text: 'Type name cannot be empty',
        type: 'error',
        addclass: "stack-topleft",
        animate: {
            animate: true,
            in_class: 'zoomInLeft',
            out_class: 'zoomOutRight'
        },delay:2000
      })
    }
    else if (this.maxBookIssueEdit == "")
    {
      var deleteSuccessful= new PNotify({
        title: 'Error',
        text: 'Max book issue cannot be empty',
        type: 'error',
        addclass: "stack-topleft",
        animate: {
            animate: true,
            in_class: 'zoomInLeft',
            out_class: 'zoomOutRight'
        },delay:2000
      })
    }
    else if (isNaN(Number(this.maxBookIssueEdit)) || Number(this.maxBookIssueEdit)<0) {
      var deleteSuccessful = new PNotify({
        title: 'Error',
        text: 'Max book issue has to be number and has to be positive',
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
    else if (this.maxDayIssueEdit == "")
    {
      var deleteSuccessful= new PNotify({
        title: 'Error',
        text: 'Max day issue cannot be empty',
        type: 'error',
        addclass: "stack-topleft",
        animate: {
            animate: true,
            in_class: 'zoomInLeft',
            out_class: 'zoomOutRight'
        },delay:2000
      })
    }
    else if (isNaN(Number(this.maxDayIssueEdit)) || Number(this.maxDayIssueEdit)<0) {
      var deleteSuccessful = new PNotify({
        title: 'Error',
        text: 'Max day issue has to be number and has to be positive',
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
    else if (this.penaltyPerDayEdit == "")
    {
      var deleteSuccessful= new PNotify({
        title: 'Error',
        text: 'Penalty per day cannot be empty',
        type: 'error',
        addclass: "stack-topleft",
        animate: {
            animate: true,
            in_class: 'zoomInLeft',
            out_class: 'zoomOutRight'
        },delay:2000
      })
    }
    else if (isNaN(Number(this.penaltyPerDayEdit)) || Number(this.penaltyPerDayEdit)<0) {
      //Show Pnotify announcement
      var deleteSuccessful = new PNotify({
        title: 'Error',
        text: 'Penalty per day has to be number and has to be positive',
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
      this.updateBorrowerType();
    }
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
