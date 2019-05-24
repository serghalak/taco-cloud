package tacos.data;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import tacos.Taco;

import java.util.List;

public interface TacoRepo
       extends PagingAndSortingRepository<Taco, Long> {

}
