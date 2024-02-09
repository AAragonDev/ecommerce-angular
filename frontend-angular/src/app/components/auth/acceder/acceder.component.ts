import { Component } from '@angular/core';
import { AuthService } from '../../../services/auth-service/auth.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LocalstorageService } from '../../../services/storage-service/localstorage.service';
import { Router } from '@angular/router';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { NzMessageModule } from 'ng-zorro-antd/message'

@Component({
  selector: 'app-acceder',
  templateUrl: './acceder.component.html',
  styleUrl: './acceder.component.css'
})
export class AccederComponent {

  validateForm!: FormGroup;

  constructor(private authService: AuthService,
     private fb: FormBuilder,
     private router: Router,
     private noti: NzNotificationService){}

  ngOnInit(){
    this.validateForm = this.fb.group({
      username:[null,[Validators.required]],
      password:[null,[Validators.required]],
    })
    
  }

  login(){
    this.authService.login(this.validateForm.get(['username'])!.value,this.validateForm.get(['password'])!.value).subscribe((res) =>{
      if(LocalstorageService.isAdminLoggIn()){
        
        this.router.navigateByUrl("/admin/home");
      }else if(LocalstorageService.isUserLoggedIn()){
        console.log("boolean:");
        this.router.navigateByUrl("/user/home")
      }
    }, error =>{
      console.log(error);
      if(error.status == 406){
        this.noti.error("ERROR", "Usuario no registrado",{nzDuration: 5000})
      }else {
        this.noti.error("ERROR","Credenciales incorrectas",{nzDuration: 5000})
      }
    })
  }
}

