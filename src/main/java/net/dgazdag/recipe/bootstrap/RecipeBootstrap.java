package net.dgazdag.recipe.bootstrap;

import lombok.extern.slf4j.Slf4j;
import net.dgazdag.recipe.domain.Category;
import net.dgazdag.recipe.domain.Difficulty;
import net.dgazdag.recipe.domain.Ingredient;
import net.dgazdag.recipe.domain.Notes;
import net.dgazdag.recipe.domain.Recipe;
import net.dgazdag.recipe.domain.UnitOfMeasure;
import net.dgazdag.recipe.repositories.CategoryRepository;
import net.dgazdag.recipe.repositories.RecipeRepository;
import net.dgazdag.recipe.repositories.UnitOfMeasureRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Created by edavgaz on 7/28/17.
 */
@Component
@Slf4j
@Profile("default")
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent>{

  private RecipeRepository recipeRepository;
  private CategoryRepository categoryRepository;
  private UnitOfMeasureRepository unitOfMeasureRepository;

  public RecipeBootstrap(RecipeRepository recipeRepository, CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository)
  {
    this.recipeRepository = recipeRepository;
    this.categoryRepository = categoryRepository;
    this.unitOfMeasureRepository = unitOfMeasureRepository;
  }

  @Override
  @Transactional
  public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
    log.debug("bootstrap has been started");
    recipeRepository.saveAll(getRecipes());
  }

  private List<Recipe> getRecipes(){
    List<Recipe> recipes = new ArrayList<>(2);

    // get UOMs
    Optional<UnitOfMeasure> eachUomOptional = unitOfMeasureRepository.findByDescription("Each");

    if(!eachUomOptional.isPresent()){
      throw new RuntimeException("Expected UOM not found");
    }

    Optional<UnitOfMeasure> tableSpoonUomOptional = unitOfMeasureRepository.findByDescription("Tablespoon");

    if(!tableSpoonUomOptional.isPresent()){
      throw new RuntimeException("Expected UOM not found");
    }

    Optional<UnitOfMeasure> teaSpoonUomOptional = unitOfMeasureRepository.findByDescription("Teaspoon");

    if(!teaSpoonUomOptional.isPresent()){
      throw new RuntimeException("Expected UOM not found");
    }

    Optional<UnitOfMeasure> dashUomOptional = unitOfMeasureRepository.findByDescription("Dash");

    if(!dashUomOptional.isPresent()){
      throw new RuntimeException("Expected UOM not found");
    }

    Optional<UnitOfMeasure> pintUomOptional = unitOfMeasureRepository.findByDescription("Pint");

    if(!pintUomOptional.isPresent()){
      throw new RuntimeException("Expected UOM not found");
    }

    Optional<UnitOfMeasure> cupsUomOptional = unitOfMeasureRepository.findByDescription("Cup");

    if(!cupsUomOptional.isPresent()){
      throw new RuntimeException("Expected UOM not found");
    }

    UnitOfMeasure eachUom = eachUomOptional.get();
    UnitOfMeasure tableSpoonUom = tableSpoonUomOptional.get();
    UnitOfMeasure teaSpoonUom = teaSpoonUomOptional.get();
    UnitOfMeasure dashUom = dashUomOptional.get();
    UnitOfMeasure pintUom = pintUomOptional.get();
    UnitOfMeasure cupsUom = cupsUomOptional.get();

    // get categories

    Optional<Category> americanCategoryOptional = categoryRepository.findByDescription("American");

    if(!americanCategoryOptional.isPresent()){
      throw new RuntimeException("Expected category not found");
    }

    Optional<Category> italianCategoryOptional = categoryRepository.findByDescription("Italian");

    if(!italianCategoryOptional.isPresent()){
      throw new RuntimeException("Expected category not found");
    }

    Optional<Category> mexicanCategoryOptional = categoryRepository.findByDescription("Mexican");

    if(!mexicanCategoryOptional.isPresent()){
      throw new RuntimeException("Expected category not found");
    }

    Optional<Category> fastFoodCategoryOptional = categoryRepository.findByDescription("Fast Food");

    if(!fastFoodCategoryOptional.isPresent()){
      throw new RuntimeException("Expected category not found");
    }

    Category americanCategory = americanCategoryOptional.get();
    Category italianCategory = italianCategoryOptional.get();
    Category mexicanCategory = mexicanCategoryOptional.get();
    Category fastFoodCategory = fastFoodCategoryOptional.get();

    Recipe guacamole = new Recipe();
    guacamole.setDescription("Perfect Guacamole");
    guacamole.setPrepTime(10);
    guacamole.setCookTime(0);
    guacamole.setServings(3);
    guacamole.setDifficulty(Difficulty.EASY);
    guacamole.setUrl("http://www.simplyrecipes.com/recipes/perfect_guacamole/");
    guacamole.setSource("Simply Recipes");

    guacamole.setDirections("1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place in a bowl.\n"
        + "\n"
        + "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)\n"
        + "\n"
        + "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n"
        + "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n"
        + "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n"
        + "4 Cover with plastic and chill to store: Place plastic wrap on the surface of the guacamole cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.\n"
        + "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.\n"
        + "\n"
        + "\n"
        + "Read more: http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz4pS5EZtko");
    Notes guacNotes = new Notes();
    guacNotes.setRecipeNotes("For a very quick guacamole just take a 1/4 cup of salsa and mix it in with your mashed avocados.\n"
        + "Feel free to experiment! One classic Mexican guacamole has pomegranate seeds and chunks of peaches in it (a Diana Kennedy favorite). Try guacamole with added pineapple, mango, or strawberries.\n"
        + "The simplest version of guacamole is just mashed avocados with salt. Don't let the lack of availability of other ingredients stop you from making guacamole.\n"
        + "To extend a limited supply of avocados, add either sour cream or cottage cheese to your guacamole dip. Purists may be horrified, but so what? It tastes great.\n"
        + "\n"
        + "\n"
        + "Read more: http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz4pSBmHLia");
    guacamole.setNotes(guacNotes);

    guacamole.addIngredient(new Ingredient("ripe avocados", new BigDecimal(2), eachUom));
    guacamole.addIngredient(new Ingredient("Kosher salt", new BigDecimal(5), teaSpoonUom));
    guacamole.addIngredient(new Ingredient("fresh lime juice or lemon juice", new BigDecimal(1), tableSpoonUom));
    guacamole.addIngredient(new Ingredient("minced red onion or thinly sliced green onion", new BigDecimal(2), tableSpoonUom));
    guacamole.addIngredient(new Ingredient("serrano chiles, stems and seeds removed, minced", new BigDecimal(2), eachUom));
    guacamole.addIngredient(new Ingredient("cilantro (leaves and tender stems), finely chopped", new BigDecimal(2), tableSpoonUom));
    guacamole.addIngredient(new Ingredient("freshly grated black pepper", new BigDecimal(1), dashUom));
    guacamole.addIngredient(new Ingredient("ripe tomato, seeds and pulp removed, chopped", new BigDecimal(.5), eachUom));

    guacamole.getCategories().add(americanCategory);
    guacamole.getCategories().add(mexicanCategory);

    recipes.add(guacamole);

    Recipe taco = new Recipe();
    taco.setDescription("Spicy Grilled Chicken Tacos");
    taco.setCookTime(9);
    taco.setPrepTime(20);
    taco.setDifficulty(Difficulty.MODERATE);

    taco.setDirections("1 Prepare a gas or charcoal grill for medium-high, direct heat.\n"
        + "2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n"
        + "Set aside to marinate while the grill heats and you prepare the rest of the toppings.\n"
        + "Spicy Grilled Chicken Tacos\n"
        + "3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.\n"
        + "4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.\n"
        + "Wrap warmed tortillas in a tea towel to keep them warm until serving.\n"
        + "5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.\n"
        + "\n"
        + "\n"
        + "Read more: http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4pSHDN2Zg");

    Notes tacoNotes = new Notes();
    tacoNotes.setRecipeNotes("We have a family motto and it is this: Everything goes better in a tortilla.\n"
        + "Any and every kind of leftover can go inside a warm tortilla, usually with a healthy dose of pickled jalapenos. I can always sniff out a late-night snacker when the aroma of tortillas heating in a hot pan on the stove comes wafting through the house.\n"
        + "Today’s tacos are more purposeful – a deliberate meal instead of a secretive midnight snack!\n"
        + "First, I marinate the chicken briefly in a spicy paste of ancho chile powder, oregano, cumin, and sweet orange juice while the grill is heating. You can also use this time to prepare the taco toppings.\n"
        + "Grill the chicken, then let it rest while you warm the tortillas. Now you are ready to assemble the tacos and dig in. The whole meal comes together in about 30 minutes!\n"
        + "Spicy Grilled Chicken TacosThe ancho chiles I use in the marinade are named for their wide shape. They are large, have a deep reddish brown color when dried, and are mild in flavor with just a hint of heat. You can find ancho chile powder at any markets that sell Mexican ingredients, or online.\n"
        + "I like to put all the toppings in little bowls on a big platter at the center of the table: avocados, radishes, tomatoes, red onions, wedges of lime, and a sour cream sauce. I add arugula, as well – this green isn’t traditional for tacos, but we always seem to have some in the fridge and I think it adds a nice green crunch to the tacos.\n"
        + "Everyone can grab a warm tortilla from the pile and make their own tacos just they way they like them.\n"
        + "You could also easily double or even triple this recipe for a larger party. A taco and a cold beer on a warm day? Now that’s living!\n"
        + "\n"
        + "\n"
        + "Read more: http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4pSK1iCnc");
    taco.setNotes(tacoNotes);

    taco.addIngredient(new Ingredient("ancho chili powder", new BigDecimal(2), tableSpoonUom));
    taco.addIngredient(new Ingredient("dried oregano", new BigDecimal(1), teaSpoonUom));
    taco.addIngredient(new Ingredient("dried cumin", new BigDecimal(1), teaSpoonUom));
    taco.addIngredient(new Ingredient("sugar", new BigDecimal(1), teaSpoonUom));
    taco.addIngredient(new Ingredient("salt", new BigDecimal(.5), teaSpoonUom));
    taco.addIngredient(new Ingredient("garlic, finely chopped", new BigDecimal(1), eachUom));
    taco.addIngredient(new Ingredient("finely grated orange zest", new BigDecimal(1), tableSpoonUom));
    taco.addIngredient(new Ingredient("afresh-squeezed orange juice", new BigDecimal(3), tableSpoonUom));
    taco.addIngredient(new Ingredient("olive oil", new BigDecimal(2), tableSpoonUom));
    taco.addIngredient(new Ingredient("skinless, boneless chicken thighs", new BigDecimal(6), eachUom));

    taco.addIngredient(new Ingredient("small corn tortillas", new BigDecimal(8), eachUom));
    taco.addIngredient(new Ingredient("packed baby arugula", new BigDecimal(3), cupsUom));
    taco.addIngredient(new Ingredient("medium ripe avocados, sliced", new BigDecimal(2), eachUom));
    taco.addIngredient(new Ingredient("radishes, thinly sliced", new BigDecimal(4), eachUom));
    taco.addIngredient(new Ingredient("cherry tomatoes, halved", new BigDecimal(.5), pintUom));
    taco.addIngredient(new Ingredient("red onion, thinly sliced", new BigDecimal(.5), eachUom));
    taco.addIngredient(new Ingredient("Roughly chopped cilantro", new BigDecimal(1), eachUom));
    taco.addIngredient(new Ingredient("sour cream thinned", new BigDecimal(.5), cupsUom));
    taco.addIngredient(new Ingredient("milk", new BigDecimal(.25), cupsUom));
    taco.addIngredient(new Ingredient("lime, cut into wedges", new BigDecimal(1), eachUom));

    taco.getCategories().add(americanCategory);

    recipes.add(taco);

    return recipes;
  }

}
