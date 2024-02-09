import { Component } from '@angular/core';
import { AdminService } from '../../admin-service/admin.service';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { Subject, Subscription } from 'rxjs';

@Component({
  selector: 'app-admin-productos',
  templateUrl: './admin-productos.component.html',
  styleUrl: './admin-productos.component.css'
})


export class AdminProductosComponent {

  productos:any=[];

  constructor(private adminService: AdminService,
    private noti:NzNotificationService){}

  ngOnInit(){
     this.getAllProducts();
  }

  getAllProducts(){
    this.adminService.getAllProducts().subscribe((res)=>{
      console.log(res);
      this.productos = [];
      res.forEach((elem:any)=>{
        elem.processedImage = "data:image/jpeg;base64," + elem.returnedImage;
        this.productos.push(elem);
      })
      });
    }

    deleteProduct(id: number){
      console.log(id);
      this.adminService.deleteProduct(id).subscribe((res)=>{
        console.log(res);
        this.noti.success("SUCCESS", "Product deleted successfully",{nzDuration:5000})
        this.getAllProducts();
      })
    }
  }

