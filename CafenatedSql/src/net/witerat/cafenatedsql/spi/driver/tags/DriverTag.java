package net.witerat.cafenatedsql.spi.driver.tags;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * The Class DriverTag.
 */
@XmlRootElement(name = "driver", namespace = "-//org.witerat/cafenated/sql")
public class DriverTag extends AbstractMacroLocator {

  /** The definitions. */
  private Map<String, DefTag> defs;

  /** The dialects by id. */
  private Map<String, DialectTag> dialectsById;

  /** The connections by method. */
  private Map<String, ConnectionTag> connectionsByMethod;

  /** The ddl. */
  private DdlTag ddl;

  /** The dml. */
  private DmlTag dml;

  /** The meta property. */
  private MetaTag meta;

  /**
   * The templateEngine property.
   */
  private String templateEngine;

  /**
   * Adds the.
   *
   * @param def
   *          A definition.
   */
  public void add(final DefTag def) {
    defs.put(def.getName(), def);
  }

  /**
   * Gets the connections.
   *
   * @return the connections
   */
  public Collection<ConnectionTag> getConnections() {
    if (connectionsByMethod == null) {
      connectionsByMethod = new LinkedHashMap<>();
    }
    return connectionsByMethod.values();
  }

  /**
   * Gets the ddl.
   *
   * @return the ddl
   */
  @XmlElement(namespace = "-//org.witerat/cafenated/sql")
  public DdlTag getDdl() {
    return ddl;
  }

  /**
   * Gets the definitions.
   *
   * @return the definitions in a map keyed by name.
   */
  @XmlTransient
  public Map<String, DefTag> getDefs() {
    if (defs == null) {
      defs = new HashMap<>();
    }
    return defs;
  }

  /**
   * Gets the dml.
   *
   * @return the dml
   */
  @XmlElement(name = "dml", type = DmlTag.class,
      namespace = "-//org.witerat/cafenated/sql")
  public DmlTag getDml() {
    return dml;
  }

  /**
   * {@inheritDoc}
   * @see net.witerat.cafenatedsql.spi.driver.MacroLocator#
   *    getMacro(java.lang.String)
   */
  @Override
  public MacroTag getMacro(final String name) {
    return defs.get(name);
  }

  /**
   * Sets the connections.
   *
   * @param connection
   *          the new connection
   */
  @XmlElement(type = ConnectionTag.class, name = "connection",
      namespace = "-//org.witerat/cafenated/sql")
  public void setConnection(final ConnectionTag connection) {
    if (connectionsByMethod == null) {
      connectionsByMethod = new LinkedHashMap<>();
    }
    connectionsByMethod.put(connection.getMethod(), connection);
    connection.setDriver(this);
  }

  /**
   * Sets the dialect.
   *
   * @param dialect
   *          the new dialect
   */
  @XmlElement(name = "dialect", type = DialectTag.class,
      namespace = "-//org.witerat/cafenated/sql")
  public void setDialect(final DialectTag dialect) {
    if (dialectsById == null) {
      dialectsById = new HashMap<>();
    }
    dialectsById.put(dialect.getId(), dialect);
  }

  /**
   * Sets the ddl.
   *
   * @param ddl0
   *          the new ddl
   */
  public void setDdl(final DdlTag ddl0) {
    this.ddl = ddl0;
  }

  /**
   * Sets the defs.
   *
   * @param defs0
   *          the defs
   */
  public void setDefs(final Map<String, DefTag> defs0) {
    this.defs = defs0;
  }

  /**
   * Set a def element.
   *
   * @param def
   *          a new def tag
   */
  @XmlElement(name = "def", type = DefTag.class,
      namespace = "-//org.witerat/cafenated/sql")
  public void setDef(final DefTag def) {
    if (defs == null) {
      defs = new HashMap<>();
    }
    defs.put(def.getName(), def);
  }

  /**
   * Sets the dml.
   *
   * @param dml0
   *          the new dml
   */
  public void setDml(final DmlTag dml0) {
    this.dml = dml0;
  }

  /**
   * Get a dialect for the given name.
   * @param name
   *          name of dialect
   * @return the dialect associated with name
   */
  public DialectTag getDialectByName(final String name) {
    if (dialectsById != null) {
      return dialectsById.get(name);
    }
    return null;
  }

  /**
   * Gets the dialects.
   *
   * @return the dialects
   */
  public Collection<DialectTag> getDialects() {
    if (dialectsById == null) {
      dialectsById = new HashMap<>();
    }
    return dialectsById.values();
  }

  /**
   * @return The meta tag.
   */
  @XmlElement(namespace = "-//org.witerat/cafenated/sql", type = MetaTag.class)
  public MetaTag getMeta() {
    // TODO Auto-generated method stub
    return meta;
  }

  /**
   * @param meta0
   *          the meta tag.
   */
  public void setMeta(final MetaTag meta0) {
    this.meta = meta0;
  }

  /**
   * Get identifier for the template engine.
   * @return the template engine id.
   */
  @XmlElement(name = "template-engine", type = String.class,
      namespace = "-//org.witerat/cafenated/sql")
  public String getTemplateEngine() {
    return templateEngine;
  }

  /**
   * @param templateEngine0
   *          The template engine id.
   */
  public void setTemplateEngine(final String templateEngine0) {
    this.templateEngine = templateEngine0;
  }

  /**
   * {@inheritDoc}
   * @see net.witerat.cafenatedsql.spi.driver.tags.AbstractMacroLocator#
   *    iterator()
   */
  @Override
  public Iterator<MacroTag> iterator() {
    LinkedList<MacroTag> llMt = new LinkedList<>();
    for (DefTag dt : defs.values()) {
      for (MacroTag mt : dt.getMacros()) {
        llMt.add(mt);
      }
    }
    return llMt.iterator();
  }

}
