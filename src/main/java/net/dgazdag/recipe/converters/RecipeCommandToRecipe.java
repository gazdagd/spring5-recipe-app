/**
 * Copyright © 2017 Ericsson. A written permission from Ericsson is required to use this software.
 */
package net.dgazdag.recipe.converters;

import lombok.Synchronized;
import net.dgazdag.recipe.commands.RecipeCommand;
import net.dgazdag.recipe.domain.Recipe;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe>
{

  private CategoryCommandToCategory categoryCommandToCategory;
  private IngredientCommandToIngredient ingredientCommandToIngredient;
  private NotesCommandToNotes notesCommandToNotes;

  public RecipeCommandToRecipe(CategoryCommandToCategory categoryCommandToCategory,
      IngredientCommandToIngredient ingredientCommandToIngredient,
      NotesCommandToNotes notesCommandToNotes)
  {
    this.categoryCommandToCategory = categoryCommandToCategory;
    this.ingredientCommandToIngredient = ingredientCommandToIngredient;
    this.notesCommandToNotes = notesCommandToNotes;
  }

  @Synchronized
  @Nullable
  @Override
  public Recipe convert(RecipeCommand recipeCommand)
  {
    if (recipeCommand == null) {
      return null;
    }

    final Recipe recipe = new Recipe();
    recipe.setId(recipeCommand.getId());
    recipe.setCookTime(recipeCommand.getCookTime());
    recipe.setPrepTime(recipeCommand.getPrepTime());
    recipe.setDescription(recipeCommand.getDescription());
    recipe.setDifficulty(recipeCommand.getDifficulty());
    recipe.setDirections(recipeCommand.getDirections());
    recipe.setServings(recipeCommand.getServings());
    recipe.setSource(recipeCommand.getSource());
    recipe.setUrl(recipeCommand.getUrl());
    recipe.setNotes(notesCommandToNotes.convert(recipeCommand.getNotes()));

    if (recipeCommand.getCategories() != null && recipeCommand.getCategories().size() > 0){
      recipeCommand.getCategories()
          .forEach( category -> recipe.getCategories().add(categoryCommandToCategory.convert(category)));
    }

    if (recipeCommand.getIngredients() != null && recipeCommand.getIngredients().size() > 0){
      recipeCommand.getIngredients()
          .forEach(ingredient -> recipe.getIngredients().add(ingredientCommandToIngredient.convert(ingredient)));
    }

    return recipe;
  }
}
