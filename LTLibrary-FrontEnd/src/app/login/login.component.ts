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

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  providers: [CookieService]
})
export class LoginComponent implements OnInit {
  //Server link
  serverLink: string = 'http://localhost:8080/SpringMVCHibernateCRUDExample';
  
  data:any;
  username:string;
  password:string;
  stillActive:boolean;
  libid:string;
  libFirstName:string;
  libLastName:string;
  libRole:string;

  errMes:string;

  constructor(private http: Http,private cookies: CookieService, private router: Router) {
    this.getData();
  }

  ngOnInit() {
  }

  getData()
  {
    return this.http.get(this.serverLink+'/getAllLibrariansREST')
    .map((res: Response) => res.json())
    .subscribe(data => {
      this.data = data;
      console.log(this.data);
    });
  }

  checkLogin()
  {
    var success:boolean = false;
    for (let value of this.data)
    {
      if (this.username == value.username)
      {
        if (this.password = value.password)
        {
          if (value.status == true)
          {
            success = true;
            this.libid = value.libid;
            this.libFirstName = value.firstname;
            this.libLastName = value.lastname;
            this.libRole = value.libRole;
            break;
          }
        }
      }
    }
    if (!success)
    {
      this.errMes = "Login failed. Please check your username and password";
    }
    else
    {
      this.cookies.set('loginLibId', this.libid);
      this.cookies.set('loginLibFirstName', this.libFirstName);
      this.cookies.set('loginLibLastName', this.libLastName);
      this.cookies.set('loginLibRole', this.libRole);
      this.router.navigate(['./Book']);
    }
  }
}
