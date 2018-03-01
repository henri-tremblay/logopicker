import { Component } from '@angular/core';
import {CloudType, Logo} from "./logo";
import { AppService } from "./app.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  logo: Logo = {
    id: 1,
    name: 'Loading',
    cloud: CloudType.UNKNOWN,
    url: '/assets/unknown.png'
  };

  constructor(private appService: AppService) { }

  ngOnInit() {
    this.getLogo();
  }

  getLogo(): void {
    this.appService.getServer()
       .subscribe(serverUrl => {
         this.appService.getLogo(serverUrl + '/api/logos/current')
           .subscribe(logo => this.logo = logo);
       });
  }
}
