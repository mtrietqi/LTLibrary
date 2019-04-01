import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import 'rxjs/add/operator/map';
@Injectable()
export class MyServiceService {

  constructor(private _http: HttpClient) { }

  bookNotYetReturnAmount(){
    return this._http.get("http://localhost:8080/SpringMVCHibernateCRUDExample/getBookNotYetReturnAmountREST").map(result => result);
  }

  bookAmount(){
    return this._http.get("http://localhost:8080/SpringMVCHibernateCRUDExample/getBookAmountREST").map(result => result);
  }
}
