/**
 * Copyright © 2017 Ericsson. A written permission from Ericsson is required to use this software.
 */
package net.dgazdag.recipe.converters;

import lombok.Synchronized;
import net.dgazdag.recipe.commands.RecipeCommand;
import net.dgazdag.recipe.domain.Category;
import net.dgazdag.recipe.domain.Recipe;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand>
{

  private CategoryToCategoryCommand categoryToCategoryCommand;
  private IngredientToIngredientCommand ingredientToIngredientCommand;
  private NotesToNotesCommand notesToNotesCommand;

  public RecipeToRecipeCommand(CategoryToCategoryCommand categoryToCategoryCommand, IngredientToIngredientCommand ingredientToIngredientCommand, NotesToNotesCommand notesToNotesCommand)
  {
    this.categoryToCategoryCommand = categoryToCategoryCommand;
    this.ingredientToIngredientCommand = ingredientToIngredientCommand;
    this.notesToNotesCommand = notesToNotesCommand;
  }

  @Synchronized
  @Nullable
  @Override
  public RecipeCommand convert(Recipe recipe)
  {
    if (recipe == null) {
      return null;
    }

    final RecipeCommand command = new RecipeCommand();
    command.setId(recipe.getId());
    command.setCookTime(recipe.getCookTime());
    command.setPrepTime(recipe.getPrepTime());
    command.setDescription(recipe.getDescription());
    command.setDifficulty(recipe.getDifficulty());
    command.setDirections(recipe.getDirections());
    command.setServings(recipe.getServings());
    command.setSource(recipe.getSource());
    command.setUrl(recipe.getUrl());
    command.setImage(recipe.getImage());
    command.setNotes(notesToNotesCommand.convert(recipe.getNotes()));

    if (recipe.getCategories() != null && recipe.getCategories().size() > 0){
      recipe.getCategories()
          .forEach((Category category) -> command.getCategories().add(categoryToCategoryCommand.convert(category)));
    }

    if (recipe.getIngredients() != null && recipe.getIngredients().size() > 0){
      recipe.getIngredients()
          .forEach(ingredient -> command.getIngredients().add(ingredientToIngredientCommand.convert(ingredient)));
    }

    return command;
  }
}
