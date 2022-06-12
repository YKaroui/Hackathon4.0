import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Router } from '@angular/router';
import { User } from '../models/user.model';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private REST_API_SERVER = 'http://localhost:8083/hackathon4.0';
  constructor(private httpService: HttpClient, private router: Router) {}
  signUpUser(user: User) {
    console.log(user.username);
    console.log(user.password);
    return this.httpService.post<any>(
      this.REST_API_SERVER + '/registration/user',
      user
    );
  }
  signInUser(user: any) {
    console.log('data sent' + user.username);
    console.log('data sent' + user.password);

    const payload = new HttpParams()
      .set('username', user.username)
      .set('password', user.password);

    let httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/x-www-form-urlencoded',
      }),
    };
    return this.httpService.post<any>(
      this.REST_API_SERVER + '/login',
      payload,
      httpOptions
    );
  }

  forgetpassword(user: User) {
    return this.httpService.post<any>(
      this.REST_API_SERVER + '/forgot_password?username=' + user.username,
      user
    );
  }

  resetPassword(user: User, url: string) {
    return this.httpService.post<any>(
      this.REST_API_SERVER + url,
      user.password
    );
  }

  loggedIn() {
    return !!localStorage.getItem('token');
  }

  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    this.router.navigate(['/signin']);
  }

  getToken() {
    return localStorage.getItem('token');
  }

  getUser(): any {
    const user = localStorage.getItem('user');
    if (user) {
      return JSON.parse(user);
    }
    return {};
  }
}
