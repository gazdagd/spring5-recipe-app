package net.dgazdag.recipe.services;

import net.dgazdag.recipe.commands.UnitOfMeasureCommand;

import java.util.Set;

public interface UnitOfMeasureService
{
  Set<UnitOfMeasureCommand> listAllUoms();
}
