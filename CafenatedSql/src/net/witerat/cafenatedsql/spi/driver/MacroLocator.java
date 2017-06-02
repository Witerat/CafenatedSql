package net.witerat.cafenatedsql.spi.driver;

import net.witerat.cafenatedsql.spi.driver.tags.MacroTag;

// TODO: Auto-generated Javadoc
/**
 * The Interface MacroLocator.
 */
public interface MacroLocator {
	
	/**
	 * Gets the macro.
	 *
	 * @param name the name
	 * @return the macro
	 */
	MacroTag getMacro(String name);
}
