import { HttpClient, HttpResponse,HttpHeaderResponse, HttpHeaders} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map, tap } from 'rxjs';
import { apiServer } from '../../apiServer';
import { LocalstorageService } from '../storage-service/localstorage.service';

const BASIC_URL = apiServer["serverUrl"];
const AUTH_HEADER = "Authorization";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http:HttpClient, private storageService: LocalstorageService) { }

  register(signupDTO: any): Observable<any>{
    return this.http.post(BASIC_URL+"sign-up",signupDTO);
  }

  login(username: string,password: string): Observable<any>{
    return this.http.post(BASIC_URL+"authenticate",{username,password},
    {observe: 'response'}).pipe(
      tap(_=> this.log("User Authentication")),
      map((res:HttpResponse<any>)=>{
        this.storageService.saveUserId(res.body.userId);
        this.storageService.saveUserRole(res.body.role);
        const tokenLenght = res.headers.get('Authorization')?.length;
        const bearerToken = res.headers.get(AUTH_HEADER)?.substring(7,tokenLenght);
        this.storageService.saveToken(bearerToken);
        return res;
      })
    );
    
  }

  log(message: string): void{
    console.log(`User auth Service: ${message}`);
  }

}
