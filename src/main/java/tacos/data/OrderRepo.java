package tacos.data;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import tacos.Order;
import tacos.User;

import java.util.Date;
import java.util.List;

public interface OrderRepo extends CrudRepository<Order,Long> {
    List<Order> findByDeliveryZip(String deliveryZip);
    List<Order> readOrdersByDeliveryZipAndPlaceAtBetween(
            String deliveryZip, Date startDate, Date endDate);

    List<Order> findByUserOrderByPlaceAtDesc(User user, Pageable pageable);

//    @Query("Order o where o.city='Seattle'")
//    List<Order> readOrdersDeliveredInSeattle();
}
