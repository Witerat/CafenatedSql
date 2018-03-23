package net.witerat.cafenatedsql.api.driver;

import net.witerat.cafenatedsql.spi.driver.Content;

/**
 * The Interface ConnectionUrl.
 */
public interface ConnectionUrl extends ParameterizedRequest {

  /**
   * Adds a content element to a connect url.
   *
   * @param c
   *          the c
   */
  void add(Content c);

}
