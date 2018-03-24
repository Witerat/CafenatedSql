package net.witerat.cafenatedsql.api;

import net.witerat.cafenatedsql.api.driver.DriverCreationException;
import net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel;

/**
 * A contract for creating databases.
 * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
 *
 */
public interface DatabaseFactory extends CafenatedFactory {

  /**
   * Create a new database using the given model containing connection
   * and DBMS language specification.
   * @param model the connection model
   * @return a new database that can facade the underling DBMS specified
   * in the model.
   * @throws DriverCreationException failed to create a database or
   * configure driver.
   */
  Database newDatabase(TemplateEngineModel model)
      throws DriverCreationException;

}
