import { Injectable } from '@angular/core';
import { Headers, Http, URLSearchParams, RequestOptions } from '@angular/http';
import 'rxjs/add/operator/toPromise';

export class Currency {
	id: string;
	name: string;
}

@Injectable()
export class CurrenciesService {

  private currenciesControllerPath: string = '/rest/currency/currencies';

  constructor(private http: Http) {}

  /**
   * Server call to retrieve the full list of available currencies
   */
  getCurrencies(filter: string): Promise<Currency[]> {
    console.log(`Get currencies list`);

    let params: URLSearchParams = new URLSearchParams();
    params.set('_', '' + Date.now());
    params.set('filter', filter);
    
    return this.http.get(this.currenciesControllerPath, {
      search: params
    })
      .toPromise()
      .then(response => {
        return response.json();
      })
      .catch(error => {
        console.log(`Cannot load currencies data`);
      });

  }

}
