/**
 * Copyright © 2017 Ericsson. A written permission from Ericsson is required to use this software.
 */
package net.dgazdag.recipe.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.dgazdag.recipe.domain.Difficulty;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand
{
  private Long id;
  private String description;
  private Integer prepTime;
  private Integer cookTime;
  private Integer servings;
  private String source;
  private String url;
  private String directions;
  private Difficulty difficulty;
  private Byte[] image;
  private NotesCommand notes;
  private Set<IngredientCommand> ingredients = new HashSet<>();
  private Set<CategoryCommand> categories = new HashSet<>();
}
