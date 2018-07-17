export const enum Cloud {
  UNKNOWN = 'UNKNOWN',
  LOCALHOST = 'LOCALHOST',
  HEROKU = 'HEROKU',
  ORACLE = 'ORACLE',
  AWS = 'AWS',
  AZURE = 'AZURE',
  CLOUD_FOUNDRY = 'CLOUD_FOUNDRY',
  GOOGLE = 'GOOGLE'
}

export interface ILogo {
  id?: number;
  cloud?: Cloud;
  name?: string;
  url?: string;
}

export const defaultValue: Readonly<ILogo> = {};
