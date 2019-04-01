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
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css'],
  providers: [CookieService]
})
export class CategoryComponent implements OnInit {
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

 catId: string;
 catName: string = "";
 catNameEdit: string = "";
 nearlyDeleteId: string = "";

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
   return this.http.get(this.serverLink+'/getAllCategoriesREST')
     .map((res: Response) => res.json())
     .subscribe(data => {
       this.data = data;
      //  console.log(this.data);
     });
 }

 addData() {
   let category =
     {
      titlecategorieses: null,
      catname: this.catName
     };
   this.http.post(this.serverLink+'/addCategoryREST', category).subscribe(
     res => {
       var addSuccessful= new PNotify({
         title: 'Add Successfully !!!',
         text: 'You have added the category <strong>'+this.catName+'</strong> successfully',
         type: 'success',
         addclass: "stack-topleft",
         animate: {
             animate: true,
             in_class: 'rotateInDownLeft',
             out_class: 'rotateOutUpRight'
         },
         delay:2000
       });
     },
     err => {
       console.log(err);
     }
   );
   setTimeout(() => {
     this.getData();
     this.catName = "";
   }, 500);
   $("#modalAdd").modal("hide");
 }

 getCategoryById(id: String) {
   this.http.get(this.serverLink+'/getCategoryByIDREST/' + id).map((res: Response) => res.json())
     .subscribe(dataEdit => {
       this.dataEdit = dataEdit;
      //  console.log(this.dataEdit);
     });

   setTimeout(() => {
     this.catNameEdit= this.dataEdit.catname;
   }, 200);
 }

 updateCategory() {
   let category =
     {
       titlecategorieses: null,
       catid: this.dataEdit.catid,
       catname: this.catNameEdit
     };
   this.http.post(this.serverLink+'/updateCategoryREST', category).subscribe(
     res => {
       var updateSuccessful= new PNotify({
         title: 'Update Successfully !!!',
         text: 'You have updated the category <strong>'+this.catNameEdit+'</strong> successfully',
         type: 'info',
         addclass: "stack-topleft",
         animate: {
             animate: true,
             in_class: 'rotateInDownLeft',
             out_class: 'rotateOutUpRight'
         },
         delay:2000
       });
     },
     err => {
       console.log("Error occured");
     }
   );
   setTimeout(() => {
     this.getData();
     this.catNameEdit = "";
   }, 500);
   $("#modalEdit").modal("hide");
 }

 nearlyDelete(id: string) {
   this.nearlyDeleteId = id;
 }

 deleteCategoryById() {
   this.http.get(this.serverLink+'/deleteCategoryREST/' + this.nearlyDeleteId).map((res: Response) => res.json())
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
         },
         delay:2000
       });
     },err=>
     {
       $("#modalDelete").modal("hide");
       var deleteSuccessful = new PNotify({
         title: 'Error',
         text: 'Cannot delete this category',
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

 checkAddForm()
 {
    if (this.catName == "")
    {
      var deleteSuccessful = new PNotify({
        title: 'Error',
        text: 'Category name cannot be empty',
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
      this.addData();
    }
 }

 checkEditForm()
 {
    if (this.catNameEdit == "")
    {
      var deleteSuccessful = new PNotify({
        title: 'Error',
        text: 'Category name cannot be empty',
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
      this.updateCategory();
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
}
