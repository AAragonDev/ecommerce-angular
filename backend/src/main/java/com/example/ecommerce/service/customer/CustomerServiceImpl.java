package com.example.ecommerce.service.customer;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.ecommerce.dto.CartItemsDTO;
import com.example.ecommerce.dto.OrderDTO;
import com.example.ecommerce.dto.PlaceOrderDTO;
import com.example.ecommerce.dto.ProductDTO;
import com.example.ecommerce.entities.CartItems;
import com.example.ecommerce.entities.Order;
import com.example.ecommerce.entities.Product;
import com.example.ecommerce.entities.User;
import com.example.ecommerce.enums.OrderStatus;
import com.example.ecommerce.repository.CartItemsRepository;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService{
	
	private final ProductRepository productRepository;
	
	private final OrderRepository orderRepository;
	
	private final CartItemsRepository cartItemsRepository;
	
	private final UserRepository userRepository;

	@Override
	public List<ProductDTO> getAllProducts() {
		
		return productRepository.findAll().stream().map(Product::getProductDTO).collect(Collectors.toList());
	}

	@Override
	public List<ProductDTO> searchProductByTitle(String title) {
		// TODO Auto-generated method stub
		return productRepository.findAllByNameContaining(title).stream().map(Product::getProductDTO).collect(Collectors.toList());
	}

	@Override
	public ResponseEntity<?> addProductCart(CartItemsDTO cartItemsDTO) {
		Order pendingOrder = orderRepository.findByUserIdAndOrderStatus(cartItemsDTO.getUserId(),OrderStatus.PENDING);
		Optional<CartItems> optionalCartItems = cartItemsRepository.findByUserIdAndProductIdAndOrderId(
				cartItemsDTO.getUserId(),
				cartItemsDTO.getProductId(),
				pendingOrder.getId());
		if(optionalCartItems.isPresent()) {
			CartItemsDTO productExistInCart = new CartItemsDTO();
			productExistInCart.setProductId(null);
			return ResponseEntity.status(HttpStatus.CONFLICT).body(productExistInCart);
		}else {
			Optional<Product> optionalProduct = productRepository.findById(cartItemsDTO.getProductId());
			Optional<User> optionalUser = userRepository.findById(cartItemsDTO.getUserId());
			if(optionalProduct.isPresent() && optionalUser.isPresent()) {
				Product product = optionalProduct.get();
				CartItems cartItem= new CartItems();
				cartItem.setProduct(product);
				cartItem.setUser(optionalUser.get());
				cartItem.setQuantity(1L);
				cartItem.setOrder(pendingOrder);
				cartItem.setPrice(product.getPrice());
				CartItems updateCart = cartItemsRepository.save(cartItem);
				pendingOrder.setPrice(pendingOrder.getPrice() + cartItem.getPrice());
				pendingOrder.getCartItems().add(cartItem);
				orderRepository.save(pendingOrder);
				CartItemsDTO updateCartItemDTO = new CartItemsDTO();
				updateCartItemDTO.setId(cartItem.getId());
				return ResponseEntity.status(HttpStatus.CREATED).body(updateCartItemDTO);
			}else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario o producto no encontrado");
			}
		}
	}

	@Override
	public OrderDTO getCartByUserid(Long userid) {
		Order pendingOrder = orderRepository.findByUserIdAndOrderStatus(userid, OrderStatus.PENDING);
		List<CartItemsDTO> cartItemsDTO = pendingOrder.getCartItems().stream().map(CartItems::getCartItemDTO).toList();
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setCartItemsDTO(cartItemsDTO);
		orderDTO.setAmount(pendingOrder.getPrice());
		orderDTO.setId(pendingOrder.getId());
		orderDTO.setOrderStatus(pendingOrder.getOrderStatus());
		return orderDTO;
	}

	@Override
	public OrderDTO addMinusOnProduct(Long userId, Long productId) {
		Order pendingOrder = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.PENDING);
		Optional<Product> optionalProduct = productRepository.findById(productId);
		Optional<CartItems> optionalCartItems = cartItemsRepository.findByUserIdAndProductIdAndOrderId(userId, productId, pendingOrder.getId());
		if(optionalCartItems.isPresent() && optionalProduct.isPresent()) {
			CartItems cartItems = optionalCartItems.get();
			if(optionalCartItems.get().getQuantity()>1) {
				cartItems.setQuantity(optionalCartItems.get().getQuantity() - 1);
				pendingOrder.setPrice(pendingOrder.getPrice() - optionalProduct.get().getPrice());
			}else {
				cartItems.setQuantity(optionalCartItems.get().getQuantity());
			}
			
			cartItemsRepository.save(cartItems);
			orderRepository.save(pendingOrder);
			return pendingOrder.getOrderDTO();
		}
		return null;
	}

	@Override
	public OrderDTO addPlusOnProduct(Long userId, Long productId) {
		Order pendingOrder = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.PENDING);
		Optional<Product> optionalProduct = productRepository.findById(productId);
		Optional<CartItems> optionalCartItems = cartItemsRepository.findByUserIdAndProductIdAndOrderId(userId, productId, pendingOrder.getId());
		if(optionalCartItems.isPresent() && optionalProduct.isPresent()) {
			CartItems cartItems = optionalCartItems.get();
			cartItems.setQuantity(optionalCartItems.get().getQuantity() + 1);
			pendingOrder.setPrice(pendingOrder.getPrice() + optionalProduct.get().getPrice());
			cartItemsRepository.save(cartItems);
			orderRepository.save(pendingOrder);
			return pendingOrder.getOrderDTO();
		}
		return null;
	}

	@Override
	public OrderDTO placeOrder(PlaceOrderDTO placeOrderDTO) {
		Order existOrder = orderRepository.findByUserIdAndOrderStatus(placeOrderDTO.getUserId(), OrderStatus.PENDING);
		Optional<User> optionalUser = userRepository.findById(placeOrderDTO.getUserId());
		if(optionalUser.isPresent()) {
			existOrder.setOrderStatus(OrderStatus.SUBMITTED);
			existOrder.setAddress(placeOrderDTO.getAddress());
			existOrder.setDate(new Date());
			existOrder.setPaymentType(placeOrderDTO.getPayment());
			existOrder.setDescription(placeOrderDTO.getOrderDescription());
			existOrder.setPrice(existOrder.getPrice());
			orderRepository.save(existOrder);
			Order order = new Order();
			order.setOrderStatus(OrderStatus.PENDING);
			order.setUser(optionalUser.get());
			order.setPrice(0);
			orderRepository.save(order);
			return order.getOrderDTO();	
			
		}
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderDTO> getOrdersByUserId(Long userId) {
		// TODO Auto-generated method stub
		return orderRepository.findAllByUserIdAndOrderStatus(userId, OrderStatus.SUBMITTED).stream().map(Order::getOrderDTO).collect(Collectors.toList());
	}

}
