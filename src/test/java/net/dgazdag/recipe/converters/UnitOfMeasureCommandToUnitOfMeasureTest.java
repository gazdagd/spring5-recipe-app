package net.dgazdag.recipe.converters;

import net.dgazdag.recipe.commands.UnitOfMeasureCommand;
import net.dgazdag.recipe.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UnitOfMeasureCommandToUnitOfMeasureTest
{

  public static final String DESCRIPTION = "description";
  public static final Long ID = new Long(1L);

  UnitOfMeasureCommandToUnitOfMeasure converter;

  @Before
  public void setUp() throws Exception{
    converter = new UnitOfMeasureCommandToUnitOfMeasure();
  }

  @Test
  public void testNullParameter() throws Exception
  {
    assertNull(converter.convert(null));
  }

  @Test
  public void testEmptyObject() throws Exception
  {
    assertNotNull(converter.convert(new UnitOfMeasureCommand()));
  }

  @Test
  public void convert() throws Exception
  {
    //given
    UnitOfMeasureCommand command = new UnitOfMeasureCommand();
    command.setId(ID);
    command.setDescription(DESCRIPTION);

    //when
    UnitOfMeasure uom = converter.convert(command);

    //then
    assertNotNull(uom);
    assertEquals(ID, uom.getId());
    assertEquals(DESCRIPTION, uom.getDescription());
  }

}