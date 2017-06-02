package net.witerat.cafenatedsql.spi.driver.tags;

import javax.xml.bind.annotation.XmlTransient;

import net.witerat.cafenatedsql.spi.driver.MacroLocator;

/**
 * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
 *
 */
@XmlTransient
public abstract class AbstractMacroLocator implements MacroLocator {

	/* (non-Javadoc)
	 * @see net.witerat.cafenatedsql.spi.driver.MacroLocator#getMacro(java.lang.String)
	 */
	@Override
	public abstract MacroTag getMacro(String name);

}
