import { Injectable } from '@angular/core';
import { Headers, Http, URLSearchParams, RequestOptions } from '@angular/http';
import 'rxjs/add/operator/toPromise';

export class ExchangeRate {
	from: string;
	to: string;
    exchangeRate: number;
}

@Injectable()
export class ExchangeService {

  private controllerPath: string = '/rest/exchangeRate';

  constructor(private http: Http) {}

  /**
   * Server call to retrieve the exchange rates
   */
  getExchangeRates(): Promise<ExchangeRate[]> {
    console.log(`Get exchange rates`);

    let params: URLSearchParams = new URLSearchParams();
    params.set('_', '' + Date.now());
    
    return this.http.get(this.controllerPath + "/rates", {
        search: params
      })
      .toPromise()
      .then(response => {
        return response.json();
      })
      .catch(error => {
        console.log(`Cannot load exchange rates`);
      });

  }

   /**
   * Server call to convert between two currencies
   */
  convert(from: string, to: string, amount: number): Promise<number> {
    console.log(`Convert from ${from} to ${to} amount ${amount}`);

    let params: URLSearchParams = new URLSearchParams();
    params.set('_', '' + Date.now());
    
    return this.http.get(this.controllerPath + `/convert/${from}/${to}/${amount}`, {
        search: params
      })
      .toPromise()
      .then(response => {
        return response.json();
      })
      .catch(error => {
        console.log(`Cannot convert the data`);
      });

  }

}
