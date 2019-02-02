package net.witerat.cafenatedsql.spi.driver;

import net.witerat.cafenatedsql.api.driver.ConnectionType;
import net.witerat.cafenatedsql.api.driver.UrlDef;

/**
 * The Class SimpleConnectionType.
 */
public class SimpleConnectionType implements ConnectionType {

  /** The dialect selector. */
  private DialectSelector dialectSelector;

  /** The name. */
  private String name;

  /** The description. */
  private String description;

  /** The url def. */
  private UrlDef urlDef;

  /*
   * (non-Javadoc)
   *
   * @see net.witerat.cafenatedsql.api.driver.ConnectionType#getDescription()
   */
  @Override
  public String getDescription() {
    return description;
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * net.witerat.cafenatedsql.api.driver.ConnectionType#getDialectSelector()
   */
  @Override
  public DialectSelector getDialectSelector() {
    return dialectSelector;
  }

  /*
   * (non-Javadoc)
   *
   * @see net.witerat.cafenatedsql.api.driver.ConnectionType#getName()
   */
  @Override
  public String getName() {
    return name;
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * net.witerat.cafenatedsql.api.driver.ConnectionType#setDialectSelector(net.
   * witerat.cafenatedsql.spi.driver.DialectSelector)
   */
  @Override
  public void setDialectSelector(final DialectSelector dialectSelector0) {
    this.dialectSelector = dialectSelector0;
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * net.witerat.cafenatedsql.api.driver.ConnectionType#setUrlDef(net.witerat.
   * cafenatedsql.api.driver.UrlDef)
   */
  @Override
  public void setUrlDef(final UrlDef url) {
    urlDef = url;

  }

  /*
   * (non-Javadoc)
   *
   * @see net.witerat.cafenatedsql.api.driver.ConnectionType#getUrlDef()
   */
  @Override
  public UrlDef getUrlDef() {
    return urlDef;
  }

  /**
   * @param name0 The new name of this connection type.
   */
  public void setName(final String name0) {
    name = name0;
  }
}
