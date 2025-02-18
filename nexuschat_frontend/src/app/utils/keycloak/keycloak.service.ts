import { Injectable } from '@angular/core';
import Keycloak from 'keycloak-js';

@Injectable({
  providedIn: 'root'
})
export class KeycloakService {

  private _keycloak: Keycloak | undefined;

  constructor() { }

  get keycloak(): Keycloak{
    if (!this._keycloak) {
      this._keycloak = new Keycloak({
        url: 'http://localhost:8585',
        realm: 'nexus_chat',
        clientId: 'nexus_chat_app'
      });
    }
  return this._keycloak;
  }

  async init(): Promise<void> {
    const authenticated: boolean = await this.keycloak.init({
      onLoad: 'login-required'
    });
  }

  async login(): Promise<void> {
    await this.keycloak.login();
  }

  get userId(): string {
    return this.keycloak?.tokenParsed?.sub as string;
  }

  get isTokenValid(): boolean {
    return !this.keycloak.isTokenExpired();
  }

  get fullName(): string {
    return this.keycloak.tokenParsed?.['name'] as string;
  }

  logout(): Promise<void> {
    return this.keycloak.login({redirectUri: 'http://localhost:4200'});
  }

  accountManagement(): Promise<void> {
    return this.keycloak.accountManagement();
  }

}
