import { Component } from '@angular/core';
import { CustomerService } from '../../user/user-service/customer.service';
import { FormBuilder, FormGroup } from '@angular/forms';
import { NzNotificationService } from 'ng-zorro-antd/notification';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent{


  productos:any = [];
  searchForm!: FormGroup;
  constructor(private customerService: CustomerService,
    private fb:FormBuilder,
    private noti:NzNotificationService){}

  ngOnInit(){
    this.searchForm = this.fb.group({
      title: [null]
    })
    this.getAllProducts();
  }
  getAllProducts(){
    this.productos = [];
    this.customerService.getAllProducts().subscribe((res)=>{
      console.log(res);
      this.productos = [];
      res.forEach((elem:any)=>{
        elem.processedImage = "data:image/jpeg;base64," + elem.returnedImage;
        this.productos.push(elem);
      })
      });
    }

    searchProduct(){
      this.productos = [];
      console.log(this.searchForm.value);
      this.customerService.searchProductById(this.searchForm.get(["title"])?.value).subscribe((res)=>{
        console.log(res);
        res.forEach((elem:any)=>{
        elem.processedImage = "data:image/jpeg;base64," + elem.returnedImage;
        this.productos.push(elem);
      })
      })
    }

    addProductToCart(productId:number) {
      console.log(productId);
      this.customerService.addProductToCart(productId).subscribe((res)=>{
        console.log(res);
        this.noti.success("SUCCESS","Producto agregado al carrito",{nzDuration:5000});
      },error=>{
        this.noti.error("ERROR","Este producto ya existe en el carrito",{nzDuration:5000});
      })
      }

}
