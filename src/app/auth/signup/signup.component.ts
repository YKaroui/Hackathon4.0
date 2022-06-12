import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from 'src/app/shared/models/user.model';
import { AuthService } from 'src/app/shared/services/auth.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css'],
})
export class SignupComponent implements OnInit {
  user!: User;
  formGroup!: FormGroup;
  hasError!: boolean;
  submitted!: boolean;

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit() {
    this.hasError = false;
    this.submitted = false;
    this.formGroup = this.formBuilder.group({
      name: ['', [Validators.required]],
      lastname: ['', [Validators.required]],
      username: ['', [Validators.required]],
      password: ['', [Validators.required]],
      email: ['', [Validators.required]],
    });
  }

  signUp(form: FormGroup) {
    if (!this.formGroup.valid) {
      this.hasError = true;
      return;
    }
    return this.authService.signUpUser(form.value).subscribe((res) => {
      this.submitted = true;
      console.log(res);
      // localStorage.setItem('token', res.token);
      localStorage.setItem('user', JSON.stringify(res));
      if (res.statusCode === 200) {
        // prompt('Success')
        // prompt("login now")
        // alert("login now")
      } else {
        console.log("it's not printing succcess msg ");
      }
      // this.router.navigate(["/signin"]).then(() => {
      //   window.location.reload();
      // });
    });
  }
}
