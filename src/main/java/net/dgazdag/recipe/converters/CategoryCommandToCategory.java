/**
 * Copyright © 2017 Ericsson. A written permission from Ericsson is required to use this software.
 */
package net.dgazdag.recipe.converters;

import lombok.Synchronized;
import net.dgazdag.recipe.commands.CategoryCommand;
import net.dgazdag.recipe.domain.Category;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CategoryCommandToCategory implements Converter<CategoryCommand, Category>
{
  @Synchronized
  @Nullable
  @Override
  public Category convert(CategoryCommand categoryCommand)
  {
    if(categoryCommand == null) return null;
    final Category cat = new Category();
    cat.setId(categoryCommand.getId());
    cat.setDescription(categoryCommand.getDescription());
    return cat;
  }
}
