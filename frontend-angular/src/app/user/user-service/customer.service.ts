import { Injectable } from '@angular/core';
import { apiServer } from '../../apiServer';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LocalstorageService } from '../../services/storage-service/localstorage.service';

const BASIC_URL = apiServer['serverUrl'];

@Injectable({
  providedIn: 'root'
})
export class CustomerService {


  constructor(private http: HttpClient) { }

  getAllProducts(): Observable<any>{
    return this.http.get<[]>(BASIC_URL+"api/customer/products");
  }

  searchProductById(title: String):Observable<any>{
    return this.http.get<[]>(BASIC_URL+"api/customer/product/search/"+title);
  }

  addProductToCart(productId:number):Observable<any>{
    let cartDTO ={
      productId:productId,
      userId:LocalstorageService.getUser()
    }
    return this.http.post<[]>(BASIC_URL+ "api/customer/cart",cartDTO)
  }

  getCartByUserId(): Observable<any>{
    return this.http.get<[]>(BASIC_URL+"api/customer/cart/" + LocalstorageService.getUser());
  }

  decreaseQuantityProduct(productId:number):Observable<any>{
    return this.http.get(BASIC_URL + `api/customer/${LocalstorageService.getUser()}/discount/${productId}`);
  }

  incrementQuantityProduct(productId:number):Observable<any>{
    return this.http.get(BASIC_URL + `api/customer/${LocalstorageService.getUser()}/add/${productId}`);
  
  }

  placeOrder(placeOrderDTO:any): Observable<any>{
    placeOrderDTO.userId= LocalstorageService.getUser();
    return this.http.post<[]>(BASIC_URL+"api/customer/placeOrder",placeOrderDTO);
  }

  getOrdersByUserId(): Observable<any>{
    return this.http.get(BASIC_URL+"api/customer/orders/" + LocalstorageService.getUser());
  }
}
