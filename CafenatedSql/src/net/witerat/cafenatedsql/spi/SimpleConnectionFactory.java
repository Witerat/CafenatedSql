/*
 * @author nos $}John Hutcheson &lt;witerat.test@gmail.com&gt;
 * @created 10-Sep-2022 00:37:54
 */
package net.witerat.cafenatedsql.spi;

import com.jolbox.bonecp.BoneCPConfig;
import com.jolbox.bonecp.BoneCPDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.InvalidPropertiesFormatException;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.sql.DataSource;

//import org.apache.geronimo.mail.util.StringBufferOutputStream;

import net.witerat.cafenatedsql.api.ConnectionFactory;
import net.witerat.cafenatedsql.api.driver.ConnectionType;
import net.witerat.cafenatedsql.api.driver.template.SimplePropertiesModel;
import net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel;
import net.witerat.cafenatedsql.spi.driver.Driver;

/**
 * This connection factory implementation uses BoneJS to establish connections
 * to database repositories.
 *
 * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
 *
 */
public class SimpleConnectionFactory implements ConnectionFactory, DataSource {
  /** The properties. This property contains the property values that
   * configure the underlyng connection protocol.
   * @TODO The underlying {@link Properties} object is passed to the
   *  BoneCP connection pool API in {@link #configureDataSource()}, limiting
   *   name/value pairs to type string/string.
   *  <table>
   *  <tr>
   *  <th>Plan "A"</th><td>Abstract out BoneCP and allow alternate connection
   *   property modelling and connection strategy</td>
   *  </tr><tr>
   *  <th>Plan "B"</th><td>Down-grade to Properties</td>
   *  </tr><tr>
   *  <th>Plan "C"</th><td>Do nothing, allow other classes to address this
   *  conflict.</td>
   *  </tr>
   *  </table>
   *  @Date 10/9//2022 NZST.
   */
  private TemplateEngineModel properties;

  /** The data source. */
  private DataSource dataSource = this;

  /** The connection type. */
  private ConnectionType connectionType;

  /** The log writer. */
  private PrintWriter logWriter;

  /** The parent log writer. */
  private Logger parentLogWriter;

  /** The login time out. */
  private int loginTimeOut;

  /** The names of required properties. */
  private HashSet<String> requiredProperties = null;

  /**
   * Load from resource.
   *
   * @param bundleName
   *     The resource bundle name.
   * @return A simple connection factory with properties assigned from a
   *     resource bundle.
   * @throws IOException
   *     Signals that an I/O exception has occurred.
   */
  static SimpleConnectionFactory loadFromResource(
      final String bundleName)
      throws InvalidPropertiesFormatException, IOException {
    ResourceBundle bundle = ResourceBundle.getBundle(bundleName, Locale.ROOT);
    Enumeration<String> keys = bundle.getKeys();
    Properties properties = new Properties();
    while (keys.hasMoreElements()) {
      String k = keys.nextElement();
      String v = bundle.getString(k);
      properties.put(k, v);
    }
    return new SimpleConnectionFactory(properties);
  }

  /**
   * Load from resource stream.
   *
   * @param driver
   *          the default driver
   * @param cl
   *          the cl
   * @param resourceName
   *          the resource name
   * @return the simple connection factory
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  static SimpleConnectionFactory loadFromResourceStream(
      final Driver driver, final ClassLoader cl, final String resourceName)
          throws IOException {
    InputStream in = cl.getResourceAsStream(resourceName);
    SimplePropertiesModel model = new SimplePropertiesModel();
    if (driver != null) {
      model.set(DRIVER, driver);
    }
    model.getProperties().load(in);
    return new SimpleConnectionFactory(model.getProperties());

  }

  /**
   * A convenience method for establishing a factory using an XML properties
   * stream.
   *
   * @param inputStream
   *          the input stream
   * @return the simple connection factory
   * @throws InvalidPropertiesFormatException
   *           the invalid properties format exception
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  static SimpleConnectionFactory loadFromXML(final InputStream inputStream)
      throws InvalidPropertiesFormatException, IOException {
    return new SimpleConnectionFactory(inputStream, true);
  }

  /**
   * Load from XML resource stream.
   *
   * @param driver
   *          the driver
   * @param rClass
   *          the class used to locate the resource.
   * @return the simple connection factory
   * @throws InvalidPropertiesFormatException
   *           the invalid properties format exception
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  static SimpleConnectionFactory loadFromXMLResourceStream(
      final Driver driver, final Class<?> rClass)
      throws InvalidPropertiesFormatException, IOException {
    String rName = //"/" +
      rClass.getName().replace(".", "/") + ".properties";
    Logger.getAnonymousLogger().info(rName);
    InputStream in = rClass.getClassLoader().getResourceAsStream(rName);
    SimplePropertiesModel model = new SimplePropertiesModel();
    if (driver != null) {
      model.set(DRIVER, driver);
    }

    if (in != null) {
      model.getProperties().loadFromXML(in);
    } else {
      in = rClass.getClassLoader().getResourceAsStream(
          rClass.getName().replace(".", "/") + ".properties.xml");
      model.getProperties().loadFromXML(in);
    }

    return new SimpleConnectionFactory(model.getProperties());
  }

  /**
   * Load from XML resource stream.
   *
   * @param driver
   *          the driver
   * @param cl
   *          the cl
   * @param resourceName
   *          the resource name
   * @return the simple connection factory
   * @throws InvalidPropertiesFormatException
   *           the invalid properties format exception
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  static SimpleConnectionFactory loadFromXMLResourceStream(
      final Driver driver, final ClassLoader cl,
      final String resourceName)
          throws InvalidPropertiesFormatException, IOException {
    InputStream in = cl.getResourceAsStream(resourceName);
    SimplePropertiesModel model = new SimplePropertiesModel();
    if (driver != null) {
      model.set(DRIVER, driver);
    }
    model.getProperties().loadFromXML(in);
    return new SimpleConnectionFactory(model.getProperties());
  }

  /**
   * Instantiate a(n) SimpleConnectionFactory object.
   */
  public SimpleConnectionFactory() {
    properties = new SimplePropertiesModel();
  }

  /**
   * Instantiate a(n) SimpleConnectionFactory object extracting connection
   * properties from an input stream.
   *
   * @param inputStream
   *          a source for connection properties.
   * @throws IOException
   *           fail to read the properties file.
   */
  public SimpleConnectionFactory(
      final InputStream inputStream) throws IOException {
    properties = new SimplePropertiesModel();
    ((SimplePropertiesModel) properties).getProperties().load(inputStream);
  }

  /**
   * Instantiate a(n) SimpleConnectionFactory object extracting properties from
   * n input that may contain XML.
   *
   * @param in
   *          a source for the properties.
   * @param isXML
   *          true if the source file is expected to be in XML format.
   * @throws InvalidPropertiesFormatException
   *           failed to read the properties file
   * @throws IOException
   *           failed to access the input strean while read the properties file.
   */
  public SimpleConnectionFactory(
      final InputStream in, final boolean isXML)
      throws InvalidPropertiesFormatException, IOException {
    SimplePropertiesModel model = new SimplePropertiesModel();

    if (isXML) {
      model.getProperties().loadFromXML(in);
    } else {
      model.getProperties().load(in);
    }
    this.properties = model;
  }

  /**
   * Instantiate a(n) SimpleConnectionFactory object using pre-loaded
   * properties..
   *
   * @param model
   *          connection properties for new connections.
   */
  public SimpleConnectionFactory(final Properties model) {
    this.properties = new SimplePropertiesModel(model);
  }

  /**
   * Configure data source.
   *
   * @throws Exception
   *           the exception
   */
  private void configureDataSource() throws Exception {
    SimplePropertiesModel spm = (SimplePropertiesModel) properties;
    BoneCPConfig boneCfg = new BoneCPConfig(spm.getProperties());
    BoneCPDataSource boneCpSource = new BoneCPDataSource(boneCfg);
    dataSource = boneCpSource;
  }

  /**
   * {@inheritDoc}
   *
   * @see net.witerat.cafenatedsql.api.ConnectionFactory#connect()
   */
  @Override
  public Connection connect() {
    try {
      return connect0();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * establish a connection from associated properties.
   *
   * @return the new connection.
   * @throws SQLException
   *           failure to connect.
   */
  public Connection connect0() throws SQLException {
    validate();
    SimplePropertiesModel properties0 = (SimplePropertiesModel) properties;
    if (dataSource == null) {
      try {
        configureDataSource();
      } catch (Exception e) {
        throw new SQLException("Configuration failed", e);
      }
    }
    final Properties strProps = properties0.getProperties();
    if (strProps.containsKey(USER_NAME)
        || strProps.containsKey(PASSWORD)) {
      String un = strProps.getProperty(USER_NAME);
      String pw = strProps.getProperty(PASSWORD);
      if (dataSource != null && dataSource != this) {
        return dataSource.getConnection(un, pw);
      } else {
        String url = strProps.getProperty("connection_url");
        return DriverManager.getConnection(url, un, pw);
      }
    }
    if (dataSource != null && dataSource != this) {
      return dataSource.getConnection();
    } else {
      String url = strProps.getProperty("connection_url");
      return DriverManager.getConnection(url);
    }
  }

  /**
   * {@inheritDoc}
   */
  public Connection getConnection() throws SQLException {
    return connect0();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Connection getConnection(
      final String username, final String password) throws SQLException {
    if (username != null) {
      setProperty(USER_NAME, username);
    }
    if (password != null) {
      setProperty(PASSWORD, password);
    }
    return connect0();
  }

  /**
   * Get the current connection type which specifies the connection URL
   * template.
   *
   * @return the current connecion typt;
   */
  public ConnectionType getConnectionType() {
    return connectionType;
  }

  /**
   * {@inheritDoc}
   *
   * @see javax.sql.CommonDataSource#getLoginTimeout()
   */
  @Override
  public int getLoginTimeout() throws SQLException {
    loginTimeOut = 0;
    if (dataSource == this) {
      return loginTimeOut;
    }
    return dataSource.getLoginTimeout();
  }

  /**
   * {@inheritDoc}
   *
   * @see javax.sql.CommonDataSource#getLogWriter()
   */
  @Override
  public PrintWriter getLogWriter() throws SQLException {
    if (dataSource == this) {
      return logWriter;
    }
    return dataSource.getLogWriter();
  }

  /**
   * {@inheritDoc}
   *
   * @see javax.sql.CommonDataSource#getParentLogger()
   */
  @Override
  public Logger getParentLogger() throws SQLFeatureNotSupportedException {
    if (dataSource == this) {
      return parentLogWriter;
    }
    return dataSource.getParentLogger();
  }

  /**
   * {@inheritDoc}
   *
   * @see net.witerat.cafenatedsql.api.ConnectionFactory#
   *    getProperty(java.lang.Object)
   */
  @Override
  public Object getProperty(final Object property) {
      return getProperty((String) property);
//    final String p = property == null ? null : property.toString();
//    final String ser = (String)properties.get(p);
//    Object value;
//    if ("null".equals(ser)) {
//      value = null;
//    } else if ("true".equals(ser)) {
//      value = Boolean.TRUE;
//    } else if ("false".equals(ser)) {
//      value = Boolean.FALSE;
//    } else {
//      final String sser = ser.substring(1);
//      switch(ser.charAt(0)){
//      case '\'':
//        value = sser;
//        break;
//      case 'B':
//        value = Byte.decode(sser);
//        break;
//      case 'S':
//        value = Short.decode(sser);
//        break;
//      case 'I':
//        value = Integer.decode(sser);
//        break;
//      case 'L':
//        value = Long.decode(sser);
//        break;
//      case 'F':
//        value = Float.valueOf(sser);
//        break;
//      case 'D':
//        value = Double.valueOf(sser);
//        break;
//      case 'N':
//        value = new BigInteger(sser);
//        break;
//      case 'G':
//        value = new BigDecimal(sser);
//        break;
//      case '{':
//        Decoder b64e = Base64.getDecoder();
//        StringReader sr = new StringReader(sser);
//        ByteArrayInputStream bais = new ByteArrayInputStream(ser.getBytes());
//        try {
//          ObjectInputStream ois = new ObjectInputStream(bais);
//          value = ois.readObject();
//        } catch (ClassNotFoundException|IOException e) {
//          throw new RuntimeException(e);
//        }
//        break;
//        default:
//          throw new IllegalStateException(
//            "type character not recognised "
//              + Integer.toHexString(ser.codePointAt(0)));
//      }
//    }
//    return value;
  }

  /**
   * Gets the name property.
   *
   * @param property
   *          name of the property
   * @return the value of the property.
   */
  public Object getProperty(final String property) {
    return properties.get(property);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<?> getPropertyType(final Object property) {
    return properties.getPropertyType(property);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isPropertyRequired(final Object property) {
    if (requiredProperties == null) {
      return false;
    }
    return requiredProperties.contains(property);
  }

  /**
   * {@inheritDoc}
   *
   * @see java.sql.Wrapper#isWrapperFor(java.lang.Class)
   */
  @Override
  public boolean isWrapperFor(final Class<?> arg0) throws SQLException {
    if (null == dataSource) {
      return false;
    }
    if (arg0.isInstance(dataSource)) {
      return true;
    }
    return dataSource.isWrapperFor(arg0);
  }

  /**
   * Set the connection type, using a specified connection template.
   *
   * @param connectionType0
   *          the new connection type
   */
  public void setConnectionType(final ConnectionType connectionType0) {
    this.connectionType = connectionType0;
  }

  /**
   * {@inheritDoc}
   *
   * @see javax.sql.CommonDataSource#setLoginTimeout(int)
   */
  @Override
  public void setLoginTimeout(final int seconds) throws SQLException {
    loginTimeOut = seconds;
    if (dataSource != this) {
      dataSource.setLoginTimeout(seconds);
    }

  }

  /**
   * {@inheritDoc}
   *
   * @see javax.sql.CommonDataSource#setLogWriter(java.io.PrintWriter)
   */
  @Override
  public void setLogWriter(final PrintWriter pw) throws SQLException {
    if (dataSource == this) {
      logWriter = pw;
    } else {
      dataSource.setLogWriter(pw);
    }
  }

  /**
   * {@inheritDoc}
   *
   * @see net.witerat.cafenatedsql.api.ConnectionFactory#setProperty(java.lang.
   * Object, java.lang.Object)
   */
  @Override
  public void setProperty(final Object property, final Object value) {
    setProperty((String) property, value);
//    String ser = null;
//    if (value == null) {
//      ser = "null";
//    } else if (Boolean.TRUE.equals(value)) {
//      ser = "true";
//    } else if (Boolean.FALSE.equals(value)) {
//      ser = "false";
//    } else if (value instanceof String) {
//      ser = "'"+value.toString();
//    } else if (value instanceof Byte) {
//      ser = "B"+Byte.toString((byte) value);
//    } else if (value instanceof Short) {
//      ser = "S"+Short.toString((short) value);
//    } else if (value instanceof Integer) {
//      ser = "I"+Integer.toString((int) value);
//    } else if (value instanceof Long) {
//      ser = "L"+Long.toString((long) value);
//    } else if (value instanceof Float) {
//      ser = "F"+Float.toString((float) value);
//    } else if (value instanceof Double) {
//      ser = "D"+Double.toString((double) value);
//    } else if (value instanceof BigInteger) {
//      ser = "N"+((BigInteger)value).toString(10);
//    } else if (value instanceof BigDecimal) {
//      ser = "G"+((BigDecimal)value).toString();
//    }else {
//      StringBuffer sb = new StringBuffer("{");
//      StringBufferOutputStream sbos=new StringBufferOutputStream(sb);
//      try {
//        ObjectOutputStream oos=new ObjectOutputStream(sbos);
//        oos.writeObject(value);
//      } catch (IOException e) {
//        throw new RuntimeException(e);
//      }
//      Encoder b64e = Base64.getEncoder();
//      ser = b64e.encodeToString(sb.toString().getBytes());
//    }
//    setProperty((String) property, ser);

  }

  /**
   * Assigns a new value to a property.
   *
   * @param property
   *          The name of a property.
   * @param value
   *          A new value of the name property.
   */
  public void setProperty(final String property, final Object value) {
    properties.set(property, value);
  }

  /**
   * {@inheritDoc}
   *
   * @see java.sql.Wrapper#unwrap(java.lang.Class)
   */
  @SuppressWarnings("unchecked")
  @Override
  public <T> T unwrap(final Class<T> arg0) throws SQLException {
    if (arg0.isInstance(dataSource)) {
      return (T) dataSource;
    }
    if (dataSource != null) {
      return  dataSource.unwrap(arg0);
    }
    return null;
  }

  /**
   * Sets the property required.
   *
   * @param name
   *          the property name
   * @param mandate
   *          the property's mandate
   */
  void setPropertyRequired(final String name, final boolean mandate) {
    if (requiredProperties == null) {
      if (!mandate) {
        return;
      }
      requiredProperties = new HashSet<String>();
    }
    if (mandate) {
      requiredProperties.add(name);
    } else {
      requiredProperties.remove(name);
      if (requiredProperties.isEmpty()) {
        requiredProperties = null;
      }
    }
  }

  /**
   * Validate.
   * @throws SQLException when at least one required property is not set.
   */
  private void validate() throws SQLException {
    if (properties instanceof SimplePropertiesModel) {
       SimplePropertiesModel spm = (SimplePropertiesModel) properties;
       Properties p = spm.getProperties();
       if (null != requiredProperties) {
         for (String rp : requiredProperties) {
           if (!p.containsKey(rp)) {
             throw new SQLException("Missing required property: " + rp);
           }
         }
       }
    } else {
      TemplateEngineModel p = properties;
      if (null != requiredProperties) {
        for (String rp : requiredProperties) {
          if (null == p.get(rp)) {
            throw new SQLException("Missing required property: " + rp);
          }
        }
      }
    }
  }

}

