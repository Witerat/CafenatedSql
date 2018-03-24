package net.witerat.cafenatedsql.spi.driver;

import java.util.Collection;
import java.util.Map;

import net.witerat.cafenatedsql.api.driver.ConnectionType;
import net.witerat.cafenatedsql.api.driver.DialectDef;
import net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel;

/**
 * The Class Driver represents a construct that provides abstraction of
 * Data Definition language.
 */
public class Driver {
  static {
    ResourceProtocol.init();
  }

  /** The model. */
  private TemplateEngineModel model;

  /** The connection types by method. */
  private Map<String, ConnectionType> connectionTypesByMethod;

  /** The dialects by name. */
  private Map<String, DialectDef> dialectsByName;

  /**
   * Sets the model.
   *
   * @param model0
   *          the new model
   */
  public void setModel(final TemplateEngineModel model0) {
    this.model = model0;
  }

  /**
   * Adds a dialect to this driver.
   *
   * @param dd
   *          the dd
   */
  public void addDialect(final DialectDef dd) {
    dialectsByName.put(dd.getId(), dd);
  }

  /**
   * Gets the dialects.
   *
   * @return the dialects
   */
  public Collection<DialectDef> getDialects() {
    return dialectsByName.values();
  }

  /**
   * Gets the connection type.
   *
   * @param method
   *          the method
   * @return the connection type
   */
  public ConnectionType getConnectionType(final String method) {
    return connectionTypesByMethod.get(model.getByExpression(method));

  }
}
