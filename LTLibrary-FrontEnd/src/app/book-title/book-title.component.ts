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
  selector: 'app-book-title',
  templateUrl: './book-title.component.html',
  styleUrls: ['./book-title.component.css'],
  providers: [CookieService]
})
export class BookTitleComponent implements OnInit {
  //Server link
  serverLink: string = 'http://localhost:8080/SpringMVCHibernateCRUDExample';

   //Variables for current Librarian
   currentLibLink:string = this.serverLink+'/image/librarian/'
   currentLibId:string;
   currentLibFName:string;
   currentLibLName:string;
   currentLibRole:string;
   libData:any;

  backendBookImageLink: string = this.serverLink+"/image/bookImages/";
  backendTOCLink: string = this.serverLink+"/image/tableOfContent/";


  data: any = null;
  dataEdit: any = [];
  titleAuthorData: any = [];
  categoryData: any = [];
  publisherData: any = [];
  formData = new FormData();

  titleId:  string="";
  publisher: any;
  isbn:  string="";
  bookTitle:  string="";
  edition:  string="";
  pubDate:  string="";
  price:  string="";
  bookImage:  string="";
  tableContent: string="";
  description: string="";

  titleIdEdit:  string="";
  publisherEdit: any;
  isbnEdit:  string="";
  bookTitleEdit:  string="";
  editionEdit:  string="";
  pubDateEdit:  string="";
  priceEdit:  string="";
  bookImageEdit:  string="";
  tableContentEdit:  string="";
  descriptionEdit:  string="";
  selectedTitleAuthor: any;
  selectedTitleCategories: any;

  nearlyDeleteId: string
  constructor(private http: Http,private cookieService: CookieService , private router: Router) {
    this.getData();
    $.each(this.data, function (index, value) {
      alert(value.titleid);
      this.getTitleAuthorDataById(value.titleid);
    });

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
      $('input').iCheck({
        checkboxClass: 'icheckbox_square-blue',
        radioClass: 'iradio_square-blue',
        increaseArea: '20%' // optional
      });
      $('input[name="PublishingDate"]').daterangepicker({
        locale: {
          format: 'YYYY-MM-DD'
        },
        singleDatePicker: true,
        showDropdowns: true
      });
      $('#CategorySelectionAdd').select2();
      $('#CategorySelectionEdit').select2();
      $('#AuthorSelectionAdd').select2();
      $('#AuthorSelectionEdit').select2();
      $('#PublisherNameAdd').select2();
      $('#PublisherNameEdit').select2();
    }, 300);
  }

  //-------------------------------MAIN METHODS-------------------------------//

  checkAddForm()
  {
      if (this.isbn == "")
      {
        var deleteSuccessful = new PNotify({
          title: 'Error',
          text: 'ISBN cannot be empty',
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
      else if (this.bookTitle == "")
      {
        var deleteSuccessful = new PNotify({
          title: 'Error',
          text: 'Book title cannot be empty',
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
      else if (this.price == "")
      {
        var deleteSuccessful = new PNotify({
          title: 'Error',
          text: 'Reference price cannot be empty',
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
      //If the price is empty or the price is not the number or the price < 0
      else if (isNaN(Number(this.price)) || Number(this.price)<0) {
        var deleteSuccessful = new PNotify({
          title: 'Error',
          text: 'Reference price has to be number and has to be positive',
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
      this.addBookTitle();
  }

  checkEditForm()
  {
    if (this.isbnEdit == "")
    {
      var deleteSuccessful = new PNotify({
        title: 'Error',
        text: 'ISBN cannot be empty',
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
    else if (this.bookTitleEdit == "")
    {
      var deleteSuccessful = new PNotify({
        title: 'Error',
        text: 'Book title cannot be empty',
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
    else if (this.priceEdit == "")
    {
      var deleteSuccessful = new PNotify({
        title: 'Error',
        text: 'Reference price cannot be empty',
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
    else if (isNaN(Number(this.priceEdit)) || Number(this.priceEdit)<0) {
      var deleteSuccessful = new PNotify({
        title: 'Error',
        text: 'Reference price has to be number and has to be positive',
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
    this.updateBookTitle();
  }


  private getData() {
    return this.http.get(this.serverLink+'/getAllTitlesREST')
      .map((res: Response) => res.json())
      .subscribe(data => {
        this.data = data;
        // console.log(this.data);
      });
  }

  getTitleAuthorData() {
    return this.http.get(this.serverLink+'/getAllAuthorsREST')
      .map((res: Response) => res.json())
      .subscribe(data => {
        this.titleAuthorData = data;
        // console.log(this.titleAuthorData);
      });
  }


  getCategoryData() {
    return this.http.get(this.serverLink+'/getAllCategoriesREST')
      .map((res: Response) => res.json())
      .subscribe(data => {
        this.categoryData = data;
        // console.log(this.categoryData);
      });
  }

  getPublisherData() {
    return this.http.get(this.serverLink+'/getAllPublishersREST')
      .map((res: Response) => res.json())
      .subscribe(data => {
        this.publisherData = data;
        // console.log(this.publisherData);
      });
  }

  getTitleById(id: string) {
    // alert(id);
    this.http.get(this.serverLink+'/getTitlesREST/' + id).map((res: Response) => res.json())
      .subscribe(dataEdit => {
        this.dataEdit = dataEdit;
        // console.log(this.dataEdit);
      });

    setTimeout(() => {
      //Reset variables
      this.titleIdEdit = this.dataEdit.titleid;
      this.publisherEdit = this.dataEdit.publishers;
      this.isbnEdit = this.dataEdit.isbn;
      this.bookTitleEdit = this.dataEdit.booktitle;
      this.editionEdit = this.dataEdit.edition;
      this.pubDateEdit = this.dataEdit.pubdate;
      this.priceEdit = this.dataEdit.price;
      this.bookImageEdit = this.dataEdit.bookimage;
      this.tableContentEdit = this.dataEdit.tablecontent;
      this.descriptionEdit = this.dataEdit.description;
    }, 300);
  }

  initializeAddForm() {
    //Reset 3 selection boxes
    this.getTitleAuthorData();
    this.getCategoryData();
    this.getPublisherData();

    //Clear two file upload boxes
    $('#bookImageSelection').val('');
    $('#tableofContent').val('');

    //Initialize value for 2 selection boxes
    setTimeout(() => {
      $('#AuthorSelectionAdd').empty().trigger("change");
      $.each(this.titleAuthorData, function (index, value) {
        var fullname = value.firstname + " " + value.lastname;
        var newOption = new Option(fullname, value.auid, false, false);
        $('#AuthorSelectionAdd').append(newOption).trigger('change');
      });

      $('#CategorySelectionAdd').empty().trigger("change");
      $.each(this.categoryData, function (index, value) {
        var newOption = new Option(value.catname, value.catid, false, false);
        $('#CategorySelectionAdd').append(newOption).trigger('change');
      });

      $('#PublisherNameAdd').empty().trigger("change");
      $.each(this.publisherData, function (index, value) {
        var newOption = new Option(value.pubname, value.pubid, false, false);
        $('#PublisherNameAdd').append(newOption).trigger('change');
      });

    }, 500);
  }

  addBookTitle() {
    var auIdArray = $("#AuthorSelectionAdd").val();
    var catIdArray = $("#CategorySelectionAdd").val();
    var currentTitleId;
    var newData: any;

    this.formData.append('publisherId', $("#PublisherNameAdd").val());
    this.formData.append('isbn', this.isbn);
    this.formData.append('bookTitle', this.bookTitle);
    this.formData.append('edition', this.edition);
    this.formData.append('pubdate', $("#publishingDate").val());
    this.formData.append('price', this.price);
    this.formData.append('description', this.description);

    this.http.post(this.serverLink+'/addTitlesREST', this.formData)
      .map((res: Response) => res.json())
      .subscribe(
      res2 => {
        // console.log(res2);
        newData = res2;       
      },
      err => {
        console.log(err);
      }
      );

    setTimeout(() => {
      currentTitleId = newData.titleid;
      for (let entry of auIdArray) {
        var titleAuthor = new FormData();
        titleAuthor.append('auId', entry);
        titleAuthor.append('titleId', currentTitleId);
        this.http.post(this.serverLink+'/addTitleAuthorsREST', titleAuthor)
          .map((res: Response) => res.json())
          .subscribe(
          res2 => {
            // console.log(res2);
          },
          err => {
            console.log(err);
          }
          );
      }

      for (let entry of catIdArray) {
        var titleCategory = new FormData();
        titleCategory.append('catId', entry);
        titleCategory.append('titleId', currentTitleId);
        this.http.post(this.serverLink+'/addTitleCategoriesREST', titleCategory)
          .map((res: Response) => res.json())
          .subscribe(
          res2 => {
            // console.log(res2);
          },
          err => {
            console.log(err);
          }
          );
      }


      //Reset form data value
      this.formData = new FormData();
      $("#modalAdd").modal('hide');
      var addSuccessful= new PNotify({
        title: 'Add Successfully !!!',
        text: 'You have added the book title <strong>'+this.bookTitle+'</strong> successfully',
        type: 'success',
        addclass: "stack-topleft",
        animate: {
            animate: true,
            in_class: 'rotateInDownLeft',
            out_class: 'rotateOutUpRight'
        },
        delay: 3000
      })

      setTimeout(() => {
        this.getData();
        this.isbn = "";
        this.bookTitle = "";
        this.edition = "";
        this.price = "";
        this.description = "";
      }, 500);
    }, 2500);


  }

  initializeUploadForm(id: string) {
    //Reset formdata
    this.formData = new FormData();
    this.getTitleById(id);
    this.getTitleAuthorData();
    this.getCategoryData();
    this.getPublisherData();

    //Get selected titleauthor
    this.http.get(this.serverLink+'/getTitleAuthorFromTitleId/' + id).map((res: Response) => res.json())
      .subscribe(dataEdit => {
        this.selectedTitleAuthor = dataEdit;
        // console.log(this.selectedTitleAuthor);
      });

    //Get selected titlecateogyr
    this.http.get(this.serverLink+'/getTitleCategoryFromTitleId/' + id).map((res: Response) => res.json())
      .subscribe(dataEdit => {
        this.selectedTitleCategories = dataEdit;
        // console.log(this.selectedTitleCategories);
      });

    //Clear two file upload boxes
    $('#bookImageSelection').val('');
    $('#tableofContent').val('');

    var currentAuthorData = this.dataEdit.titleauthorses;
    //Initialize value and set selection for 3 selection boxes
    setTimeout(() => {
      $('#AuthorSelectionEdit').empty().trigger("change");
      $('#CategorySelectionEdit').empty().trigger("change");
      $('#PublisherNameEdit').empty().trigger("change");

      for (let entry of this.titleAuthorData) {
        var added = false;
        for (let entry2 of this.selectedTitleAuthor) {
          var fullname = entry.firstname + " " + entry.lastname;
          var newOption = null;
          if (entry.auid == entry2.authors.auid) {
            newOption = new Option(fullname, entry.auid, false, true);
            $('#AuthorSelectionEdit').append(newOption).trigger('change');
            added = true;
          }
          else {
            newOption = new Option(fullname, entry.auid, false, false);
          }
        }
        if (!added) {
          $('#AuthorSelectionEdit').append(newOption).trigger('change');
        }
      }

      for (let entry of this.categoryData) {
        var added = false;
        for (let entry2 of this.selectedTitleCategories) {
          var newOption = null;
          if (entry.catid == entry2.categories.catid) {
            newOption = new Option(entry.catname, entry.catid, false, true);
            $('#CategorySelectionEdit').append(newOption).trigger('change');
            added = true;
          }
          else {
            newOption = new Option(entry.catname, entry.catid, false, false);
          }
        }
        if (!added) {
          $('#CategorySelectionEdit').append(newOption).trigger('change');
        }
      }

      for (let value of this.publisherData) {
        var newOption = null;
        if (value.pubid == this.publisherEdit.pubid) {
          newOption = new Option(value.pubname, value.pubid, false, true);
        }
        else {
          newOption = new Option(value.pubname, value.pubid, false, false);
        }
        $('#PublisherNameEdit').append(newOption).trigger('change');
      };
    }, 500);

  }

  updateBookTitle() {
    var auIdArray = $("#AuthorSelectionEdit").val();
    var catIdArray = $("#CategorySelectionEdit").val();
    var currentTitleId;
    var newData: any;

    this.formData.append('publisherId', $("#PublisherNameEdit").val());
    this.formData.append('isbn', this.isbnEdit);
    this.formData.append('bookTitle', this.bookTitleEdit);
    this.formData.append('edition', this.editionEdit);
    this.formData.append('pubdate', $("#publishingDateEdit").val());
    this.formData.append('price', this.priceEdit);
    this.formData.append('description', this.descriptionEdit);
    this.formData.append('titleId', this.titleIdEdit);

    this.http.post(this.serverLink+'/updateTitlesREST', this.formData)
      .map((res: Response) => res.json())
      .subscribe(
      res2 => {
        // console.log(res2);
        newData = res2;
      },
      err => {
        console.log(err);
      }
      );

    setTimeout(() => {
      currentTitleId = newData.titleid;
      //Delete all selected titleauthor
      for (let entry of this.selectedTitleAuthor) {
        this.http.get(this.serverLink+'/deleteTitleAuthorsREST/' + entry.titleauthorid).map((res: Response) => res.json())
          .subscribe(data => {
            //Should do nothing here!!!
          });
      }

      // Add new titleauthor to database
      for (let entry of auIdArray) {
        var titleAuthor = new FormData();
        titleAuthor.append('auId', entry);
        titleAuthor.append('titleId', currentTitleId);
        this.http.post(this.serverLink+'/addTitleAuthorsREST', titleAuthor)
          .map((res: Response) => res.json())
          .subscribe(

          );
      }

      //Delete all selected titlecategories
      for (let entry of this.selectedTitleCategories) {
        this.http.get(this.serverLink+'/deleteTitleCategoriesREST/' + entry.titlecatid).map((res: Response) => res.json())
          .subscribe(data => {
            //Should do nothing here!!!
          });
      }

      // Add new titlecategories to database
      for (let entry of catIdArray) {
        var titleCategory = new FormData();
        titleCategory.append('catId', entry);
        titleCategory.append('titleId', currentTitleId);
        this.http.post(this.serverLink+'/addTitleCategoriesREST', titleCategory)
          .map((res: Response) => res.json())
          .subscribe(

          );
      }



      setTimeout(() => {
        this.getData();
        //Reset form data value
        this.formData = new FormData();
        $("#modalEdit").modal('hide');
        var updateSuccessful= new PNotify({
          title: 'Update Successfully !!!',
          text: 'You have updated the book title <strong>'+this.bookTitleEdit+'</strong> successfully',
          type: 'info',
          addclass: "stack-topleft",
          animate: {
              animate: true,
              in_class: 'rotateInDownLeft',
              out_class: 'rotateOutUpRight'
          },
          delay: 3000
        });
        this.isbnEdit = "";
        this.bookTitleEdit = "";
        this.editionEdit = "";
        this.priceEdit = "";
        this.descriptionEdit = "";
      }, 2000);
    }, 1000);
  }

  nearlyDelete(id: string) {
    //Get selected titleauthor
    this.http.get(this.serverLink+'/getTitleAuthorFromTitleId/' + id).map((res: Response) => res.json())
      .subscribe(dataEdit => {
        this.selectedTitleAuthor = dataEdit;
        // console.log(this.selectedTitleAuthor);
      });

    //Get selected titlecateogyr
    this.http.get(this.serverLink+'/getTitleCategoryFromTitleId/' + id).map((res: Response) => res.json())
      .subscribe(dataEdit => {
        this.selectedTitleCategories = dataEdit;
        // console.log(this.selectedTitleCategories);
      });
    this.nearlyDeleteId = id;

  }

  deleteBookTitle() {
    //Delete all selected titleauthor
    for (let entry of this.selectedTitleAuthor) {
      this.http.get(this.serverLink+'/deleteTitleAuthorsREST/' + entry.titleauthorid).map((res: Response) => res.json())
        .subscribe(data => {
          //Should do nothing here!!!
        });
    }

    //Delete all selected titlecategories
    for (let entry of this.selectedTitleCategories) {
      this.http.get(this.serverLink+'/deleteTitleCategoriesREST/' + entry.titlecatid).map((res: Response) => res.json())
        .subscribe(data => {
          //Should do nothing here!!!
        });
    }


    this.http.get(this.serverLink+'/deleteBookTitle/' + this.nearlyDeleteId).map((res: Response) => res.json())
      .subscribe(data => {
        this.data = data;
        // console.log(this.data);
        setTimeout(() => {
          this.getData();
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
            delay: 2000
          })
        }, 2000);
      },err=>
    {
      var deleteSuccessful= new PNotify({
        title: 'Errror',
        text: 'Cannot delete that item',
        type: 'error',
        addclass: "stack-topleft",
        animate: {
            animate: true,
            in_class: 'zoomInLeft',
            out_class: 'zoomOutRight'
        },
        delay: 2000
      })
      $("#modalDelete").modal("hide");
    });



  }

  //-------------------------------END MAIN METHODS-------------------------------//

  uploadBookImage(event) {
    let elem = event.target;
    if (elem.files.length > 0) {
      this.formData.append('bookImage', elem.files[0]);
    }
  }

  uploadTableOfContent(event) {
    let elem = event.target;
    if (elem.files.length > 0) {
      this.formData.append('tableContent', elem.files[0]);
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
    signOut()
    {
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
              x1 = x1.replace(rgx, '$1' + '.' + '$2');
      }
      return x1 + x2;
    }

}

//chrome.exe --user-data-dir="C:/Chrome dev session" --disable-web-security