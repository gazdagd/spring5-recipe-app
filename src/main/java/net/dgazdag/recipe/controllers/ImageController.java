/**
 * Copyright © 2017 Ericsson. A written permission from Ericsson is required to use this software.
 */
package net.dgazdag.recipe.controllers;

import net.dgazdag.recipe.commands.RecipeCommand;
import net.dgazdag.recipe.services.ImageService;
import net.dgazdag.recipe.services.RecipeService;
import net.dgazdag.recipe.utils.RecipeUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import sun.nio.ch.IOUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class ImageController
{

  private final ImageService imageService;
  private final RecipeService recipeService;

  public ImageController(ImageService imageService, RecipeService recipeService)
  {
    this.imageService = imageService;
    this.recipeService = recipeService;
  }

  @GetMapping("recipe/{id}/image")
  public String showUploadForm(@PathVariable String id, Model model ){
    model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
    return "recipe/imageuploadform";
  }

  @PostMapping("recipe/{id}/image")
  public String handleImagePost(@PathVariable String id, @RequestParam("imagefile") MultipartFile file){

    imageService.saveImageFile(Long.valueOf(id), file);

    return "redirect:/recipe/" + id + "/show";
  }

  @GetMapping("recipe/{id}/recipeimage")
  public void renderImageFromDb(@PathVariable String id, HttpServletResponse response) throws IOException{
    RecipeCommand command = recipeService.findCommandById(Long.valueOf(id));

    response.setContentType("image/jpeg");
    InputStream is = new ByteArrayInputStream(RecipeUtils.toPrimitive(command.getImage()));
    IOUtils.copy(is, response.getOutputStream());
  }
}
