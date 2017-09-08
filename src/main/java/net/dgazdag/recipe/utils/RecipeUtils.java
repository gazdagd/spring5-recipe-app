/**
 * Copyright © 2017 Ericsson. A written permission from Ericsson is required to use this software.
 */
package net.dgazdag.recipe.utils;

public class RecipeUtils
{
  public static byte[] toPrimitive(Byte[] byteArray){
    byte[] primitiveArray = new byte[byteArray.length];

    int i = 0;
    for(Byte wrappedByte : byteArray){
      primitiveArray[i++] = wrappedByte;
    }
    return primitiveArray;
  }
}
