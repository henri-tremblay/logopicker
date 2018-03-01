import { InMemoryDbService } from 'angular-in-memory-web-api';
import { CloudType, Logo } from "./logo";

export class InMemoryDataService implements InMemoryDbService {
  createDb() {
    const logo = {
      id: 1,
      name: 'Unknown',
      cloud: CloudType.UNKNOWN,
      url: 'http://fossbytes.com/wp-content/uploads/2016/10/localhost-127.0.0.1.jpg'
    };
    return { logo };
  }
}
