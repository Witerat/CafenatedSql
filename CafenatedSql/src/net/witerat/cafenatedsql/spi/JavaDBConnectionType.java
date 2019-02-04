package net.witerat.cafenatedsql.spi;

import java.util.Collection;
import java.util.HashMap;

import javax.naming.OperationNotSupportedException;

import net.witerat.cafenatedsql.api.driver.ConnectionType;
import net.witerat.cafenatedsql.api.driver.ParameterType;
import net.witerat.cafenatedsql.api.driver.UrlDef;
import net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel;
import net.witerat.cafenatedsql.spi.driver.Content;
import net.witerat.cafenatedsql.spi.driver.DialectSelector;
import net.witerat.cafenatedsql.spi.driver.Driver;
import net.witerat.cafenatedsql.spi.driver.DriverLocator;

/**
 * The class JavaDBConnectionType.
 */
public final class JavaDBConnectionType
  implements ConnectionType, DriverLocator {

  /**
   * The Class {@linkplain DefaultUrlDef}.
   */
  private final class DefaultUrlDef implements UrlDef, Content {

    /** Map of configured parameters. */
    private HashMap<String, ParameterType> parameters;


    /*
     * (non-Javadoc)
     *
     * @see
     * net.witerat.cafenatedsql.api.driver.UrlDef#add(net.witerat.cafenatedsql.
     * spi.driver.Content)
     */
    @Override
    public void add(final Content c) {
      throw new RuntimeException(new OperationNotSupportedException(
          "DefaultUrlDef content set by "
          + "JavaDBConnectionType#template property."));

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * net.witerat.cafenatedsql.api.driver.ParameterizedRequest#add(net.witerat.
     * cafenatedsql.api.driver.ParameterType)
     */
    @Override
    public void add(final ParameterType p) {
      if (parameters == null) {
        parameters = new HashMap<>();
      }
      parameters.put(p.getName(), p);
    }

    /*
     * (non-Javadoc)
     *
     * @see net.witerat.cafenatedsql.api.driver.UrlDef#getParameters()
     */
    @Override
    public Collection<ParameterType> getParameters() {
      return parameters.values();
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


    /*
     * (non-Javadoc)
     *
     * @see net.witerat.cafenatedsql.api.driver.UrlDef#setParameter(net.witerat.
     * cafenatedsql.api.driver.ParameterType)
     */
    @Override
    public void setParameter(final ParameterType type) {
      add(type);
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

  /** The driver locator. */
  private DriverLocator driverLoc = new DriverLocator() {
    /**
     * This implementation gets the driver associated with this connection.
     * type instance.
     *
     * @return the driver associated with this instanceof
     *          {@link JavaDBConnectionType}.
     */
    @Override
    public Driver getDriver() {
      return JavaDBConnectionType.this.driver;
    }
  };

  /** The name. */
  private final String name;

  /** The description. */
  private final String description;

  /** The template. */
  private final String template;

  /** The driver. */
  private final Driver driver;

  /** The url. */
  private UrlDef url = new DefaultUrlDef();

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
   * @see
   * net.witerat.cafenatedsql.api.driver.ConnectionType#getDialectSelector()
   */
  @Override
  public DialectSelector getDialectSelector() {
    return dialectSelector;
  }

  /**
   * Gets the driver that created this connection.
   * @return The driver that created this connection.
   * @see net.witerat.cafenatedsql.spi.driver.DriverLocator#getDriver()
   */
  public Driver getDriver() {
    return driver;
  }

  /**
   * The driver Locator property.
   * @return gets the driver locator.
   */
  public DriverLocator getDriverLoc() {
    return driverLoc;
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
   * net.witerat.cafenatedsql.api.driver.ConnectionType#setDialectSelector(net.
   * witerat.cafenatedsql.spi.driver.DialectSelector)
   */
  @Override
  public void setDialectSelector(final DialectSelector ds) {
    this.dialectSelector = ds;

  }

  /**
   * Sets the driver locator.
   * @param driverLoc0 the new locator.
   */
  protected void setDriverLoc(final DriverLocator driverLoc0) {
    this.driverLoc = driverLoc0;
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
