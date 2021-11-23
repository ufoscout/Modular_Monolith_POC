import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import { FormsModule }   from '@angular/forms';
import { HttpClientModule }    from '@angular/common/http';

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
    HttpClientModule
  ],
  providers: [
    ExchangeService
  ],
  declarations: [
    DisplayComponent,
    ConverterComponent    
  ]
})
export class ExchangeModule {}
