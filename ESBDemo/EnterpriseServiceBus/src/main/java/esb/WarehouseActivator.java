
package esb;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WarehouseActivator {

	@Autowired
	RestTemplate restTemplate;

	public Order checkStock(Order order) {
		System.out.println("WarehouseService: checking order "+order.toString());
		restTemplate.postForLocation("http://localhost:8082/orders", order);
		return order;
	}
}
