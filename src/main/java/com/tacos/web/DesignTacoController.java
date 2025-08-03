package com.tacos.web;

import com.tacos.data.jpa.IngredientRepository;
import com.tacos.domain.Ingredient;
import com.tacos.domain.IngredientType;
import com.tacos.domain.Taco;
import com.tacos.domain.TacoOrder;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {
    private final IngredientRepository ingredientRepository;

    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        List<Ingredient> ingredients = new ArrayList<>(ingredientRepository.findAll());

        IngredientType[] ingredientTypes = IngredientType.values();
        for(IngredientType ingredientType : ingredientTypes) {
            model.addAttribute(ingredientType.toString().toLowerCase(),
                    filterByIngredientType(ingredients, ingredientType));
        }
    }

    @ModelAttribute(name = "tacoOrder")
    public TacoOrder order() {
        return new TacoOrder();
    }

    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    @GetMapping
    public String showDesignForm() {
        return "design";
    }

    @PostMapping
    public String processTaco(@Valid Taco taco, Errors errors,
                              @ModelAttribute TacoOrder tacoOrder) {
        if (errors.hasErrors()) {
            return "design";
        }

        tacoOrder.addTaco(taco);
        log.info("Processing taco: {}", taco);

        return "redirect:/orders/current";
    }

    private Iterable<Ingredient> filterByIngredientType(List<Ingredient> ingredients,
                                                    IngredientType ingredientType) {
        return ingredients
                .stream()
                .filter(ingredient ->
                        ingredient.getIngredientType().equals(ingredientType))
                .collect(Collectors.toList());
    }
}
