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

  /** The parameters descriptions. */
  private Collection<ParameterTag> parameters;

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
    return dialect;
  }

  /**
   * {@inheritDoc}
   */
  public Collection<ParameterTag> getParams() {
    return parameters;
  }
}
