package edu.miu.orderservice;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class OrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

}

@Table(name = "orders")
@Getter
@Setter
@Entity
class Order{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private int idProduct;

	private int quantity;

	private LocalDateTime orderedAt = LocalDateTime.now();

}

@Repository
interface OrderRepo extends ListCrudRepository<Order,Integer>{

}

interface OrderService{

	Order save(Order order);

}

@Service
@RequiredArgsConstructor
class OrderServiceImpl implements OrderService{

	private final OrderRepo orderRepo;
	private final InventoryClient inventoryClient;

	@Override
	public Order save(Order order) {
		int quantity =inventoryClient.getQuantity(order.getIdProduct());
		if (quantity<order.getQuantity())
			throw new RuntimeException("Quantity is not enough");

		Inventory inventory = new Inventory();
		inventory.setId(order.getIdProduct());
		inventory.setQuantity(quantity-order.getQuantity());
		inventory.setIdProduct(order.getIdProduct());

		inventoryClient.update(inventory);

		return orderRepo.save(order);
	}
}

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
class OrderController{

	private final OrderService orderService;

	@PostMapping
	public Order save(@RequestBody Order order){
		return orderService.save(order);
	}

}

@Component
@FeignClient("inventory-service")
interface InventoryClient {

	@GetMapping("/inventories/quantity")
	int getQuantity(@RequestParam int idProduct);

	@PutMapping("/inventories")
	void update(@RequestBody Inventory inventory);

}

@Getter
@Setter
class Inventory{

	private int id;
	private int idProduct;
	private int quantity;
}
