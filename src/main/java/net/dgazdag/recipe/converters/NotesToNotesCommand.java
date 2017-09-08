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
public class NotesToNotesCommand implements Converter<Notes, NotesCommand>
{
  @Synchronized
  @Nullable
  @Override
  public NotesCommand convert(Notes notes)
  {
    if (notes == null) {
      return null;
    }

    final NotesCommand notesCommand = new NotesCommand();
    notesCommand.setId(notes.getId());
    notesCommand.setRecipeNotes(notes.getRecipeNotes());
    return notesCommand;
  }
}
