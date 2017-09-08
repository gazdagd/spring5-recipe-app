package net.dgazdag.recipe.controllers;

import net.dgazdag.recipe.commands.RecipeCommand;
import net.dgazdag.recipe.services.ImageService;
import net.dgazdag.recipe.services.RecipeService;
import org.assertj.core.internal.Bytes;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ImageControllerTest
{

  @Mock
  ImageService imageService;

  @Mock
  RecipeService recipeService;

  ImageController controller;

  MockMvc mockMvc;

  @Before
  public void setUp() throws Exception
  {
    MockitoAnnotations.initMocks(this);
    controller = new ImageController(imageService, recipeService);
    mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

  }

  @Test
  public void getImageForm() throws Exception
  {
    RecipeCommand recipeCommand = new RecipeCommand();
    recipeCommand.setId(1L);

    when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);

    mockMvc.perform(get("/recipe/1/image"))
    .andExpect(status().isOk())
    .andExpect(model().attributeExists("recipe"));

    verify(recipeService, times(1)).findCommandById(anyLong());
  }

  @Test
  public void handleImagePost() throws Exception
  {
    MockMultipartFile mockMultipartFile =
        new MockMultipartFile("imagefile", "testing.txt",
            "text/plain", "Spring Guru".getBytes());

    mockMvc.perform(multipart("/recipe/1/image").file(mockMultipartFile))
      .andExpect(status().is3xxRedirection())
      .andExpect(header().string("Location", "/recipe/1/show"));

    verify(imageService, times(1)).saveImageFile(anyLong(), any());
  }

  @Test
  public void renderImageFromDb() throws Exception
  {
    //given
    RecipeCommand command = new RecipeCommand();
    command.setId(1L);

    String s = "fake image text";
    Byte[] byteBoxed = new Byte[s.getBytes().length];

    int i = 0;
    for(byte primByte : s.getBytes()){
      byteBoxed[i++] = primByte;
    }

    command.setImage(byteBoxed);

    when(recipeService.findCommandById(anyLong())).thenReturn(command);

    //when
    MockHttpServletResponse response = mockMvc.perform(get("/recipe/1/recipeimage"))
        .andExpect(status().isOk())
        .andReturn().getResponse();

    byte[] responseBytes = response.getContentAsByteArray();

    assertEquals(s.getBytes().length, responseBytes.length);
  }
}