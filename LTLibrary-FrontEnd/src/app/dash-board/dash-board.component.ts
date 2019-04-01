import { Component, OnInit } from '@angular/core';
import {MyServiceService} from '../my-service.service';
import {Chart} from'chart.js';
import { CookieService } from 'ngx-cookie-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dash-board',
  templateUrl: './dash-board.component.html',
  styleUrls: ['./dash-board.component.css'],
  providers: [CookieService]
})
export class DashBoardComponent implements OnInit {
  //Variables for current Librarian
  currentLibLink:string = "http://localhost:8080/SpringMVCHibernateCRUDExample/image/librarian/"
  currentLibId:string;
  currentLibFName:string;
  currentLibLName:string;
  currentLibRole:string;
  libData:any;

  
  chart=[];
  constructor(private _myService: MyServiceService,private cookieService: CookieService , private router: Router) { 

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

    this._myService.bookNotYetReturnAmount().subscribe(res =>{
      
      let bnyra= res
      console.log(bnyra)

      this.chart= new Chart('canvas',{
        type: 'pie',
        data: {
          datasets: [{
              data: [
                bnyra,
                7
              ],
              backgroundColor: [
                "rgba(0,0,255, 0.5)",
                "rgba(255,0,0, 0.5)"
            ],
              label: 'Dataset 1'
          }],
          labels: [
              "Book Not Yet Return",
              "Total Book"
          ]
      },
      options: {
          responsive: true
      }
      })
    });
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
