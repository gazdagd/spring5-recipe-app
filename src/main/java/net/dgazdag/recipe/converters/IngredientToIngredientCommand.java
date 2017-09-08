/**
 * Copyright © 2017 Ericsson. A written permission from Ericsson is required to use this software.
 */
package net.dgazdag.recipe.converters;

import lombok.Synchronized;
import net.dgazdag.recipe.commands.IngredientCommand;
import net.dgazdag.recipe.domain.Ingredient;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand>
{

  private UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

  public IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand)
  {
    this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
  }

  @Synchronized
  @Nullable
  @Override
  public IngredientCommand convert(Ingredient ingredient)
  {
    if (ingredient == null) {
      return null;
    }

    IngredientCommand ingredientCommand = new IngredientCommand();
    ingredientCommand.setId(ingredient.getId());
    ingredientCommand.setAmount(ingredient.getAmount());
    ingredientCommand.setDescription(ingredient.getDescription());
    ingredientCommand.setUom(unitOfMeasureToUnitOfMeasureCommand.convert(ingredient.getUom()));
    if(ingredient.getRecipe() != null) ingredientCommand.setRecipeId(ingredient.getRecipe().getId());
    return ingredientCommand;
  }
}
