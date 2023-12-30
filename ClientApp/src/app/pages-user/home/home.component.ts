import { Component, OnInit } from '@angular/core';
import { HomePage } from 'src/app/_models/HomePage.model';
import { PageUserService } from '../pages-user.service';

@Component({
  selector: 'home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  homePage?: HomePage = new HomePage();

  constructor(private service: PageUserService) { }

  ngOnInit(): void {
    this.service.getHomePage().forEach(res => this.homePage = res);
  }
}
