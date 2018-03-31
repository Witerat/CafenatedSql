package net.witerat.cafenatedsql.spi.driver;

import java.util.Collection;
import java.util.Iterator;

import net.witerat.cafenatedsql.api.driver.DialectDef;
import net.witerat.cafenatedsql.api.driver.template.ExpressionFailedException;
import net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel;

/**
 * The Class SimpleDialectSelector.
 */
public class SimpleDialectSelector implements DialectSelector {

  /** The name. */
  private final String dialectName;


  /** The driver loc. */
  private final DriverLocator driverLoc;

  /**
   * Instantiates a new simple dialect selector.
   *
   * @param name0
   *          the name
   * @param driverLoc0
   *          the driver loc
   */
  public SimpleDialectSelector(final String name0,
      final DriverLocator driverLoc0) {
    this.dialectName = name0;
    this.driverLoc = driverLoc0;
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * net.witerat.cafenatedsql.spi.driver.DialectSelector#getDialect(net.witerat.
   * cafenatedsql.api.driver.template.TemplateEngineModel)
   */
  @Override
  public DialectDef getDialect(final TemplateEngineModel model) {
    Collection<DialectDef> dialects = driverLoc.getDriver().getDialects();
    Iterator<DialectDef> i = dialects.iterator();
    if (i.hasNext()) {
      return i.next();
    } else {
      return null;
    }
  }

  /**
   * {@inheritDoc}
   *
   * @see
   * net.witerat.cafenatedsql.spi.driver.DialectSelector#getDialectName(net.
   * witerat.cafenatedsql.api.driver.template.TemplateEngineModel)
   */
  public String getDialectName(final TemplateEngineModel model)
      throws ExpressionFailedException {
    return dialectName;
  }

}
