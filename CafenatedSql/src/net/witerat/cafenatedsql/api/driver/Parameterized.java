package net.witerat.cafenatedsql.api.driver;

import java.util.Collection;

import net.witerat.cafenatedsql.spi.driver.tags.ParameterTag;

/**
 * The Interface Parameterized.
 */
public interface Parameterized {

  /**
   * Gets the parameters.
   *
   * @return the parameters
   */
  Collection<ParameterTag> getParams();
}
