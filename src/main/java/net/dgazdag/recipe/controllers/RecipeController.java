/**
 * Copyright © 2017 Ericsson. A written permission from Ericsson is required to use this software.
 */
package net.dgazdag.recipe.controllers;

import lombok.extern.slf4j.Slf4j;
import net.dgazdag.recipe.commands.RecipeCommand;
import net.dgazdag.recipe.exceptions.NotFoundException;
import net.dgazdag.recipe.services.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Slf4j
@Controller
public class RecipeController
{

  public static final String RECIPE_RECIPEFORM = "recipe/recipeform";
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
    return RECIPE_RECIPEFORM;
  }

  @GetMapping("recipe/{id}/update")
  public String updateRecipe(@PathVariable String id, Model model){
    model.addAttribute("recipe", recipeService.findCommandById(Long.parseLong(id)));
    return RECIPE_RECIPEFORM;
  }

  @PostMapping("recipe")
  public String save(@Valid @ModelAttribute("recipe") RecipeCommand recipeCommand, BindingResult bindingResult){
    if(bindingResult.hasErrors()){
      bindingResult.getAllErrors().forEach(objectError -> {
        log.debug(objectError.toString());
      });
      return RECIPE_RECIPEFORM;
    }

    RecipeCommand savedCommand = recipeService.saveRecipeCommand(recipeCommand);
    return "redirect:/recipe/" + savedCommand.getId() + "/show";
  }

  @GetMapping("recipe/{id}/delete")
  public String delete(@PathVariable String id){
    recipeService.deleteById(Long.parseLong(id));
    return "redirect:/";
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(NotFoundException.class)
  public ModelAndView handleNotFound(Exception ex){
    log.error("Handling not found exception");
    log.error(ex.getMessage());
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("404error");
    modelAndView.addObject("exception", ex);
    return modelAndView;
  }

}
