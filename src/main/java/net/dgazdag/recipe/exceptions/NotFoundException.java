/**
 * Copyright © 2017 Ericsson. A written permission from Ericsson is required to use this software.
 */
package net.dgazdag.recipe.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException
{

  public NotFoundException()
  {
    super();
  }

  public NotFoundException(String message)
  {
    super(message);
  }

  public NotFoundException(String message, Throwable cause)
  {
    super(message, cause);
  }
}
