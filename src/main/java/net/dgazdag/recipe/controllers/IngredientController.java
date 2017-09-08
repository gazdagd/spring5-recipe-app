/**
 * Copyright © 2017 Ericsson. A written permission from Ericsson is required to use this software.
 */
package net.dgazdag.recipe.controllers;

import lombok.extern.slf4j.Slf4j;
import net.dgazdag.recipe.commands.IngredientCommand;
import net.dgazdag.recipe.commands.RecipeCommand;
import net.dgazdag.recipe.commands.UnitOfMeasureCommand;
import net.dgazdag.recipe.services.IngredientService;
import net.dgazdag.recipe.services.RecipeService;
import net.dgazdag.recipe.services.UnitOfMeasureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class IngredientController
{

  private RecipeService recipeService;
  private IngredientService ingredientService;
  private UnitOfMeasureService unitOfMeasureService;

  public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService)
  {
    this.recipeService = recipeService;
    this.ingredientService = ingredientService;
    this.unitOfMeasureService = unitOfMeasureService;
  }

  @GetMapping("/recipe/{recipeId}/ingredients")
  public String listIngredients(@PathVariable String recipeId, Model model){
    log.debug("Getting ingredient list for recipe id: " + recipeId);

    model.addAttribute("recipe", recipeService.findCommandById(Long.parseLong(recipeId)));

    return "/recipe/ingredient/list";
  }

  @GetMapping("/recipe/{recipeId}/ingredient/{id}/show")
  public String showIngredient(@PathVariable String recipeId, @PathVariable String id, Model model){
    model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.parseLong(recipeId), Long.parseLong(id)));

    return "recipe/ingredient/show";
  }

  @GetMapping("recipe/{recipeId}/ingredient/new")
  public String newIngredient(@PathVariable String recipeId, Model model){
    RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));

    IngredientCommand ingredientCommand = new IngredientCommand();
    ingredientCommand.setRecipeId(Long.valueOf(recipeId));
    model.addAttribute("ingredient", ingredientCommand);

    ingredientCommand.setUom(new UnitOfMeasureCommand());

    model.addAttribute("uomList", unitOfMeasureService.listAllUoms());

    return "recipe/ingredient/ingredientform";

  }

  @GetMapping("recipe/{recipeId}/ingredient/{id}/update")
  public String updateRecipeIngredient(@PathVariable String recipeId,
                                        @PathVariable String id, Model model){
    model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.parseLong(recipeId), Long.parseLong(id)));
    model.addAttribute("uomList", unitOfMeasureService.listAllUoms());
    return "recipe/ingredient/ingredientform";
  }

  @PostMapping("recipe/{recipeId}/ingredient")
  public String saveOrUpdate(@ModelAttribute IngredientCommand command){
    IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

    log.debug("saved recipe id: " + savedCommand.getRecipeId());
    log.debug("saved ingredient id: " + savedCommand.getId());

    return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredient/" + savedCommand.getId() + "/show";
  }

  @GetMapping("recipe/{recipeId}/ingredient/{id}/delete")
  public String delete(@PathVariable String recipeId, @PathVariable String id){
    ingredientService.deleteById(Long.valueOf(recipeId), Long.valueOf(id));
    return "redirect:/recipe/" + recipeId + "/ingredients";
  }
}
