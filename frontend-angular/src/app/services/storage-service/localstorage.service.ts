import { Injectable } from '@angular/core';

const TOKEN = "I_token";
const USERID = "I_user";
const USERROLE = "I_role"
@Injectable({
  providedIn: 'root'
})
export class LocalstorageService {
  
  
  
 

  constructor() { }

  saveUserId(userId:any){
    window.localStorage.removeItem(USERID);
    window.localStorage.setItem(USERID,userId);

  }

  saveUserRole(role:any){
    window.localStorage.removeItem(USERROLE);
    window.localStorage.setItem(USERROLE,role);
  }

  saveToken(token:any){
    window.localStorage.removeItem(TOKEN);
    window.localStorage.setItem(TOKEN,token);
  }

  Token(): string | null{
    return localStorage.getItem(TOKEN);
  }

  static getToken(): string | null{
    const token = localStorage.getItem(TOKEN);
    return token !== null ? token : null;
  }

  static hasToken(): boolean{
    return this.getToken() !== null;
  }

  static isUserLoggedIn(): boolean{
    if(this.getToken()===null){
      return false;
    }
    const role: string = this.getUserRole();
    return role === "USER";

  }

  static getUserRole(): string{
    const role = localStorage.getItem(USERROLE);
    return role !== null ? role : '';
  }

  static getUser(){
    const user = localStorage.getItem(USERID);
    return user !== null ? JSON.parse(user) : null;
  }

  static getUserId():string{
    const user = this.getUser();
    if(user==null){return ''}
    return user.userId;
  }

  static isAdminLoggIn(): boolean {
    if(this.getToken()===null){
      return false;
    }
    const role: string = this.getUserRole();
    return role === "ADMIN";
  }

  static signOut() {
    window.localStorage.removeItem(TOKEN);
    window.localStorage.removeItem(USERID);
    window.localStorage.removeItem(USERROLE);
  }

}
