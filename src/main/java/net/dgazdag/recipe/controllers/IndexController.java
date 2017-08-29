/**
 * Copyright © 2017 Ericsson. A written permission from Ericsson is required to use this software.
 */
package net.dgazdag.recipe.controllers;

import lombok.extern.slf4j.Slf4j;
import net.dgazdag.recipe.domain.Category;
import net.dgazdag.recipe.domain.UnitOfMeasure;
import net.dgazdag.recipe.repositories.CategoryRepository;
import net.dgazdag.recipe.repositories.UnitOfMeasureRepository;
import net.dgazdag.recipe.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@Slf4j
public class IndexController
{

  private RecipeService recipeService;

  public IndexController(RecipeService recipeService)
  {
    this.recipeService = recipeService;
  }

  @RequestMapping({"", "/", "/index"})
  public String getIndexPage(Model model){
    log.debug("controller in action");
    model.addAttribute("recipes", recipeService.getRecipes());
    return "index";
  }

}
