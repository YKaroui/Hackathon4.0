import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Event } from '@angular/router';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
};

@Injectable({
  providedIn: 'root',
})
export class EventService {
  private REST_API_SERVER = 'http://localhost:8083/hackathon4.0';

  constructor(private http: HttpClient) {}

  getAllEvent(): Observable<any> {
    return this.http.get(`${this.REST_API_SERVER}/event/retrieveAll`);
  }
}
