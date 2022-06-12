import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from 'src/app/shared/models/user.model';
import { AuthService } from 'src/app/shared/services/auth.service';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.css'],
})
export class SigninComponent implements OnInit {
  user!: User;
  hasError!: boolean;
  formGroup!: FormGroup;

  constructor(
    private authService: AuthService,
    private formBuilder: FormBuilder,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.hasError = false;
    this.formGroup = this.formBuilder.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required]],
    });
  }

  login(form: FormGroup): void {
    if (!this.formGroup.valid) {
      this.hasError = true;
      return;
    }
    this.authService.signInUser(form.value).subscribe((res) => {
      localStorage.setItem('token', res.access_token);

      localStorage.setItem('refresh_token', res.refresh_token);
      localStorage.setItem('user_name', res.user_name);
      // localStorage.setItem("user", JSON.stringify(res.user));
      this.router.navigate(['/']).then(() => {
        window.location.reload();
      });
    });
  }
}
