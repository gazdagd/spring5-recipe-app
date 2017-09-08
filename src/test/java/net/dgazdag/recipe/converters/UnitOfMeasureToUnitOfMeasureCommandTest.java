package net.dgazdag.recipe.converters;

import net.dgazdag.recipe.commands.UnitOfMeasureCommand;
import net.dgazdag.recipe.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UnitOfMeasureToUnitOfMeasureCommandTest
{
  public static final String DESCRIPTION = "description";
  public static final Long ID = new Long(1L);

  private UnitOfMeasureToUnitOfMeasureCommand converter;

  @Before
  public void setUp() throws Exception
  {
    converter = new UnitOfMeasureToUnitOfMeasureCommand();
  }

  @Test
  public void testNullParameter() throws Exception
  {
    assertNull(converter.convert(null));
  }

  @Test
  public void testEmptyObject() throws Exception
  {
    assertNotNull(converter.convert(new UnitOfMeasure()));
  }

  @Test
  public void convert() throws Exception
  {
    //given
    UnitOfMeasure uom = new UnitOfMeasure();
    uom.setId(ID);
    uom.setDescription(DESCRIPTION);
    //when
    UnitOfMeasureCommand uomc = converter.convert(uom);

    //then
    assertEquals(ID, uomc.getId());
    assertEquals(DESCRIPTION, uomc.getDescription());
  }
}