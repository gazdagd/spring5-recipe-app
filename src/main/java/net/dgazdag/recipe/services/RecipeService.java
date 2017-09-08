package net.dgazdag.recipe.services;

import net.dgazdag.recipe.commands.RecipeCommand;
import net.dgazdag.recipe.domain.Recipe;

import java.util.Set;

public interface RecipeService
{
  Set<Recipe> getRecipes();

  Recipe findById(Long id);

  RecipeCommand findCommandById(Long id);

  RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);

  void deleteById(Long id);
}
