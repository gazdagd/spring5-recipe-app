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
public class CategoryToCategoryCommand implements Converter<Category, CategoryCommand>
{
  @Synchronized
  @Nullable
  @Override
  public CategoryCommand convert(Category category)
  {
    if(category == null) return null;

    final CategoryCommand cc = new CategoryCommand();
    cc.setId(category.getId());
    cc.setDescription(category.getDescription());
    return cc;
  }
}
