import { Component } from '@angular/core';
import { AdminModule } from '../../admin.module';
import { AdminService } from '../../admin-service/admin.service';
import { RouterTestingHarness } from '@angular/router/testing';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { Router } from '@angular/router';

@Component({
  selector: 'app-post-product',
  templateUrl: './post-product.component.html',
  styleUrl: './post-product.component.css'
})
export class PostProductComponent {


  categories:any;

  postProductForm!: FormGroup;

  selectedFile!: File;
  constructor(private adminService: AdminService,
    private fb: FormBuilder,
    private noti:NzNotificationService,
    private router:Router){}



  ngOnInit(){
    this.postProductForm = this.fb.group({
      categoryId: [null,Validators.required],
      name: [null,Validators.required],
      price: [null,Validators.required],
      amount: [null,Validators.required],
      description: [null,Validators.required]
    })
    this.getAllCategories();
  }

  getAllCategories(){
    this.adminService.getAllCategories().subscribe((res) =>{
      this.categories = res;
      console.log(res);
    })
  }
  onFileSelected(event: any){
    this.selectedFile = event.target.files[0];
  }

  postProduct() {
    if(!this.selectedFile){
      console.error("no se ha seleccionado ningun archivo");
      return;
    }
    console.log(this.selectedFile);
    const productDTO:FormData = new FormData();
    productDTO.append("name",this.postProductForm.get(["name"])!.value);
    productDTO.append("price",this.postProductForm.get(["price"])!.value);
    productDTO.append("amount",this.postProductForm.get(["amount"])!.value);
    productDTO.append("description",this.postProductForm.get(["description"])!.value);
    productDTO.append("image",this.selectedFile);

    this.adminService.postProduct(this.postProductForm.get(['categoryId'])!.value, productDTO).subscribe((res)=>{
      console.log(res);
      if(res.id != null){
        this.noti.success(
          'SUCCESS',
          "Product posted succesfully",
          {nzDuration: 5000}
        )
        this.router.navigateByUrl("/admin/productos")
      }else{
        this.noti.error(
          "ERROR",
          `$(res.message)`,
          {nzDuration:5000}
        )
      }
    })
    }
}
