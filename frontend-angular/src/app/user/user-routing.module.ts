import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { userGuard } from '../guards/user-guard/user.guard';
import { HomeComponent } from '../components/home/home.component';
import { CartComponent } from './components/cart/cart.component';
import { PlaceOrderComponent } from './components/place-order/place-order.component';
import { MyOrdersComponent } from './components/my-orders/my-orders.component';

const routes: Routes = [
  {path: "home", component: HomeComponent, canActivate: [userGuard]},
  {path: "cart", component: CartComponent, canActivate: [userGuard]},
  {path: "place-order", component: PlaceOrderComponent, canActivate: [userGuard]},
  {path: "my-orders", component: MyOrdersComponent, canActivate: [userGuard]},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserRoutingModule { }
