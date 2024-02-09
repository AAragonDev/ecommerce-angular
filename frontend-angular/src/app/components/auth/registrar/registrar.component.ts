import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../../services/auth-service/auth.service';
import { Router } from '@angular/router';
import { NzNotificationService } from 'ng-zorro-antd/notification';

@Component({
  selector: 'app-registrar',
  templateUrl: './registrar.component.html',
  styleUrl: './registrar.component.css'
})
export class RegistrarComponent {

  validateForm!: FormGroup;
  confirmationValidator = (control: FormControl): {[s:string]: boolean} => {
    if (!control.value){
      return {required:true}
    }else if (control.value !== this.validateForm.controls['password'].value){
      return {confirm:true,error:true}
    }
    return{}
  }

  constructor(private fb: FormBuilder,private authService: AuthService,
    private router:Router,
    private noti: NzNotificationService){}

  ngOnInit(){
    this.validateForm = this.fb.group({
      name: [null,[Validators.required]],
      email:[null,[Validators.required,Validators.email]],
      password:[null,[Validators.required]],
      confirmPassword:[null,[Validators.required,this.confirmationValidator]],
    })
  }

  register(){
    console.log(this.validateForm.value);
    this.authService.register(this.validateForm.value).subscribe((res)=>{
      this.router.navigateByUrl("/acceder");
      this.noti.success("SUCCESS", "Usuario creado",{nzDuration: 5000})
      console.log(res)
    },error=>{
      this.noti.error("ERROR", "Credenciales incorrectas",{nzDuration: 5000})
    })
  }

}
