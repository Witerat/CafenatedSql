package net.witerat.cafenatedsql.spi;

import net.witerat.cafenatedsql.api.ConnectionFactory;
import net.witerat.cafenatedsql.api.Database;
import net.witerat.cafenatedsql.api.DatabaseFactory;
import net.witerat.cafenatedsql.api.Provider;
import net.witerat.cafenatedsql.api.ProviderRegistrar;
import net.witerat.cafenatedsql.api.driver.DriverCreationException;
import net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel;

/**
 * A factory for Creating {@link SimpleDatabase} objects.
 * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
 *
 */
public class SimpleDatabaseFactory implements DatabaseFactory {

  /*
   * (non-Javadoc)
   *
   * @see net.witerat.cafenatedsql.api.DatabaseFactory#newDatabase(net.witerat.
   * cafenatedsql.api.driver.template.TemplateEngineModel)
   */
  @Override
  public Database newDatabase(final TemplateEngineModel model)
      throws DriverCreationException {
    String ddlDriver = (String) model.get("driver");
    String connectionMethod = (String) model.get("method");
    ProviderRegistrar rpr = ProviderRegistrar.ROOT_REGISTRAR;
    Provider p = rpr.getProvider(ddlDriver);
    ConnectionFactory cf = p.getConnectionFactory(connectionMethod);
    return new SimpleDatabase(p, cf, model);
  }

}
