import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LocalstorageService } from '../storage-service/localstorage.service';

@Injectable({
  providedIn: 'root'
})
export class JwtInterceptorService implements HttpInterceptor{

  constructor(private storageService: LocalstorageService) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let token = LocalstorageService.getToken();
    if (token != null){
      req = req.clone({
        setHeaders: {
          'Authorization': `Bearer ${token}`,
        },
      })
    }
    return next.handle(req);
  }
}
