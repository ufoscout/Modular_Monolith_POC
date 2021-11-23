import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';

import { ExchangeService, ExchangeRate } from '../exchange.service';
import { CurrenciesService, Currency } from '../../currency/currency.service';

/**
 * Exchange Rate Converter component.
 */
@Component({
  selector: 'exchange-rate-converter',
  templateUrl: './converter.component.html'
})
export class ConverterComponent implements OnInit {

  paramTo: string = "";

  currencyFrom!: Currency;
  amountFrom: number = 1;
  amountTo: string = "-";
  exchangeRateTo!: ExchangeRate;
  currencyList: Currency[] = [];
  currencyFromExchangeRates: ExchangeRate[] = [];

  constructor(private exchangeService: ExchangeService,
    private currencyService: CurrenciesService,
    private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.activatedRoute.queryParams.subscribe((params: Params) => {
      let from = params['from'];
      this.paramTo = params['to'];
      console.log(`Converter from ${from} to ${this.paramTo}`);
      this.currencyService.getCurrencies('')
        .then((response: Currency[]): void => {
          console.log(`Currencies loaded`);
          this.currencyList = response;
          this.currencyFrom = this.getCurrencyById(from);
          this.refresh();
        });
    });
  }

  public refresh(): void {
    console.log("Refresh");
    if (this.currencyFrom === null) {
      return;
    }
    this.exchangeService.getExchangeRates([this.currencyFrom.id])
      .then((response: ExchangeRate[]): void => {
        this.currencyFromExchangeRates = response;
        this.exchangeRateTo = this.getExchangeRateByToId(this.paramTo);
        this.convert();
      }, function errorCallback() {
      });
  }

  public getCurrencyById(currencyId: string): Currency {
    console.log(`Get Currency for ${currencyId}`);
    var result = this.currencyList[0];
    for (var i in this.currencyList) {
      if (currencyId === this.currencyList[i].id) {
        result = this.currencyList[i];
      }
    }
    return result;
  }

  public getExchangeRateByToId(currencyId: string) : ExchangeRate {
    console.log(`Get Exchange rate for ${currencyId}`);
    var result = this.currencyFromExchangeRates[0];
    for (var i in this.currencyFromExchangeRates) {
      if (currencyId === this.currencyFromExchangeRates[i].to) {
        result = this.currencyFromExchangeRates[i];
      }
    }
    return result;
  }

  public convert(): void {
    console.log(`Convert`);
    if (!isNaN(this.amountFrom) && !isNaN(this.exchangeRateTo.exchangeRate)) {
      this.amountTo = ''+ (this.amountFrom * this.exchangeRateTo.exchangeRate);
    } else {
      this.amountTo = "-";
    }
  }

}

