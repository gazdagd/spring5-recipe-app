package net.dgazdag.recipe.services;

import net.dgazdag.recipe.domain.Recipe;

import java.util.Set;

public interface RecipeService
{
  Set<Recipe> getRecipes();
}
