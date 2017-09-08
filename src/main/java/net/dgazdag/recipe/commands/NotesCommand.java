/**
 * Copyright © 2017 Ericsson. A written permission from Ericsson is required to use this software.
 */
package net.dgazdag.recipe.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NotesCommand
{
  private Long id;
  private RecipeCommand recipe;
  private String recipeNotes;
}
