/**
 * Copyright © 2017 Ericsson. A written permission from Ericsson is required to use this software.
 */
package net.dgazdag.recipe.converters;

import lombok.Synchronized;
import net.dgazdag.recipe.commands.NotesCommand;
import net.dgazdag.recipe.domain.Notes;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NotesCommandToNotes implements Converter<NotesCommand, Notes>
{
  @Synchronized
  @Nullable
  @Override
  public Notes convert(NotesCommand notesCommand)
  {
    if(notesCommand == null) {
      return null;
    }

    final Notes notes = new Notes();
    notes.setId(notesCommand.getId());
    notes.setRecipeNotes(notesCommand.getRecipeNotes());
    return notes;
  }
}
