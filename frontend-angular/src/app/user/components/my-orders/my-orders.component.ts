import { Component } from '@angular/core';
import { CustomerService } from '../../user-service/customer.service';

@Component({
  selector: 'app-my-orders',
  templateUrl: './my-orders.component.html',
  styleUrl: './my-orders.component.css'
})
export class MyOrdersComponent {

  myorders:any;
  constructor(private customerServices:CustomerService){}

  ngOnInit(){
    this.getMyOrders();
  }

  getMyOrders(){
    this.customerServices.getOrdersByUserId().subscribe((res)=>{
      console.log(res);
      this.myorders=res;
    })
  }

}
