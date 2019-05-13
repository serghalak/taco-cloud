package tacos.web;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import tacos.Ingredient;
import tacos.data.IngredientRepo;

@Component
public class IngredientByIdConverter implements
        Converter<String, Ingredient> {

    //private final IngredientRepository ingredientRepository;
    private final IngredientRepo ingredientRepository;

    public IngredientByIdConverter(IngredientRepo ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public Ingredient convert(String id) {
        return ingredientRepository.findById(id).get();
    }
}
