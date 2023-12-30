import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../../../_services/auth.service';
import { TokenStorageService, } from '../../../../_services/token-storage.service';
import { fadeInUp400ms } from '../../../../../@vex/animations/fade-in-up.animation';
import { LayoutService } from 'src/@vex/services/layout.service';
import { NgxSpinnerService } from "ngx-spinner";
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'vex-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
  animations: [
    fadeInUp400ms
  ]
})
export class LoginComponent implements OnInit {
  submitted = false;
  form: UntypedFormGroup;

  constructor(
    private router: Router,
    private fb: UntypedFormBuilder,
    private authService: AuthService,
    private tokenStorage: TokenStorageService,
    private toast: ToastrService,
    private layoutService: LayoutService,
    private loadingSpinner: NgxSpinnerService

  ) { }

  ngOnInit() {
    this.loadingSpinner.hide();
    this.form = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  get f() { return this.form.controls; }

  login(): void {
    this.submitted = true;
    if (this.form.invalid) {
      return;
    }
    this.loadingSpinner.show()
    this.authService.login(this.form.value)
      .subscribe({
        next: (response) => {
          this.loadingSpinner.hide()
          this.tokenStorage.signOut();
          this.tokenStorage.saveToken(response.access_token);
          this.tokenStorage.saveUser(response);
          this.layoutService.getCurrentUser().subscribe({
            next: (value: any) => {
              if (value) {
                this.layoutService.currentUser = value;
              }
              this.router.navigate(['/admin/category'])
            }
          })
        },
        error: (error) => {
          this.loadingSpinner.hide()
          if (error.status === 401) {
            this.toast.error("Tên đăng nhập hoặc mật khẩu không chính xác!");
          } else {
            this.toast.error(error.statusText);
          }
        },
        complete: () => this.submitted = false
      })
  }
}
