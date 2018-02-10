import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import { FormsModule }   from '@angular/forms';
import { HttpModule }    from '@angular/http';

import {ExchangeService} from './exchange.service';
import {ExchangeRoutingModule} from './exchange.routing'

import {CurrencyModule} from '../currency/currency.module';

import {ConverterComponent} from './converter/converter.component'
import {DisplayComponent} from './display/display.component'

@NgModule({
  imports: [
    CommonModule,
    CurrencyModule,
    ExchangeRoutingModule,
    FormsModule,
    HttpModule
  ],
  providers: [
    ExchangeService
  ],
  declarations: [
    ConverterComponent,
    DisplayComponent
  ]
})
export class ExchangeModule {}
