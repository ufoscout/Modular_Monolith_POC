import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';

export type Currency = {
	id: string;
	name: string;
}

@Injectable()
export class CurrenciesService {

  private currenciesControllerPath: string = '/rest/currency/currencies';

  constructor(private http: HttpClient) {}

  /**
   * Server call to retrieve the full list of available currencies
   */
  async getCurrencies(filter: string): Promise<Currency[]> {
    console.log(`Get currencies list`);

    let params = new HttpParams();
    params = params.append('_', '' + Date.now());
    params = params.append('filter', filter);
    
    let request = this.http.get<Currency[]>(this.currenciesControllerPath, {
      params
    });

    return await firstValueFrom(request);

  }

}
