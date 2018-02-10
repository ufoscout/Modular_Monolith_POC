import {NgModule} from '@angular/core';

import {ExchangeService} from './exchange.service';
import {ExchangeRoutingModule} from './exchange.routing'

import {ConverterComponent} from './converter/converter.component'
import {DisplayComponent} from './display/display.component'

@NgModule({
  imports: [
    ExchangeRoutingModule
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
