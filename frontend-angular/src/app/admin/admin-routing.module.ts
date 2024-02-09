import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from '../components/home/home.component';
import { PostCategoryComponent } from './components/post-category/post-category.component';
import { adminGuard } from '../guards/admin-guard/admin.guard';
import { PostProductComponent } from './components/post-product/post-product.component';
import { AdminProductosComponent } from './components/admin-productos/admin-productos.component';
import { UpdateProductComponent } from './components/update-product/update-product.component';
import { ViewOrdersComponent } from './components/view-orders/view-orders.component';
import { ViewUsersComponent } from './components/view-users/view-users.component';

const routes: Routes = [
  {path: "home", component: HomeComponent, canActivate: [adminGuard]},
  {path: "categorias", component:PostCategoryComponent, canActivate: [adminGuard]},
  {path: "producto", component:PostProductComponent, canActivate: [adminGuard]},
  {path: "productos", component:AdminProductosComponent, canActivate: [adminGuard]},
  {path: "producto/:id", component:UpdateProductComponent, canActivate: [adminGuard]},
  {path: "ordenes", component:ViewOrdersComponent, canActivate: [adminGuard]},
  {path: "usuarios", component:ViewUsersComponent, canActivate: [adminGuard]}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
