/**
 * Copyright © 2017 Ericsson. A written permission from Ericsson is required to use this software.
 */
package net.dgazdag.recipe.services;

import lombok.extern.slf4j.Slf4j;
import net.dgazdag.recipe.domain.Recipe;
import net.dgazdag.recipe.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class RecipeServiceImpl implements RecipeService
{

  private final RecipeRepository recipeRepository;

  public RecipeServiceImpl(RecipeRepository recipeRepository)
  {
    this.recipeRepository = recipeRepository;
  }

  @Override public Set<Recipe> getRecipes()
  {
    log.debug("service has been called!");
    Set<Recipe> recipes = new HashSet<>();
    recipeRepository.findAll().forEach(recipes::add);
    return recipes;
  }
}
