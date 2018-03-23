package net.witerat.cafenatedsql.spi.driver;

import net.witerat.cafenatedsql.api.driver.ConnectionType;
import net.witerat.cafenatedsql.api.driver.DialectDef;
import net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel;

/**
 * The Class Connection.
 *
 * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
 */
public class Connection {

  /** The type. */
  private ConnectionType type;

  /** The model. */
  private TemplateEngineModel model;

  /**
   * Instantiate a Connection object.
   *
   * @param type0
   *          the connection type.
   */
  public Connection(final ConnectionType type0) {
    this.type = type0;
  }

  /**
   * Gets the dialect that describes the SQL syntax used with this connection.
   * This implementation chooses a dialect based on the model and connection
   * type.
   *
   * @return The dialect appropriate for the model/connection type.
   *
   * @see DialectSelector#getDialect(TemplateEngineModel)
   */
  public DialectDef getDialect() {
    return type.getDialectSelector().getDialect(model);
  }

  /**
   * Gets the model of properties that describe the connection and how it is
   * used. .
   *
   * @return the model
   */
  public TemplateEngineModel getModel() {
    return model;
  }

  /**
   * Gets the type for the connection.
   *
   * @return the type
   */
  public ConnectionType getType() {
    return type;
  }

  /**
   * Sets the model of properties that describe the connection and how it is
   * used. .
   *
   * @param model0
   *          the new model
   */
  public void setModel(final TemplateEngineModel model0) {
    this.model = model0;
  }
}
