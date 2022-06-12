import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { User } from 'src/app/shared/models/user.model';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private REST_API_SERVER = 'http://localhost:8083/hackathon4.0/';

  constructor(private httpService: HttpClient) {}

  createUser(user: User): Observable<User> {
    return this.httpService.post<User>(
      this.REST_API_SERVER + 'registration/user',
      user
    );
  }

  getUserByUsername(username: string): Observable<User> {
    return this.httpService
      .get(this.REST_API_SERVER + '?username=' + username)
      .pipe(map((res: User) => res));
  }

  getUserByResetpasswordtoken(resetpasswordtoken: string): Observable<User> {
    return this.httpService
      .get(this.REST_API_SERVER + '?resetpasswordtoken=' + resetpasswordtoken)
      .pipe(map((res: User) => res));
  }
}
