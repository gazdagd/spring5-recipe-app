/**
 * Copyright © 2017 Ericsson. A written permission from Ericsson is required to use this software.
 */
package net.dgazdag.recipe.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.dgazdag.recipe.domain.Difficulty;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand
{
  private Long id;
  @NotBlank
  @Size(min = 3, max = 255)
  private String description;

  @Min(1)
  @Max(999)
  private Integer prepTime;

  @Min(1)
  @Max(999)
  private Integer cookTime;

  @Min(1)
  @Max(100)
  private Integer servings;
  private String source;

  @URL
  @NotBlank
  private String url;

  @NotBlank
  private String directions;
  private Difficulty difficulty;
  private Byte[] image;
  private NotesCommand notes;
  private Set<IngredientCommand> ingredients = new HashSet<>();
  private Set<CategoryCommand> categories = new HashSet<>();
}
