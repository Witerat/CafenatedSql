package net.witerat.cafenatedsql.api.driver;

import net.witerat.cafenatedsql.spi.driver.DialectSelector;

/**
 * The ConnectionType interface.
 */
public interface ConnectionType {

  /**
   * Gets the name for the type.
   *
   * @return the name
   */
  String getName();

  /**
   * Gets the human readable description of the connection type.
   *
   * @return the description
   */
  String getDescription();

  /**
   * Gets the dialect selector.
   *
   * @return the dialect selector
   */
  DialectSelector getDialectSelector();

  /**
   * Sets the dialect selector.
   *
   * @param ds
   *          the new dialect selector
   */
  void setDialectSelector(DialectSelector ds);

  /**
   * Sets the URL definition.
   *
   * @param url
   *          the new URL definition
   */
  void setUrlDef(UrlDef url);

  /**
   * Gets the URL definition.
   *
   * @return the URK definition
   */
  UrlDef getUrlDef();
}
