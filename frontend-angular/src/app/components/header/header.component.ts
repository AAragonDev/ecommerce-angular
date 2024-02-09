import { Component } from '@angular/core';
import { LocalstorageService } from '../../services/storage-service/localstorage.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {
  isAdminLoggedIn: boolean = LocalstorageService.isAdminLoggIn();
  isUserLoggedIn: boolean = LocalstorageService.isUserLoggedIn();

  constructor(private router: Router){}

  ngOnInit():void{
    this.router.events.subscribe(event=>{
      if(event.constructor.name == "NavigationEnd"){
        this.isAdminLoggedIn = LocalstorageService.isAdminLoggIn();
        this.isUserLoggedIn = LocalstorageService.isUserLoggedIn();
      }
    })

  }


  logout(){
    LocalstorageService.signOut();
    this.router.navigateByUrl("/acceder");
  }

}
