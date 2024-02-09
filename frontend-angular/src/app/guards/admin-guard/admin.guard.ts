import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, CanActivateFn, Router, RouterStateSnapshot } from '@angular/router';
import { LocalstorageService } from '../../services/storage-service/localstorage.service';
import {NzNotificationService} from 'ng-zorro-antd/notification'
import { NzMessageModule } from 'ng-zorro-antd/message';

@Injectable({
  providedIn: `root`
})

export class adminGuard implements CanActivate{

  constructor(private router: Router,private noti: NzNotificationService){}
  canActivate(
    route: ActivatedRouteSnapshot,
     state: RouterStateSnapshot): boolean{
      if(LocalstorageService.isUserLoggedIn()){
        this.router.navigateByUrl("/user/home");
        this.noti.error("ERROR", "No tienes permisos para acceder a este sitio",
        {nzDuration:5000});
        return false;
      }else if (!LocalstorageService.hasToken()){
        LocalstorageService.signOut();
        this.router.navigateByUrl("/acceder");
        this.noti.error("ERROR", "No has iniciado sesión",{nzDuration: 5000});
        return false;
      }
      return true;
  }
  
}
