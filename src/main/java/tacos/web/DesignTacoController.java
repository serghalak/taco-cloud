package tacos.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import tacos.Ingredient;
import tacos.Taco;
import tacos.data.IngredientRepository;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/design")
@Controller
@SessionAttributes("order")
public class DesignTacoController {

    private static final org.slf4j.Logger log =
            org.slf4j.LoggerFactory.getLogger(DesignTacoController.class);

    private final IngredientRepository ingredientRepo;

    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepo) {
        this.ingredientRepo = ingredientRepo;
    }

    @GetMapping
    public String showDesignForm(Model model){
//        List<Ingredient> ingredients = Arrays.asList(
//                new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP),
//                new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP),
//                new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN),
//                new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN),
//                new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES),
//                new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES),
//                new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE),
//                new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE),
//                new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE),
//                new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE)
//        );
        List<Ingredient>ingredients= new ArrayList<>();
        ingredientRepo.findAll().forEach(i -> ingredients.add(i));
        Ingredient.Type[] types=Ingredient.Type.values();
        for(Ingredient.Type type : types){
            System.out.println("Type: " + type);
            model.addAttribute(
                    type.toString().toLowerCase(),
                    filterByType(ingredients,type));
        }
        model.addAttribute("taco", new Taco());

        return "design";
    }

    @PostMapping
    public String processDesign(@Valid Taco taco, Errors errors){
        if(errors.hasErrors()){
            log.info("errors: " + errors);
            return "design";
        }
        log.info("Processing design: " + taco);
        return "redirect:/orders/current";
    }

    private List<Ingredient>filterByType(
            List<Ingredient>ingredients,Ingredient.Type type){
//        List<Ingredient>list=new ArrayList<>();
//        for(Ingredient ingredient : ingredients){
//            if(ingredient.getType().equals(type)){
//                list.add(ingredient);
//            }
//        }
//        return list;
        return ingredients.stream()
                .filter(ingredient -> ingredient.getType().equals(type))
                .collect(Collectors.toList());
    }
}
