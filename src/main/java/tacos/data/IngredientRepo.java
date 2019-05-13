package tacos.data;

import org.springframework.data.repository.CrudRepository;
import tacos.Ingredient;

public interface IngredientRepo extends
        CrudRepository<Ingredient, String> {
}
