import { BrowserModule } from '@angular/platform-browser';
import { NgModule, APP_INITIALIZER } from '@angular/core';
import { AuthGuard } from './_guards/index';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { VexModule } from '../@vex/vex.module';
import { HttpClientModule } from '@angular/common/http';
import { CustomLayoutModule } from './custom-layout/custom-layout.module';
import { ConfigInitService } from './init/config-init.service';
import { authInterceptorProviders } from './_helpers/auth.interceptor';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { NgxSpinnerModule } from "ngx-spinner";
import { ToastrModule } from "ngx-toastr";
import { DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE, MatNativeDateModule } from "@angular/material/core";
import { MomentDateAdapter } from "@angular/material-moment-adapter";
import { DATE_FORMATS } from "./_common/date-formats";

@NgModule({
  declarations: [AppComponent],
  imports: [
    MatNativeDateModule,
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    // Vex
    VexModule,
    CustomLayoutModule,
    MatSnackBarModule,
    NgxSpinnerModule.forRoot({ type: "ball-spin-clockwise-fade" }),
    ToastrModule.forRoot({
      timeOut: 10000,
      positionClass: 'toast-top-right',
      closeButton: true,
      progressBar: true,
      progressAnimation: "decreasing",
      enableHtml: true,
      tapToDismiss: true,
    }),
  ],
  providers: [
    AuthGuard,
    authInterceptorProviders,
    {
      provide: APP_INITIALIZER,
      multi: true,
      deps: [ConfigInitService],
      useFactory: (appConfigService: ConfigInitService) => () => appConfigService.loadAppConfig()
    },
    { provide: MAT_DATE_LOCALE, useValue: 'vi' },
    { provide: DateAdapter, useClass: MomentDateAdapter, deps: [MAT_DATE_LOCALE] },
    { provide: MAT_DATE_FORMATS, useValue: DATE_FORMATS },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
