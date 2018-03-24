package net.witerat.cafenatedsql.api;

import net.witerat.cafenatedsql.api.driver.DialectDef;
import net.witerat.cafenatedsql.spi.driver.Driver;

/**
 * A factory for creating Dialect objects.
 */
public interface DialectFactory extends CafenatedFactory {

  /**
   * New dialect.
   *
   * @param driver
   *          the driver
   * @param name
   *          the name
   * @return the dialect def
   */
  DialectDef newDialect(Driver driver, String name);

}
