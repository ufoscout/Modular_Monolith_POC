import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {NgbDropdownModule, NgbCollapseModule} from '@ng-bootstrap/ng-bootstrap';

import {MainRoutingModule} from './main.routing';

import {NavbarComponent} from './navbar/navbar.component';
import {MainRootComponent} from './root/root.component';

@NgModule({
  imports: [
    BrowserModule,
    NgbDropdownModule.forRoot(),
    NgbCollapseModule.forRoot(),
    MainRoutingModule
  ],
  providers: [
  ],
  declarations: [
    NavbarComponent,
    MainRootComponent
  ],
bootstrap: [MainRootComponent]
})
export class MainModule {}
