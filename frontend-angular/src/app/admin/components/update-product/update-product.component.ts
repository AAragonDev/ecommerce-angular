import { Component } from '@angular/core';
import { AdminService } from '../../admin-service/admin.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NzNotificationService } from 'ng-zorro-antd/notification';

@Component({
  selector: 'app-update-product',
  templateUrl: './update-product.component.html',
  styleUrl: './update-product.component.css'
})
export class UpdateProductComponent {

  id:number = this.activated.snapshot.params["id"];
  updateFormGroup!: FormGroup;
  categories:any;
  imagePreview:String | null = null;
  existingImage: String | null = null;
  selectedFile:any;
  imgChanged=false;

  constructor(private adminService: AdminService,
    private activated:ActivatedRoute,
    private fb: FormBuilder,
    private noti:NzNotificationService,
    private router: Router){}

  ngOnInit(){
    this.updateFormGroup = this.fb.group({
      categoryId: [null,Validators.required],
      name: [null,Validators.required],
      price: [null,Validators.required],
      amount: [null,Validators.required],
      description: [null,Validators.required]
    })
    this.getProductById();
    this.getAllCategories();
  }

  getProductById(){
    this.adminService.getProductById(this.id).subscribe((res)=>{
      console.log(res);
      const product = res;
      this.existingImage = "data:image/jpeg;base64," + product.returnedImage;
      this.updateFormGroup.patchValue(product);
      this.updateFormGroup.get("categoryId")?.setValue(res.categoryId.toString());

    })
  }

  getAllCategories(){
    this.adminService.getAllCategories().subscribe((res) =>{
      this.categories = res;
      console.log(res);
    })
  }

  onFileSelected(event:any){
    this.selectedFile = event.target.files[0];
    this.previewImage();
    this.imgChanged = true;
    this.existingImage = null;
  }

  previewImage(){
    const reader = new FileReader();
    reader.onload = () =>{
      if (typeof reader.result === 'string'){
      this.imagePreview = reader.result;}
    };
    reader.readAsDataURL(this.selectedFile);
  }

  updateProduct(){
    const productDTO: FormData = new FormData();
    if(this.imgChanged){
      productDTO.append('image',this.selectedFile);
    }
    productDTO.append('price',this.updateFormGroup.get('price')!.value);
    productDTO.append('name',this.updateFormGroup.get('name')!.value);
    productDTO.append('description',this.updateFormGroup.get('description')!.value);
    productDTO.append('amount',this.updateFormGroup.get('amount')!.value);
    this.adminService.updateProduct(this.id,this.updateFormGroup.get('categoryId')!.value,productDTO)
    .subscribe((res)=>{
      console.log(res);
      if(res.id != null){
        this.noti.success("SUCCES","Producto actualizado",{nzDuration:5000});
        this.router.navigateByUrl("/admin/productos");
      }else{this.noti.error("ERROR","Algo salió mal",{nzDuration:5000})}
    })
  }

}
