/**
 * Copyright © 2017 Ericsson. A written permission from Ericsson is required to use this software.
 */
package net.dgazdag.recipe.services;

import com.sun.org.apache.bcel.internal.generic.NEW;
import net.dgazdag.recipe.commands.RecipeCommand;
import net.dgazdag.recipe.converters.RecipeCommandToRecipe;
import net.dgazdag.recipe.converters.RecipeToRecipeCommand;
import net.dgazdag.recipe.domain.Recipe;
import net.dgazdag.recipe.repositories.RecipeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeServiceIT
{
  public static final String NEW_DESCRIPTION = "New description";

  @Autowired
  RecipeService recipeService;

  @Autowired
  RecipeRepository recipeRepository;

  @Autowired
  RecipeCommandToRecipe recipeCommandToRecipe;

  @Autowired
  RecipeToRecipeCommand recipeToRecipeCommand;

  @Test
  @Transactional
  public void testSaveOfDescription() throws Exception
  {
    //given
    Iterable<Recipe> recipes = recipeRepository.findAll();
    Recipe testRecipe = recipes.iterator().next();
    RecipeCommand testRecipeCommand = recipeToRecipeCommand.convert(testRecipe);

    //when
    testRecipeCommand.setDescription(NEW_DESCRIPTION);
    RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(testRecipeCommand);

    //then
    assertEquals(NEW_DESCRIPTION, savedRecipeCommand.getDescription());
    assertEquals(testRecipe.getId(), savedRecipeCommand.getId());
    assertEquals(testRecipe.getCategories().size(), savedRecipeCommand.getCategories().size());
    assertEquals(testRecipe.getIngredients().size(), savedRecipeCommand.getIngredients().size());
  }
}
