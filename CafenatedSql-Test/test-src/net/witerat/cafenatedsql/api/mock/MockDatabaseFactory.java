package net.witerat.cafenatedsql.api.mock;

import net.witerat.cafenatedsql.api.Database;
import net.witerat.cafenatedsql.api.DatabaseFactory;
import net.witerat.cafenatedsql.api.driver.DriverCreationException;
import net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel;

public class MockDatabaseFactory implements DatabaseFactory {

  @Override
  public Database newDatabase(TemplateEngineModel model) throws DriverCreationException {
    return new MockDatabase(model);
  }

}
