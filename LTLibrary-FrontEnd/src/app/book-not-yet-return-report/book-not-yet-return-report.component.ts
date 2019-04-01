import { Component, OnInit } from '@angular/core';
import { MyServiceService } from '../my-service.service';
import { Chart } from 'chart.js';
import { Http, Response, Headers } from '@angular/http';
import { NgModel } from '@angular/forms';
import 'rxjs/add/operator/map'
import { LIFECYCLE_HOOKS_VALUES } from '@angular/compiler/src/lifecycle_reflector';
import { DomSanitizer } from '@angular/platform-browser';
import { CookieService } from 'ngx-cookie-service';
import { Router } from '@angular/router';


declare var $: any;
@Component({
  selector: 'app-book-not-yet-return-report',
  templateUrl: './book-not-yet-return-report.component.html',
  styleUrls: ['./book-not-yet-return-report.component.css'],
  providers: [CookieService]
})
export class BookNotYetReturnReportComponent implements OnInit {
  //Server link
  serverLink: string = 'http://localhost:8080/SpringMVCHibernateCRUDExample';
  data;
  chart = [];
  bnyra;
  bookAmount;
  //Variables for current Librarian
  currentLibLink: string = this.serverLink + '/image/librarian/'
  currentLibId: string;
  currentLibFName: string;
  currentLibLName: string;
  currentLibRole: string;
  libData: any;
  constructor(private _myService: MyServiceService, private http: Http, private cookieService: CookieService, private router: Router) {
    // constructor(private http: Http) {
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
    this._myService.bookNotYetReturnAmount().subscribe(res => {

      this.bnyra = res
      // console.log(this.bnyra)

    });

    this._myService.bookAmount().subscribe(res => {

      this.bookAmount = res
      // console.log(this.bookAmount)

    });



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
      
      //Add dato to ChartJS
      this.chart = new Chart('canvas', {
        type: 'pie',
        data: {
          datasets: [{
            data: [
              this.bnyra,
              this.bookAmount - this.bnyra
            ],
            backgroundColor: [
              "rgba(0,0,255, 0.5)",
              "rgba(255,0,0, 0.5)"
            ],
            label: 'Dataset 1'
          }],
          labels: [
            "Book Not Yet Return",
            "Available Book"
          ]
        },
        options: {
          responsive: true
        }
      });
    }, 500);



  }

  private getData() {
    return this.http.get(this.serverLink + '/getAllBookNotYetReturnREST')
      .map((res: Response) => res.json())
      .subscribe(data => {
        this.data = data;
        // console.log(this.data);
      });
  }

  addDateTime(borDate: string, maxdayissue: number) {
    var dateTemp = new Date(borDate);
    dateTemp.setDate(dateTemp.getDate() + maxdayissue);
    return dateTemp.getFullYear() + '-' +
      ((dateTemp.getMonth() + 1) < 10 ? '0' : '') + (dateTemp.getMonth() + 1) + '-' +
      (dateTemp.getDate() < 10 ? '0' : '') + dateTemp.getDate();
  }

  signOut() {
    this.cookieService.delete('loginLibId');
    this.cookieService.delete('loginLibFirstName');
    this.cookieService.delete('loginLibLastName');
    this.cookieService.delete('loginLibRole');
    this.router.navigate(['./Login']);
  }

}
