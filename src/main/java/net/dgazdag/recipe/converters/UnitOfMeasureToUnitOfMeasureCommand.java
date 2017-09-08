/**
 * Copyright © 2017 Ericsson. A written permission from Ericsson is required to use this software.
 */
package net.dgazdag.recipe.converters;

import lombok.Synchronized;
import net.dgazdag.recipe.commands.UnitOfMeasureCommand;
import net.dgazdag.recipe.domain.UnitOfMeasure;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureToUnitOfMeasureCommand implements Converter<UnitOfMeasure, UnitOfMeasureCommand>
{
  @Synchronized
  @Nullable
  @Override
  public UnitOfMeasureCommand convert(UnitOfMeasure unitOfMeasure)
  {
    if(unitOfMeasure == null) return null;

    final UnitOfMeasureCommand uomc = new UnitOfMeasureCommand();
    uomc.setId(unitOfMeasure.getId());
    uomc.setDescription(unitOfMeasure.getDescription());
    return uomc;
  }
}
