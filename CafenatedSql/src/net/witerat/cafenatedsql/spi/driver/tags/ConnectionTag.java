package net.witerat.cafenatedsql.spi.driver.tags;

import java.util.Collection;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import net.witerat.cafenatedsql.api.driver.Parameterized;

/**
 * The Class ConnectionTag describe a connection method.
 */
@XmlType(name = "ConnectionType", namespace = "-//org.witerat/cafenated/sql")
public class ConnectionTag implements Parameterized {

  /** The method name. */
  private String method;

  /** The url. */
  private UrlTag url;

  /** The dialect. */
  private DialectTag dialect;

  /** the named dialect. */
  private UseDialectTag useDialect;

  /** The parameters descriptions. */
  private Collection<ParamTag> parameters;

  /** local reference to driver, which the driver will set when the connection
   * tag is added.
   */
  private DriverTag driverTag;

  /**
   * Gets the name of the connection method.
   *
   * @return the method
   */
  public String getMethod() {
    return method;
  }

  /**
   * Sets the name of the method.
   *
   * @param method0
   *          the new method
   */
  @XmlAttribute
  public void setMethod(final String method0) {
    this.method = method0;
  }

  /**
   * Gets the url template description.
   *
   * @return the url
   */
  @XmlElement(name = "url", namespace = "-//org.witerat/cafenated/sql",
      type = UrlTag.class)
  public UrlTag getUrl() {
    return url;
  }

  /**
   * Sets the url template description.
   *
   * @param url0
   *          the new url
   */
  public void setUrl(final UrlTag url0) {
    this.url = url0;
  }

  /**
   * Sets the associated dialect.
   *
   * @param dialect0
   *          the new dialect
   */
  @XmlElement(name = "dialect", namespace = "-//org.witerat/cafenated/sql",
      type = DialectTag.class)
  void setDialect(final DialectTag dialect0) {
    this.dialect = dialect0;
  }

  /**
   * Gets the associatd dialect.
   *
   * @return the dialect
   */
  public DialectTag getDialect() {
    if (dialect == null) {
      if (useDialect != null) {
        dialect = driverTag.getDialectByName(useDialect.getName());
        if (dialect == null) {
          dialect = driverTag.getDialectByName(useDialect.getDefaultName());
        }
      }
    }
    return dialect;
  }

  /**
   * {@inheritDoc}
   */
  public Collection<ParamTag> getParams() {
    return parameters;
  }

  /**
   * The dialectRefence.
   * @return the dialectrefence or <code>null</code> if none set.
   */
  @XmlElement(name = "use-dialect", namespace = "-//org.witerat/cafenated/sql",
      type = UseDialectTag.class)
  public UseDialectTag getUseDialect() {
    return useDialect;
  }


  /**
   * Set dialect reference.
   * @param useDialect0 the refernece.
   */
  public void setUseDialect(final UseDialectTag useDialect0) {
    this.useDialect = useDialect0;
  }

  /**
   * @param driverTag0 the driver parent.
   */
  public void setDriver(final DriverTag driverTag0) {
    this.driverTag = driverTag0;
  }
}
