package com.tacos.config;

import com.tacos.data.jpa.IngredientRepository;
import com.tacos.data.jpa.OrderRepository;
import com.tacos.data.jpa.TacoRepository;
import com.tacos.data.jpa.UserRepository;
import com.tacos.domain.Ingredient;
import com.tacos.domain.IngredientType;
import com.tacos.domain.Taco;
import com.tacos.domain.TacoOrder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.net.URI;
import java.util.Arrays;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
        registry.addViewController("login").setViewName("login");
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Traverson traverson() {
        return new Traverson(
                URI.create("http://localhost:8080/api"),
                MediaTypes.HAL_JSON
        );
    }

    @Bean
    public CommandLineRunner dataLoader(
            IngredientRepository repo,
            UserRepository userRepo,
            PasswordEncoder encoder,
            TacoRepository tacoRepo) {
        return args -> {
            Ingredient flourTortilla = new Ingredient(
                    "FLTO", "Flour Tortilla", IngredientType.WRAP);
            Ingredient cornTortilla = new Ingredient(
                    "COTO", "Corn Tortilla", IngredientType.WRAP);
            Ingredient groundBeef = new Ingredient(
                    "GRBF", "Ground Beef", IngredientType.PROTEIN);
            Ingredient carnitas = new Ingredient(
                    "CARN", "Carnitas", IngredientType.PROTEIN);
            Ingredient tomatoes = new Ingredient(
                    "TMTO", "Diced Tomatoes", IngredientType.VEGGIES);
            Ingredient lettuce = new Ingredient(
                    "LETC", "Lettuce", IngredientType.VEGGIES);
            Ingredient cheddar = new Ingredient(
                    "CHED", "Cheddar", IngredientType.CHEESE);
            Ingredient jack = new Ingredient(
                    "JACK", "Monterrey Jack", IngredientType.CHEESE);
            Ingredient salsa = new Ingredient(
                    "SLSA", "Salsa", IngredientType.SAUCE);
            Ingredient sourCream = new Ingredient(
                    "SRCR", "Sour Cream", IngredientType.SAUCE);
            repo.save(flourTortilla);
            repo.save(cornTortilla);
            repo.save(groundBeef);
            repo.save(carnitas);
            repo.save(tomatoes);
            repo.save(lettuce);
            repo.save(cheddar);
            repo.save(jack);
            repo.save(salsa);
            repo.save(sourCream);
            Taco taco1 = new Taco();
            taco1.setName("Carnivore");
            taco1.setIngredients(Arrays.asList(
                    flourTortilla, groundBeef, carnitas,
                    sourCream, salsa, cheddar));
            tacoRepo.save(taco1);
            Taco taco2 = new Taco();
            taco2.setName("Bovine Bounty");
            taco2.setIngredients(Arrays.asList(
                    cornTortilla, groundBeef, cheddar,
                    jack, sourCream));
            tacoRepo.save(taco2);
            Taco taco3 = new Taco();
            taco3.setName("Veg-Out");
            taco3.setIngredients(Arrays.asList(
                    flourTortilla, cornTortilla, tomatoes,
                    lettuce, salsa));
            tacoRepo.save(taco3);
        };
    }
}
