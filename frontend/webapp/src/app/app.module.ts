import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import {MainModule} from './main/main.module'
import {MainRootComponent} from './main/root/root.component';

@NgModule({
  declarations: [
  ],
  imports: [
    BrowserModule,
    MainModule
  ],
  providers: [],
  bootstrap: [MainRootComponent]
})
export class AppModule { }
