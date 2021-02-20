package net.witerat.cafenatedsql.spi;

import net.witerat.cafenatedsql.api.Cafenated;
import net.witerat.cafenatedsql.api.Database;
import net.witerat.cafenatedsql.api.DatabaseFactory;
import net.witerat.cafenatedsql.api.driver.DriverCreationException;
import net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel;

/**
 * A factory for creating SimpleDatabase objects.
 */
public class SimpleDatabaseFactory implements DatabaseFactory {

  /**
   * {@inheritDoc}
   *
   * @see net.witerat.cafenatedsql.api.DatabaseFactory#
   *    newDatabase(
   *            net.witerat.cafenatedsql.api.driver.template
   *                    .TemplateEngineModel)
   */
  @Override
  public Database newDatabase(final TemplateEngineModel model)
      throws DriverCreationException {
    return new SimpleDatabase((String) model.get(Cafenated.PROVIDER_NAME),
      (String) model.get(Cafenated.CONNECTION_METHOD), model);
  }

}
