package tacos.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import tacos.Order;
import tacos.Taco;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcOrderRepository implements OrderRepository {

    private SimpleJdbcInsert orderInserter;
    private SimpleJdbcInsert orderTacoInserter;
    private ObjectMapper objectMapper;

    public JdbcOrderRepository(JdbcTemplate jdbcTemplate) {
        this.orderInserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("taco_order")
                .usingGeneratedKeyColumns("id");
        this.orderTacoInserter=new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("taco_order_tacos");
        this.objectMapper=new ObjectMapper();
    }

    @Override
    public Order save(Order order) {
        order.setPlaceAt(new Date());
        long orderId=saveOrderDetails(order);
        order.setId(orderId);
        List<Taco>tacos=order.getTacos();
        for (Taco taco:tacos) {
            saveTacoToOrder(taco,orderId);
        }
        return null;
    }

    private void saveTacoToOrder(Taco taco, long orderId) {
        Map<String,Object>values=new HashMap<>();
        values.put("tacoOrder",orderId);
        values.put("taco",taco.getId());
        orderTacoInserter.execute(values);
    }

    private long saveOrderDetails(Order order) {
        Map<String,Object> values=objectMapper.convertValue(order,Map.class);
        values.put("placeAt",order.getPlaceAt());
        long orderId=orderInserter
                .executeAndReturnKey(values)
                .longValue();
        return orderId;
    }
}
