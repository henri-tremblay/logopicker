export class Logo {
  id: number;
  name: string;
  cloud: CloudType;
  url: string;
}

export enum CloudType {
  LOCALHOST, HEROKU, ORACLE, AWS, AZURE, CLOUD_FOUNDRY, UNKNOWN
}
