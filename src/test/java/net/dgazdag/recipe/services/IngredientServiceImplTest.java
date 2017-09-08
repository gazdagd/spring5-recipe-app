package net.dgazdag.recipe.services;

import net.dgazdag.recipe.commands.IngredientCommand;
import net.dgazdag.recipe.commands.UnitOfMeasureCommand;
import net.dgazdag.recipe.converters.IngredientCommandToIngredient;
import net.dgazdag.recipe.converters.IngredientToIngredientCommand;
import net.dgazdag.recipe.converters.UnitOfMeasureCommandToUnitOfMeasure;
import net.dgazdag.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import net.dgazdag.recipe.domain.Ingredient;
import net.dgazdag.recipe.domain.Recipe;
import net.dgazdag.recipe.repositories.RecipeRepository;
import net.dgazdag.recipe.repositories.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class IngredientServiceImplTest
{

  UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
  UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure = new UnitOfMeasureCommandToUnitOfMeasure();
  IngredientCommandToIngredient ingredientCommandToIngredient = new IngredientCommandToIngredient(unitOfMeasureCommandToUnitOfMeasure);
  IngredientToIngredientCommand ingredientToIngredientCommand;

  @Mock
  UnitOfMeasureRepository unitOfMeasureRepository;

  @Mock
  RecipeRepository recipeRepository;

  IngredientServiceImpl ingredientService;

  @Before
  public void setUp() throws Exception
  {
    MockitoAnnotations.initMocks(this);

    ingredientToIngredientCommand = new IngredientToIngredientCommand(unitOfMeasureToUnitOfMeasureCommand);
    ingredientService = new IngredientServiceImpl(ingredientToIngredientCommand, ingredientCommandToIngredient, recipeRepository, unitOfMeasureRepository);
  }

  @Test
  public void findByRecipeIdAndIngredientIdHappy() throws Exception
  {
    //given
    Recipe recipe = new Recipe();
    recipe.setId(1L);

    Ingredient ingredient1 = new Ingredient();
    ingredient1.setId(1L);

    Ingredient ingredient2 = new Ingredient();
    ingredient2.setId(2L);

    Ingredient ingredient3 = new Ingredient();
    ingredient3.setId(3L);

    recipe.addIngredient(ingredient1);
    recipe.addIngredient(ingredient2);
    recipe.addIngredient(ingredient3);
    Optional<Recipe> recipeOptional = Optional.of(recipe);

    when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

    //when
    IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(1L, 3L);

    //then
    assertEquals(Long.valueOf(3L), ingredientCommand.getId());
    assertEquals(Long.valueOf(1L), ingredientCommand.getRecipeId());
    verify(recipeRepository, times(1)).findById(anyLong());

  }

  @Test
  public void testSaveRecipeCommand() throws Exception
  {
    //given
    IngredientCommand command = new IngredientCommand();
    command.setId(3L);
    command.setRecipeId(2L);

    Optional<Recipe> recipeOptional = Optional.of(new Recipe());

    Recipe savedRecipe = new Recipe();
    savedRecipe.addIngredient(new Ingredient());
    savedRecipe.getIngredients().iterator().next().setId(3L);

    when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
    when(recipeRepository.save(any())).thenReturn(savedRecipe);

    //when
    IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

    //then
    assertEquals(Long.valueOf(3L), savedCommand.getId());
    verify(recipeRepository, times(1)).findById(anyLong());
    verify(recipeRepository, times(1)).save(any(Recipe.class));

  }

  @Test
  public void testDeleteIngredient() throws Exception
  {
    //given
    Recipe recipe = new Recipe();
    recipe.setId(1L);
    Ingredient ingredient1 = new Ingredient();
    ingredient1.setId(1L);
    recipe.addIngredient(ingredient1);

    Ingredient ingredient2 = new Ingredient();
    ingredient2.setId(2L);
    recipe.addIngredient(ingredient2);


    when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));

    //when
    ingredientService.deleteById(1L, 2L);

    verify(recipeRepository, times(1)).findById(anyLong());
    verify(recipeRepository, times(1)).save(any(Recipe.class));
  }
}