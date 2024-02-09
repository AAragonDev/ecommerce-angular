import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AccederComponent } from './components/auth/acceder/acceder.component';
import { RegistrarComponent } from './components/auth/registrar/registrar.component';
import { HomeComponent } from './components/home/home.component';

const routes: Routes = [
  {path: '', component: HomeComponent},
  {path:'acceder', component: AccederComponent},
  {path:'registrar', component: RegistrarComponent},
  {path:"admin",loadChildren: ()=> import ("./admin/admin.module").then(m=>m.AdminModule)},
  {path:"user",loadChildren: ()=> import ("./user/user.module").then(m=>m.UserModule)},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
