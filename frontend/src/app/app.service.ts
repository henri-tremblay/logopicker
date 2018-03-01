import { Observable } from 'rxjs/Observable';
import { catchError, map, tap } from "rxjs/operators";

import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { CloudType, Logo } from './logo';
import { of } from "rxjs/observable/of";

import {Application, Eureka} from "./application";

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
    'Accept': 'application/json'
  })
};

@Injectable()
export class AppService {

  private registryUrl = 'http://localhost:8761';
  // private registryUrl = 'https://henri-jhipster-registry.herokuapp.com';

  constructor(private http: HttpClient) { }

  getServer(): Observable<string> {
    return this.http.get<Eureka>(this.registryUrl + '/api/eureka/applications', httpOptions)
      .pipe(
        map(eureka => eureka.applications[0].instances[0].homePageUrl)
      );
  }

  getLogo(logoUrl : string): Observable<Logo> {
    return this.http.get<Logo>(logoUrl)
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
      url: '/assets/storm.png'
    };
  }

}
