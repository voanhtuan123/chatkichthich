import { Component, OnInit } from '@angular/core';
import { LayoutService } from '../../@vex/services/layout.service';
import { filter, map, startWith } from 'rxjs/operators';
import { NavigationEnd, Router } from '@angular/router';
import { checkRouterChildsData } from '../../@vex/utils/check-router-childs-data';
import { ConfigService } from '../../@vex/config/config.service';
import { UntilDestroy } from '@ngneat/until-destroy';

@UntilDestroy()
@Component({
  selector: 'vex-custom-layout',
  templateUrl: './custom-layout.component.html',
  styleUrls: ['./custom-layout.component.scss']
})
export class CustomLayoutComponent implements OnInit {

  sidenavCollapsed$ = this.layoutService.sidenavCollapsed$;
  isFooterVisible$ = this.configService.config$.pipe(map(config => config.footer.visible));
  isDesktop$ = this.layoutService.isDesktop$;

  toolbarShadowEnabled$ = this.router.events.pipe(
    filter(event => event instanceof NavigationEnd),
    startWith(null),
    map(() => checkRouterChildsData(this.router.routerState.root.snapshot, data => data.toolbarShadowEnabled))
  );

  constructor(
    private layoutService: LayoutService,
    private configService: ConfigService,
    private router: Router
  ) { }

  ngOnInit() {
  }
}
