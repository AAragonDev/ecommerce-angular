import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminRoutingModule } from './admin-routing.module';
import { PostCategoryComponent } from './components/post-category/post-category.component';
import { ReactiveFormsModule,FormsModule } from '@angular/forms';
import { PostProductComponent } from './components/post-product/post-product.component';
import { AdminProductosComponent } from './components/admin-productos/admin-productos.component';
import { UpdateProductComponent } from './components/update-product/update-product.component';
import { ViewOrdersComponent } from './components/view-orders/view-orders.component';
import { ViewUsersComponent } from './components/view-users/view-users.component';


@NgModule({
  declarations: [
    PostCategoryComponent,
    PostProductComponent,
    AdminProductosComponent,
    UpdateProductComponent,
    ViewOrdersComponent,
    ViewUsersComponent
  ],
  imports: [
    CommonModule,
    AdminRoutingModule,
    ReactiveFormsModule 
  ]
})
export class AdminModule { }
