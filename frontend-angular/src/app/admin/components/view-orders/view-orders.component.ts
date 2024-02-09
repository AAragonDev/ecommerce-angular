import { Component } from '@angular/core';
import { AdminService } from '../../admin-service/admin.service';

@Component({
  selector: 'app-view-orders',
  templateUrl: './view-orders.component.html',
  styleUrl: './view-orders.component.css'
})
export class ViewOrdersComponent {

  orders:any;
  constructor(private adminServices:AdminService){}

  ngOnInit(){
    this.getAllOrders();
  }

  getAllOrders(){
    this.adminServices.getAllOrders().subscribe((res)=>{
      console.log(res);
      this.orders = res;
    })
  }

}
