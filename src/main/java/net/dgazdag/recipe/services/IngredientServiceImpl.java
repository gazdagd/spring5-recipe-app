/**
 * Copyright © 2017 Ericsson. A written permission from Ericsson is required to use this software.
 */
package net.dgazdag.recipe.services;

import lombok.extern.slf4j.Slf4j;
import net.dgazdag.recipe.commands.IngredientCommand;
import net.dgazdag.recipe.converters.IngredientCommandToIngredient;
import net.dgazdag.recipe.converters.IngredientToIngredientCommand;
import net.dgazdag.recipe.domain.Ingredient;
import net.dgazdag.recipe.domain.Recipe;
import net.dgazdag.recipe.repositories.RecipeRepository;
import net.dgazdag.recipe.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService
{

  private final IngredientToIngredientCommand ingredientToIngredientCommand;
  private final IngredientCommandToIngredient ingredientCommandToIngredient;
  private final RecipeRepository recipeRepository;
  private final UnitOfMeasureRepository unitOfMeasureRepository;

  public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand, IngredientCommandToIngredient ingredientCommandToIngredient, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository)
  {
    this.ingredientToIngredientCommand = ingredientToIngredientCommand;
    this.ingredientCommandToIngredient = ingredientCommandToIngredient;
    this.recipeRepository = recipeRepository;
    this.unitOfMeasureRepository = unitOfMeasureRepository;
  }

  @Override public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long id)
  {
    Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

    return recipeOptional.map(recipe -> findIngredientById(recipe, id))
        .orElseThrow(() -> errorHandling("recipe", id));
  }

  @Override public IngredientCommand saveIngredientCommand(IngredientCommand command)
  {
    Optional<Recipe> recipeOptional= recipeRepository.findById(command.getRecipeId());
    if(!recipeOptional.isPresent()){
      log.error("Recipe not found with id: " + command.getRecipeId());
      return new IngredientCommand();
    } else {
      Recipe recipe = recipeOptional.get();

      Optional<Ingredient> ingredientOptional = recipe
          .getIngredients()
          .stream()
          .filter(ingredient -> ingredient.getId().equals(command.getId()))
          .findFirst();

      if(ingredientOptional.isPresent())
      {
        Ingredient ingredient = ingredientOptional.get();
        ingredient.setDescription(command.getDescription());
        ingredient.setAmount(command.getAmount());
        ingredient.setUom(
            unitOfMeasureRepository.findById(command.getUom().getId())
            .orElseThrow(() -> new RuntimeException("UOM NOT FOUND")));
      } else {
        Ingredient ingredient = ingredientCommandToIngredient.convert(command);
        ingredient.setRecipe(recipe);
        recipe.addIngredient(ingredient);
      }

      Recipe savedRecipe = recipeRepository.save(recipe);

      Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
          .filter(recipeIngredient -> recipeIngredient.getId().equals(command.getId())).findFirst();

      if(!savedIngredientOptional.isPresent()){
        //???
        savedIngredientOptional = savedRecipe.getIngredients().stream()
            .filter(recipeIngredient -> recipeIngredient.getDescription().equals(command.getDescription()))
            .filter(recipeIngredient -> recipeIngredient.getAmount().equals(command.getAmount()))
            .filter(recipeIngredient -> recipeIngredient.getUom().getId().equals(command.getUom().getId()))
            .findFirst();
      }


      return ingredientToIngredientCommand.convert(savedIngredientOptional.get());
    }
  }

  @Override
  public void deleteById(Long recipeId, Long id)
  {
    log.info("ingredient with id " + id + " will be deleted");
    Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
    if(!recipeOptional.isPresent()){
      log.error("recipe not found with id: " + recipeId);
      throw new RuntimeException("Recipe not found");
    }
    Recipe recipe = recipeOptional.get();

    Ingredient ingredient = recipe.getIngredients().stream()
      .filter(ing -> ing.getId().equals(id))
      .findFirst().orElseThrow(() -> new RuntimeException("ingredient not found"));

    ingredient.setRecipe(null);
    recipe.getIngredients().remove(ingredient);

    recipeRepository.save(recipe);
  }

  private IngredientCommand findIngredientById(Recipe recipe, Long id){
    return recipe.getIngredients().stream()
        .filter(i -> i.getId().equals(id))
        .map(ingredientToIngredientCommand::convert)
        .findFirst()
        .orElseThrow(() -> errorHandling("ingredient", id));
  }

  private static RuntimeException errorHandling(String entityName, Long id){
    log.error("Entity "+ entityName +" not found with id " + id + "!");
    return new RuntimeException("Entity "+ entityName +" not found with id " + id + "!");
  }
}
