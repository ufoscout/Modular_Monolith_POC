import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';

export type ExchangeRate = {
	from: string;
	to: string;
    exchangeRate: number;
}

@Injectable()
export class ExchangeService {

  private controllerPath: string = '/rest/exchangeRate';

  constructor(private http: HttpClient) {}

  /**
   * Server call to retrieve the exchange rates
   */
  async getExchangeRates(currencies: string[]): Promise<ExchangeRate[]> {
    console.log(`Get exchange rates`);

    let params = new HttpParams();
    params = params.append('_', '' + Date.now());
    params = params.append('currencies', currencies.join(','));
    
    let request = this.http.get<ExchangeRate[]>(this.controllerPath + "/rates", {
      params
    });
    
    return await firstValueFrom(request);

  }

   /**
   * Server call to convert between two currencies
   */
  async convert(from: string, to: string, amount: number): Promise<number> {
    console.log(`Convert from ${from} to ${to} amount ${amount}`);

    let params = new HttpParams();
    params = params.append('_', '' + Date.now());
    
    let request = this.http.get<number>(this.controllerPath + `/convert/${from}/${to}/${amount}`, {
        params
    });

    return await firstValueFrom(request);

  }

}
