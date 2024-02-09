import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CustomerService } from '../../user-service/customer.service';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { Router } from '@angular/router';

@Component({
  selector: 'app-place-order',
  templateUrl: './place-order.component.html',
  styleUrl: './place-order.component.css'
})
export class PlaceOrderComponent {

  placeOrderForm!: FormGroup;
  payments = ["Efectivo","Tarjeta de credito","Tarjeta de debito"];

  constructor(private fb: FormBuilder,
    private customerService:CustomerService,
    private noti:NzNotificationService,
    private router: Router){}

  ngOnInit(){
    this.placeOrderForm = this.fb.group({
      address: [null,Validators.required],
      payment: [null,Validators.required],
      orderDescription: [null,Validators.required]
    })
  }

  placeOrder(){
    console.log(this.placeOrderForm.value);
    this.customerService.placeOrder(this.placeOrderForm.value).subscribe((res)=>{
      console.log(res);
      this.noti.success("SUCCESS","Datos enviados correctamente",{nzDuration:5000});
      this.router.navigateByUrl("/user/home")

    })
  }
}
