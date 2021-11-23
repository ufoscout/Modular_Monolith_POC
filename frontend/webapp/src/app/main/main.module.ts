import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {NgbDropdownModule, NgbCollapseModule} from '@ng-bootstrap/ng-bootstrap';

import {MainRoutingModule} from './main.routing';

import {NavbarComponent} from './navbar/navbar.component';
import {MainRootComponent} from './root/root.component';

import {ExchangeModule} from '../exchangeRate/exchange.module'

@NgModule({
  imports: [
    BrowserModule,
    NgbDropdownModule,
    NgbCollapseModule,
    MainRoutingModule,
    ExchangeModule
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
