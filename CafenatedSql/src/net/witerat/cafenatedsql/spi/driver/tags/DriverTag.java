/*
 * @author nos $}John Hutcheson &lt;witerat.test@gmail.com&gt;
 * @created 04-Sep-2022 23:37:38
 */
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
 * The DriverTag class.
 *
 * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
 */
@XmlRootElement(name = "driver", namespace = "-//org.witerat/cafenated/sql")
public class DriverTag extends AbstractDefLocator {

  /** The definitions. */
  private Map<String, DefTag> defs;

  /** The dialects by id. */
  private Map<String, DialectTag> dialectsById;

  /** The connections by method. */
  private Map<String, ConnectionTag> connectionsByMethod;

  /** The DDL. */
  private DdlTag ddl;

  /** The DML. */
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
   * Gets the DDL.
   *
   * @return the DDL
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
   * Gets the DML.
   *
   * @return The DML.
   */
  @XmlElement(name = "dml", type = DmlTag.class,
      namespace = "-//org.witerat/cafenated/sql")
  public DmlTag getDml() {
    return dml;
  }

  /**
   * {@inheritDoc}
   * @see net.witerat.cafenatedsql.spi.driver.DefLocator
   *    #getDef(java.lang.String)
   */
  @Override
  public DefTag getDef(final String name) {
    if (defs == null) {
      return null;
    }
    return defs.get(name);
  }

  /**
   * Adds a connection description.
   *
   * @param connection
   *          A new connection description
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
   * Adds a dialect.
   *
   * @param dialect
   *          A new dialect
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
   * Sets the DDL for this driver description.
   *
   * @param ddl0
   *          A new DDL.
   */
  public void setDdl(final DdlTag ddl0) {
    this.ddl = ddl0;
  }

  /**
   * Sets the macro definitions collection.
   *
   * @param defs0
   *          the macro definitions.
   */
  public void setDefs(final Map<String, DefTag> defs0) {
    this.defs = defs0;
  }

  /**
   * Add a macro definition element.
   *
   * @param def
   *          A new macro definition tag.
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
   * Sets the DML.
   *
   * @param dml0
   *          The new DML.
   */
  @XmlElement(name = "dml", type = DmlTag.class,
      namespace = "-//org.witerat/cafenated/sql")
  public void setDML(final DmlTag dml0) {
    this.dml = dml0;
  }

  /**
   * Get a dialect for the given name.
   * @param name
   *          The name of a dialect.
   * @return The dialect associated with <code>name</code>.
   */
  public DialectTag getDialectByName(final String name) {
    if (dialectsById == null) {
      return null;
    }
    return dialectsById.get(name);
  }

  /**
   * Gets the dialects.
   *
   * @return The dialects.
   */
  public Collection<DialectTag> getDialects() {
    if (dialectsById == null) {
      dialectsById = new HashMap<>();
    }
    return dialectsById.values();
  }

  /**
   * Gets the meta tag.
   *
   * @return The meta tag.
   */
  @XmlElement(namespace = "-//org.witerat/cafenated/sql",
      type = MetaTag.class)
  public MetaTag getMeta() {
    return meta;
  }

  /**
   * Sets the meta tag.
   *
   * @param meta0
   *          The meta tag.
   */
  public void setMeta(final MetaTag meta0) {
    this.meta = meta0;
  }

  /**
   * Get identifier for the template engine.
   *
   * @return The template engine id.
   */
  @XmlElement(name = "template-engine", type = String.class,
      namespace = "-//org.witerat/cafenated/sql")
  public String getTemplateEngine() {
    return templateEngine;
  }

  /**
   * Assigns the name of a template engine.
   *
   * @param templateEngine0
   *          The template engine id.
   */
  public void setTemplateEngine(final String templateEngine0) {
    this.templateEngine = templateEngine0;
  }

  /**
   * Returns an iterator of {@link DefTag}s on this {@linkplain DriverTag}.
   *
   * @return An iterator of MacroTag objects.
   * @see net.witerat.cafenatedsql.spi.driver.tags.AbstractDefLocator
   *    #iterator()
   */
  @Override
  public Iterator<DefTag> iterator() {
    LinkedList<DefTag> llMt = new LinkedList<>();
    llMt.addAll(defs.values());
    return llMt.iterator();
  }

}
