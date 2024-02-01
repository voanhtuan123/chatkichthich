import { ChangeDetectorRef, Component, Input, OnInit } from '@angular/core';
import { NavigationService } from '../../services/navigation.service';
import { LayoutService } from '../../services/layout.service';
import { ConfigService } from '../../config/config.service';
import { map } from 'rxjs/operators';
import { NavigationLink } from '../../interfaces/navigation-item.interface';
import { Router } from '@angular/router';

@Component({
  selector: 'vex-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.scss']
})
export class SidenavComponent implements OnInit {

  @Input() collapsed: boolean;
  @Input() items = this.navigationService.items;
  @Input() title = 'RASA';

  collapsedOpen$ = this.layoutService.sidenavCollapsedOpen$;
  imageUrl$ = this.configService.config$.pipe(map(config => config.sidenav.imageUrl));

  constructor(
    private navigationService: NavigationService,
    private layoutService: LayoutService,
    private configService: ConfigService,
    private router: Router,
    private cd: ChangeDetectorRef
  ) { }

  ngOnInit() {
    if (!this.layoutService.currentUser) {
      this.layoutService.getCurrentUser().subscribe({
        next: (response) => this.layoutService.currentUser = response,
        error: () => this.router.navigate(['/login']),
      })
    }
  }

  collapseOpenSidenav() {
    this.cd.detectChanges();
    this.layoutService.collapseOpenSidenav();
  }

  collapseCloseSidenav() {
    this.cd.detectChanges();
    this.layoutService.collapseCloseSidenav();
  }

  trackByRoute(_: number, item: NavigationLink): string {
    return item.route;
  }

  clickHome() {
    this.router.navigate(["/"]);
  }
}
