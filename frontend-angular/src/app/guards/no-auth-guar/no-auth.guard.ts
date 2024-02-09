import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, CanActivateFn, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { LocalstorageService } from '../../services/storage-service/localstorage.service';

@Injectable({
  providedIn: `root`
})

export class noAuthGuard implements CanActivate{

  constructor(private router: Router){}
  canActivate(
    route: ActivatedRouteSnapshot,
     state: RouterStateSnapshot): boolean{
    if(LocalstorageService.hasToken() && LocalstorageService.isUserLoggedIn()){
      this.router.navigateByUrl("/user/home")
      return false;
    }else if (LocalstorageService.hasToken() && LocalstorageService.isAdminLoggIn()){
      this.router.navigateByUrl("/admin/home");
      return false
    }
    return true;
  }
  
}
