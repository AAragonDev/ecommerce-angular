import { Component } from '@angular/core';
import { CustomerService } from '../../user-service/customer.service';
import { NzNotificationService } from 'ng-zorro-antd/notification';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.css'
})
export class CartComponent {

  totalAmount: number | undefined;
  cartProducts:any = [];

  constructor(private customerService: CustomerService,
    private noti: NzNotificationService){}
  ngOnInit(){
    this.getCart();
  }

  getCart(){
    this.customerService.getCartByUserId().subscribe((res)=>{
      console.log(res);
      this.cartProducts = res.cartItemsDTO;
      this.totalAmount = res.amount;
    })
  }

  minusProduct(productId:number){
    this.customerService.decreaseQuantityProduct(productId).subscribe((res)=>{
      console.log(res);
      this.noti.success("SUCCESS", "decrementado" , {nzDuration:5000});
      this.getCart();
    })
  }

  plusProduct(productId:number){
    this.customerService.incrementQuantityProduct(productId).subscribe((res)=>{
      console.log(res);
      this.noti.success("SUCCESS", "incrementado" , {nzDuration:5000});
      this.getCart();
    })
  }
}
