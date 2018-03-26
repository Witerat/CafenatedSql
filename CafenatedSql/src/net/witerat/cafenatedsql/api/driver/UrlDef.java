package net.witerat.cafenatedsql.api.driver;

import java.util.Collection;

import net.witerat.cafenatedsql.spi.driver.Content;

/**
 * The Interface UrlDef.
 */
public interface UrlDef extends ParameterizedRequest {

  /**
   * Gets the parameter types.
   *
   * @return the parameters
   */
  Collection<ParameterType> getParameters();

  /**
   * Sets a parameter type for for this URL.
   *
   * @param type
   *          the new parameter
   */
  void setParameter(ParameterType type);

  /**
   * Adds a content element to the URL definition.
   *
   * @param c
   *          the c
   */
  void add(Content c);

}
