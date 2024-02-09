import { Component } from '@angular/core';
import { AdminService } from '../../admin-service/admin.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-post-category',
  templateUrl: './post-category.component.html',
  styleUrl: './post-category.component.css'
})
export class PostCategoryComponent {

  categoryForm!: FormGroup;

  constructor(private adminService: AdminService,
    private fb: FormBuilder){}

  ngOnInit(){
    this.categoryForm = this.fb.group({
      name: [null,[Validators.required]],
      description: [null,[Validators.required]]
    })
  }

  postCategory(){
    this.adminService.postCategory(this.categoryForm.value).subscribe((res)=>{
      console.log(res);
    });
  }
}
