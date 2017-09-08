package net.dgazdag.recipe.services;

import net.dgazdag.recipe.commands.IngredientCommand;

public interface IngredientService
{

  IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long id);

  IngredientCommand saveIngredientCommand(IngredientCommand command);

  void deleteById(Long recipeId, Long id);

}
