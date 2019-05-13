package tacos.data;

import org.springframework.data.repository.CrudRepository;
import tacos.Taco;

public interface TacoRepo
        extends CrudRepository<Taco,Long> {
}
