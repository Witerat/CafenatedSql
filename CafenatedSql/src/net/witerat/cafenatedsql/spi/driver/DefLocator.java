package net.witerat.cafenatedsql.spi.driver;

import net.witerat.cafenatedsql.spi.driver.tags.MacroTag;

/**
 * The Interface MacroLocator.
 */
public interface DefLocator {

  /**
   * Gets the named macro definition tag.
   *
   * @param name
   *          The name of the def.
   * @return The def identified by <code>name</code>.
   */
  MacroTag getDef(String name);
}
