import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { apiServer } from '../../apiServer';
import { Observable, Subject, tap } from 'rxjs';
import { LocalstorageService } from '../../services/storage-service/localstorage.service';

const BASIC_URL = apiServer['serverUrl'];

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private http: HttpClient) { }


  postCategory(categoryDTO: any): Observable<any>{
    return this.http.post<[]>(BASIC_URL+"api/admin/category",categoryDTO);

  }

  getAllCategories(): Observable<any>{
    return this.http.get<[]>(BASIC_URL+"api/admin/categories");
  }

  postProduct(categoryId:number,productDTO: any): Observable<any>{
    return this.http.post<[]>(BASIC_URL+"api/admin/product/" + categoryId,productDTO);

  }

  getAllProducts(): Observable<any>{
    return this.http.get<[]>(BASIC_URL+"api/admin/products");
  }

  deleteProduct(id: number): Observable<any>{
    return this.http.delete<[]>(BASIC_URL+"api/admin/product/"+id);
  }

  getProductById(id: number): Observable<any>{
    return this.http.get<[]>(BASIC_URL+"api/admin/product/"+id);
  }

  updateProduct(productId:number,categoryId:number,productDTO:any): Observable<any>{
    return this.http.put<[]>(BASIC_URL+ `api/admin/${categoryId}/product/${productId}`,productDTO);
  }
  createAuthorizationHeader(): HttpHeaders{
    let authHeaders: HttpHeaders = new HttpHeaders();
    return authHeaders.set(
      `Authorization`,
      `Bearer ${LocalstorageService.getToken()}`
    )
  }

  getAllOrders(): Observable<any>{
    return this.http.get<[]>(BASIC_URL+"api/admin/orders");
  }

  getAllUsers(): Observable<any>{
    return this.http.get<[]>(BASIC_URL+"api/admin/users");
  }
  
}
