import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

import { environment } from '../environments/environment';
import { ClientInfo } from './models/ClientInfo';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json',
  })
};

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(private http: HttpClient) { 

  }

  getSolution(data: ClientInfo): Observable<{}> {
    console.log('Fetching solution for parameters:', data);
    return this.http.post(environment.apiUrl + '/', data, httpOptions);
  }
}
