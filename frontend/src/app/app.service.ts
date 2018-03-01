import { Observable } from 'rxjs/Observable';
import {catchError} from "rxjs/operators";

import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import {CloudType, Logo} from './logo';
import {of} from "rxjs/observable/of";

@Injectable()
export class AppService {

  private logoUrl = 'api/logo';

  constructor(private http: HttpClient) { }

  getLogo(): Observable<Logo> {
    return this.http.get<Logo>(this.logoUrl)
      .pipe(
        catchError(this.handleError('getLogo', this.failedLogo()))
      );
  }

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error); // log to console instead

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  /**
   * Return a logo showing there was a failure.
   *
   * @returns {Logo}
   */
  private failedLogo() : Logo {
    return {
      id: 1,
      name: 'the Storm!',
      cloud: CloudType.UNKNOWN,
      url: 'https://openclipart.org/image/2400px/svg_to_png/22010/nicubunu-Weather-Symbols-Storm.png'
    };
  }

}
