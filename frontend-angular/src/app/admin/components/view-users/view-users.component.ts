import { Component } from '@angular/core';
import { AdminService } from '../../admin-service/admin.service';

@Component({
  selector: 'app-view-users',
  templateUrl: './view-users.component.html',
  styleUrl: './view-users.component.css'
})
export class ViewUsersComponent {

  users:any;
  constructor(private adminServices:AdminService){}

  ngOnInit(){
    this.getAllOrders();
  }

  getAllOrders(){
    this.adminServices.getAllUsers().subscribe((res)=>{
      console.log(res);
      this.users = res;
    })
  }
}
