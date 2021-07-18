package net.witerat.cafenatedsql.spi.driver.tags;

import java.util.Iterator;

import javax.xml.bind.annotation.XmlTransient;

import net.witerat.cafenatedsql.spi.driver.MacroLocator;

/**
 * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
 * TODO Check if this class is needed, it defines no concrete methods, or
 *      fields.
 */
@XmlTransient
public abstract class AbstractMacroLocator implements MacroLocator,
  Iterable<MacroTag> {

  /**
   * {@inheritDoc}
   *
   * @see net.witerat.cafenatedsql.spi.driver.MacroLocator#
   *    getMacro(java.lang.String)
   */
  @Override
  public abstract MacroTag getMacro(String name);

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Iterable#iterator()
   */
  public abstract Iterator<MacroTag> iterator();

}
