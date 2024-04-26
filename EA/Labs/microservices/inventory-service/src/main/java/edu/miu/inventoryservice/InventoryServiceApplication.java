package edu.miu.inventoryservice;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;


@EnableDiscoveryClient
@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

}

@Entity
@Getter
@Setter
class Inventory{

	@Id
	private int id;

	private int idProduct;

	private int quantity;

}

@Repository
interface InventoryRepo extends ListCrudRepository<Inventory,Integer> {
		Inventory findByIdProduct(int idProduct);
}

interface InventoryService{

	int getQuantity(int idProduct);

	void update(Inventory inventory);

}

@RequiredArgsConstructor
@Service
class InventoryServiceImpl implements InventoryService{

	private final InventoryRepo inventoryRepo;

	@Override
	public int getQuantity(int idProduct) {
		return inventoryRepo.findByIdProduct(idProduct).getQuantity();
	}

	@Override
	public void update(Inventory inventory) {
		inventoryRepo.save(inventory);
	}
}

@RestController
@RequiredArgsConstructor
@RequestMapping("/inventories")
class InventoryController {

	private final InventoryService inventoryService;

	@GetMapping("/quantity")
	public int getQuantity(@RequestParam int idProduct) {
		return inventoryService.getQuantity(idProduct);
	}

	@PutMapping
	public void update(@RequestBody Inventory inventory) {
		 inventoryService.update(inventory);
	}

}
