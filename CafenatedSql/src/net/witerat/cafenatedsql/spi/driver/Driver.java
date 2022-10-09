package net.witerat.cafenatedsql.spi.driver;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import net.witerat.cafenatedsql.api.EntityManager;
import net.witerat.cafenatedsql.api.driver.ConnectionType;
import net.witerat.cafenatedsql.api.driver.DialectDef;
import net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel;
import net.witerat.cafenatedsql.api.driver.DDLEditor;

/**
 * The Class Driver represents a construct that provides abstraction of Data
 * Definition language.
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
   * @return the current model for the driver providing default values and
   *         context for operations related to his driver.
   */
  protected TemplateEngineModel getModel() {
    return model;
  }

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
    if (null == dialectsByName) {
      dialectsByName = new LinkedHashMap<>();
    }
    dialectsByName.put(dd.getId(), dd);
  }

  /**
   * Gets the dialects.
   *
   * @return the dialects
   */
  public Collection<DialectDef> getDialects() {
    if (null == dialectsByName) {
      return new LinkedHashSet<DialectDef>();
    }
    return dialectsByName.values();
  }

  /**
   * Gets the connection type.
   *
   * @param methodEx
   *          the method expression to extract the method from the context
   *          model.
   * @return the connection type
   */
  public ConnectionType getConnectionType(final String methodEx) {
    if (null == connectionTypesByMethod) {
      return null;
    }
    String method = (String) model.getByExpression(methodEx);
    return connectionTypesByMethod.get(method);
  }

  /**
   * @param ct the new connection type.
   */
  public void addConnectionType(final ConnectionType ct) {
    if (ct != null) {
      if (null == connectionTypesByMethod) {
        connectionTypesByMethod = new LinkedHashMap<>();
      }
      connectionTypesByMethod.put(ct.getName(), ct);
    }
  }

  /**
   * Return an editor suitable for committing changes to the database.
   * @param noun the type of entity affected;
   * @param operation the type of effect.
   * @return an editor that can marshal changes, select or construct
   *    templates for commitment to the underlying database.
   */
  public DDLEditor getEditor(final String noun,
      final EntityManager.Calculus operation) {
    // TODO Auto-generated method stub
    return null;
  }
}
