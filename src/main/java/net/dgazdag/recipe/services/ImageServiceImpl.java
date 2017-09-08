/**
 * Copyright © 2017 Ericsson. A written permission from Ericsson is required to use this software.
 */
package net.dgazdag.recipe.services;

import lombok.extern.slf4j.Slf4j;
import net.dgazdag.recipe.domain.Recipe;
import net.dgazdag.recipe.repositories.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService
{

  private final RecipeRepository recipeRepository;

  public ImageServiceImpl(RecipeRepository recipeRepository)
  {
    this.recipeRepository = recipeRepository;
  }

  @Override
  @Transactional
  public void saveImageFile(Long recipeId, MultipartFile multipartFile)
  {

    Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new RuntimeException("recipe not found"));

    try
    {
      Byte[] bytes = new Byte[multipartFile.getBytes().length];
      int i=0;
      for(byte b : multipartFile.getBytes()){
        bytes[i++] = b;
      }
      recipe.setImage(bytes);
      recipeRepository.save(recipe);
    }catch (IOException ioe){
      log.error("error occured", ioe);
      ioe.printStackTrace();
    }



    log.debug("Recieved a file");
  }
}
