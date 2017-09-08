/**
 * Copyright © 2017 Ericsson. A written permission from Ericsson is required to use this software.
 */
package net.dgazdag.recipe.converters;

import net.dgazdag.recipe.commands.IngredientCommand;
import net.dgazdag.recipe.domain.Ingredient;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient>
{

  private UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure;

  public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure)
  {
    this.unitOfMeasureCommandToUnitOfMeasure = unitOfMeasureCommandToUnitOfMeasure;
  }

  @Nullable
  @Override
  public Ingredient convert(IngredientCommand ingredientCommand)
  {
    if (ingredientCommand == null) {
      return null;
    }

    final Ingredient ingredient = new Ingredient();
    ingredient.setId(ingredientCommand.getId());
    ingredient.setAmount(ingredientCommand.getAmount());
    ingredient.setDescription(ingredientCommand.getDescription());
    ingredient.setUom(unitOfMeasureCommandToUnitOfMeasure.convert(ingredientCommand.getUom()));
    return ingredient;
  }
}
