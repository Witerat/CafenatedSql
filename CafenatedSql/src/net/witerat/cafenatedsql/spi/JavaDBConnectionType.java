package net.witerat.cafenatedsql.spi;

import java.util.Collection;

import net.witerat.cafenatedsql.api.driver.ConnectionType;
import net.witerat.cafenatedsql.api.driver.ParameterType;
import net.witerat.cafenatedsql.api.driver.UrlDef;
import net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel;
import net.witerat.cafenatedsql.spi.driver.Content;
import net.witerat.cafenatedsql.spi.driver.DialectSelector;
import net.witerat.cafenatedsql.spi.driver.Driver;
import net.witerat.cafenatedsql.spi.driver.DriverLocator;

/**
 * The Class JavaDBConnectionType.
 */
public final class JavaDBConnectionType
  implements ConnectionType, DriverLocator {

  /** The driver loc. */
  private DriverLocator driverLoc = new DriverLocator() {
    @Override
    public Driver getDriver() {
      return JavaDBConnectionType.this.driver;
    }
  };

  /**
   * The Class DefaultUrl.
   */
  private final class DefaultUrl implements UrlDef, Content {

    /*
     * (non-Javadoc)
     *
     * @see
     * net.witerat.cafenatedsql.api.driver.ParameterizedRequest#add(net.witerat.
     * cafenatedsql.api.driver.ParameterType)
     */
    @Override
    public void add(final ParameterType p) {
      // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * net.witerat.cafenatedsql.api.driver.UrlDef#add(net.witerat.cafenatedsql.
     * spi.driver.Content)
     */
    @Override
    public void add(final Content c) {
      // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     *
     * @see net.witerat.cafenatedsql.api.driver.UrlDef#setParameter(net.witerat.
     * cafenatedsql.api.driver.ParameterType)
     */
    @Override
    public void setParameter(final ParameterType type) {
      // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     *
     * @see net.witerat.cafenatedsql.api.driver.UrlDef#getParameters()
     */
    @Override
    public Collection<ParameterType> getParameters() {
      // TODO Auto-generated method stub
      return null;
    }


    /*
     * (non-Javadoc)
     *
     * @see net.witerat.cafenatedsql.spi.driver.Content#getText(net.witerat.
     * cafenatedsql.api.driver.template.TemplateEngineModel)
     */
    @Override
    public String getText(final TemplateEngineModel model) {
      StringBuilder sb = new StringBuilder();
      int ic = -1;
      int bi = 0;
      int si = -1;
      boolean expr = false;
      boolean lit = false;
      String t = template;
      for (char c : t.toCharArray()) {
        ic++;
        if (lit) {
          sb.append((String.valueOf(c) + "<>&tnrf")
              .charAt("<>&\t\n\r\f".indexOf(c) + 1));
          lit = false;
          bi = ic + 1;
        } else if (c == '\\') {
          lit = true;
          sb.append(t.substring(bi, si));
        } else if (c == '<') {
          sb.append(t.substring(bi, ic));
          si = ic + 1;
        } else if (c == '>') {
          if (si >= 0) {
            String id = t.substring(si, ic);
            Object p = null;
            if (expr) {
              p = model.getByExpression(id);
            } else {
              p = model.get(id);
            }
            if (p != null) {
              sb.append(p.toString());
            }
            si = -1;
            expr = false;
            bi = ic + 1;
          }
        } else if (c == '=' && (!expr) && si >= 0) {
          expr = true;
        }
      }
      sb.append(t.toString().substring(bi));
      return sb.toString();
    }
  }

  /** The Constant DERBY_SERVER. */
  public static final JavaDBConnectionType DERBY_SERVER =
      new JavaDBConnectionType("Derby Server", "connect to server",
      "jdbc:derby://<servername>:<port>)", null);

  /** The Constant DERBY_JNDI. */
  public static final JavaDBConnectionType DERBY_JNDI =
      new JavaDBConnectionType("Derby Server", "connect to JNDI",
      "jdbc:derby://<JNDIname>)", null);

  /** The name. */
  private final String name;

  /** The description. */
  private final String description;

  /** The template. */
  private final String template;

  /** The driver. */
  private final Driver driver;

  /** The url. */
  private UrlDef url = new DefaultUrl();

  /** The dialect selector. */
  private DialectSelector dialectSelector;

  /**
   * Instantiates a new java DB connection type.
   *
   * @param name0
   *          the name
   * @param description0
   *          the description
   * @param template0
   *          the template
   * @param driver0
   *          the driver
   */
  private JavaDBConnectionType(final String name0, final String description0,
      final String template0, final Driver driver0) {
    super();
    this.name = name0;
    this.description = description0;
    this.template = template0;
    this.driver = driver0;
  }

  /**
   * Gets the driver that created this connection.
   * @return The driver that created this connection.
   * @see net.witerat.cafenatedsql.spi.driver.DriverLocator#getDriver()
   */
  public Driver getDriver() {
    return driver;
  }

  /*
   * (non-Javadoc)
   *
   * @see net.witerat.cafenatedsql.api.driver.ConnectionType#getName()
   */
  @Override
  public String getName() {
    return name;
  }

  /*
   * (non-Javadoc)
   *
   * @see net.witerat.cafenatedsql.api.driver.ConnectionType#getDescription()
   */
  @Override
  public String getDescription() {
    return description;
  }

  /*
   * (non-Javadoc)
   *
   * @see net.witerat.cafenatedsql.api.driver.ConnectionType#getUrlDef()
   */
  @Override
  public UrlDef getUrlDef() {
    return url;
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * net.witerat.cafenatedsql.api.driver.ConnectionType#getDialectSelector()
   */
  @Override
  public DialectSelector getDialectSelector() {
    return dialectSelector;
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * net.witerat.cafenatedsql.api.driver.ConnectionType#setDialectSelector(net.
   * witerat.cafenatedsql.spi.driver.DialectSelector)
   */
  @Override
  public void setDialectSelector(final DialectSelector ds) {
    this.dialectSelector = ds;

  }

  /*
   * (non-Javadoc)
   *
   * @see
   * net.witerat.cafenatedsql.api.driver.ConnectionType#setUrlDef(net.witerat.
   * cafenatedsql.api.driver.UrlDef)
   */
  @Override
  public void setUrlDef(final UrlDef url0) {
    this.url = url0;
  }
}
