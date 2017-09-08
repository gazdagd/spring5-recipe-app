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
public class UnitOfMeasureCommandToUnitOfMeasure implements Converter<UnitOfMeasureCommand, UnitOfMeasure>
{
  @Synchronized
  @Nullable
  @Override
  public UnitOfMeasure convert(UnitOfMeasureCommand unitOfMeasureCommand)
  {
    if(unitOfMeasureCommand == null) return null;

    final UnitOfMeasure uom = new UnitOfMeasure();
    uom.setId(unitOfMeasureCommand.getId());
    uom.setDescription(unitOfMeasureCommand.getDescription());
    return uom;
  }
}
