import { NgModule }  from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import {DisplayComponent} from './display/display.component';
import {ConverterComponent} from './converter/converter.component';

const appRoutes: Routes = [
  {
    path: 'exchange-rate-display',
    component: DisplayComponent
  },
  {
    path: 'exchange-rate-convert',
    component: ConverterComponent
  }
];

@NgModule({
  imports: [
    RouterModule.forChild(appRoutes)
  ],
  exports: [
    RouterModule
  ]
})
export class ExchangeRoutingModule { }
