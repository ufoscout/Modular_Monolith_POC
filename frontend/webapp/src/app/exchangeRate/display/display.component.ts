import {Component, OnInit} from '@angular/core';
import { ExchangeRate, ExchangeService } from '../exchange.service';
import { CurrenciesService, Currency } from '../../currency/currency.service';

/**
 * Exchange Rate Display component.
 */
@Component({
  selector: 'exchange-rate-display',
  templateUrl: './display.component.html'
})
export class DisplayComponent implements OnInit {

  public filter: string = '';
  public currencyList: Currency[] = [];
  public filteredCurrencyList: Currency[] = [];
  exchangeRates: ExchangeRate[] = [];

  constructor(private exchangeService: ExchangeService, 
              private currencyService: CurrenciesService) {}

  ngOnInit(): void {
    this.refresh();
  }

  public refresh(): void {
    console.log('Refresh. Filter is ' + this.filter);
    this.currencyService.getCurrencies(this.filter)
    .then((response): void => {
      this.filteredCurrencyList = response;
      if(this.currencyList.length === 0){
        this.currencyList = this.filteredCurrencyList;
      }
      var currencyIDs: string[] = [];
      for (var i in this.filteredCurrencyList) {
        currencyIDs.push(this.filteredCurrencyList[i].id);
      }
      this.refreshExchangeRates(currencyIDs);
    });
  }

  public getExchangeRate (currencyFrom: string, currencyTo: string): string {
    for (var i in this.exchangeRates) {
      var rate = this.exchangeRates[i];
      if ( (currencyFrom === rate.from) && (currencyTo === rate.to) ) {
        return "" + rate.exchangeRate;
      }
    }
    return "-";
  };

  refreshExchangeRates(currencyIDs: string[]) {
    this.exchangeService.getExchangeRates(currencyIDs)
      .then((response) : void => {
        this.exchangeRates = response;
      });
  }

}

