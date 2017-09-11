package net.dgazdag.recipe.services;

import net.dgazdag.recipe.converters.RecipeCommandToRecipe;
import net.dgazdag.recipe.converters.RecipeToRecipeCommand;
import net.dgazdag.recipe.domain.Recipe;
import net.dgazdag.recipe.exceptions.NotFoundException;
import net.dgazdag.recipe.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest
{

  RecipeServiceImpl recipeService;

  @Mock
  RecipeRepository recipeRepository;

  @Mock
  RecipeCommandToRecipe recipeCommandToRecipe;
  @Mock
  RecipeToRecipeCommand recipeToRecipeCommand;

  @Before
  public void setUp() throws Exception
  {
    MockitoAnnotations.initMocks(this);

    recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
  }

  @Test
  public void getRecipeById() throws Exception
  {
    Recipe recipe = new Recipe();
    recipe.setId(1L);
    Optional<Recipe> recipeOptional = Optional.of(recipe);

    when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

    Recipe recipeReturned = recipeService.findById(1L);

    assertNotNull("Null recipe returned", recipeReturned);
    verify(recipeRepository, times(1)).findById(anyLong());
    verify(recipeRepository, never()).findAll();
  }

  @Test
  public void getRecipes() throws Exception
  {
    Recipe recipe = new Recipe();
    Set<Recipe> recipesData = new HashSet<>();
    recipesData.add(recipe);

    when(recipeService.getRecipes()).thenReturn(recipesData);

    Set<Recipe> recipes = recipeService.getRecipes();

    assertEquals(recipes.size(), 1);
    verify(recipeRepository, times(1)).findAll();
    verify(recipeRepository, never()).findById(anyLong());
  }

  @Test
  public void testDeleteById() throws Exception
  {
    Long idToDelete = Long.valueOf(2L);
    recipeService.deleteById(idToDelete);

    verify(recipeRepository, times(1)).deleteById(anyLong());
  }

  @Test(expected = NotFoundException.class)
  public void getRecipeByIdTestNotFound() throws Exception
  {
    when(recipeRepository.findById(anyLong())).thenReturn(Optional.empty());
    Recipe recipe = recipeService.findById(1L);
  }
}