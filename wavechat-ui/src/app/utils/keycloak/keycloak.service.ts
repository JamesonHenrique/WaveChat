import { Injectable } from '@angular/core';
import  Keycloak  from 'keycloak-js';
@Injectable({
  providedIn: 'root'
})
export class KeycloakService {
  private _keyclock :Keycloak | undefined;
  constructor() { }
  get keycloak() {
    if(!this._keyclock){
      this._keyclock = new Keycloak({
        url: 'http://localhost:9090',
        realm: 'wavechat',
        clientId: 'wavechat-client'
      });
    }
    return this._keyclock;
  }
  async init(){
   const authenticated:boolean = await this.keycloak.init({onLoad: 'login-required'});
  }
  async login(){
    await this.keycloak.login();
  }
  get userId():string {
    return this.keycloak?.tokenParsed?.sub as string;
  }
  get isTokenValid() {
    return !this.keycloak?.isTokenExpired() ;
  }
  get fullName() {
    return this.keycloak?.tokenParsed?.['name'] as string;
  }
   logout(){
    return  this.keycloak.logout({redirectUri: 'http://localhost:4200'});
  }
  accountMenagement(){
    return this.keycloak.accountManagement();
  }
}
