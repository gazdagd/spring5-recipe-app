/**
 * Copyright © 2017 Ericsson. A written permission from Ericsson is required to use this software.
 */
package net.dgazdag.recipe.controllers;

import net.dgazdag.recipe.commands.RecipeCommand;
import net.dgazdag.recipe.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RecipeController
{

  private RecipeService recipeService;

  public RecipeController(RecipeService recipeService)
  {
    this.recipeService = recipeService;
  }

  @GetMapping("/recipe/{id}/show")
  public String showById(@PathVariable String id, Model model){
    model.addAttribute("recipe", recipeService.findById(Long.parseLong(id)));

    return "recipe/show";
  }

  @GetMapping("recipe/new")
  public String newRecipe(Model model){
    model.addAttribute("recipe", new RecipeCommand());
    return "recipe/recipeform";
  }

  @GetMapping("recipe/{id}/update")
  public String updateRecipe(@PathVariable String id, Model model){
    model.addAttribute("recipe", recipeService.findCommandById(Long.parseLong(id)));
    return "recipe/recipeform";
  }

  @PostMapping("recipe")
  public String save(@ModelAttribute RecipeCommand recipeCommand){
    RecipeCommand savedCommand = recipeService.saveRecipeCommand(recipeCommand);
    return "redirect:/recipe/" + savedCommand.getId() + "/show";
  }

  @GetMapping("recipe/{id}/delete")
  public String delete(@PathVariable String id){
    recipeService.deleteById(Long.parseLong(id));
    return "redirect:/";
  }

}
