package net.witerat.cafenatedsql.spi.driver.tags;

import java.util.Iterator;

import javax.xml.bind.annotation.XmlTransient;

import net.witerat.cafenatedsql.spi.driver.DefLocator;

/**
 * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
 * TODO Check if this class is needed, it defines no concrete methods, or
 *      fields.
 */
@XmlTransient
public abstract class AbstractDefLocator implements DefLocator,
  Iterable<DefTag> {

  /**
   * {@inheritDoc}
   *
   * @see net.witerat.cafenatedsql.spi.driver.DefLocator#
   *    getDef(java.lang.String)
   */
  @Override
  public abstract DefTag getDef(String name);

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Iterable#iterator()
   */
  public abstract Iterator<DefTag> iterator();

}
