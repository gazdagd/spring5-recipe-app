/**
 * Copyright © 2017 Ericsson. A written permission from Ericsson is required to use this software.
 */
package net.dgazdag.recipe.services;

import lombok.extern.slf4j.Slf4j;
import net.dgazdag.recipe.commands.RecipeCommand;
import net.dgazdag.recipe.converters.RecipeCommandToRecipe;
import net.dgazdag.recipe.converters.RecipeToRecipeCommand;
import net.dgazdag.recipe.domain.Recipe;
import net.dgazdag.recipe.exceptions.NotFoundException;
import net.dgazdag.recipe.repositories.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class RecipeServiceImpl implements RecipeService
{

  private final RecipeRepository recipeRepository;
  private final RecipeCommandToRecipe recipeCommandToRecipe;
  private final RecipeToRecipeCommand recipeToRecipeCommand;

  public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand)
  {
    this.recipeRepository = recipeRepository;
    this.recipeCommandToRecipe = recipeCommandToRecipe;
    this.recipeToRecipeCommand = recipeToRecipeCommand;
  }

  @Override public Set<Recipe> getRecipes()
  {
    log.debug("service has been called!");
    Set<Recipe> recipes = new HashSet<>();
    recipeRepository.findAll().forEach(recipes::add);
    return recipes;
  }

  @Override public Recipe findById(Long id)
  {
    Optional<Recipe> recipeOptional = recipeRepository.findById(id);
    if(!recipeOptional.isPresent()){
      throw new NotFoundException("Recipe not found for id: " + id.toString());
    }
    return recipeOptional.get();
  }

  @Transactional
  @Override public RecipeCommand findCommandById(Long id)
  {
    return recipeToRecipeCommand.convert(findById(id));
  }

  @Override
  @Transactional
  public RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand)
  {
    Recipe detachedRecipe = recipeCommandToRecipe.convert(recipeCommand);

    Recipe savedRecipe = recipeRepository.save(detachedRecipe);
    log.debug("saved recipeId: " + savedRecipe.getId());
    return recipeToRecipeCommand.convert(savedRecipe);
  }

  @Override public void deleteById(Long id)
  {
    recipeRepository.deleteById(id);
  }
}
