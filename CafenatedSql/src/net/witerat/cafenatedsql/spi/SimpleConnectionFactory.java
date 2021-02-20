package net.witerat.cafenatedsql.spi;

import com.jolbox.bonecp.BoneCPConfig;
import com.jolbox.bonecp.BoneCPDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Enumeration;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.sql.DataSource;

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
  /** The properties. */
  private TemplateEngineModel properties;

  /** The data source. */
  private DataSource dataSource = this;

  /** The connection type. */
  private ConnectionType connectionType;

  /**
   * Load from resource.
   *
   * @param packageName
   *          the package name
   * @param resourceName
   *          the resource name
   * @return the simple connection factory
   * @throws InvalidPropertiesFormatException
   *           the invalid properties format exception
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  static SimpleConnectionFactory loadFromResource(
      final String packageName, final String resourceName)
      throws InvalidPropertiesFormatException, IOException {
    ResourceBundle bundle = ResourceBundle.getBundle(packageName);
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
   * @param c
   *          the c
   * @return the simple connection factory
   * @throws InvalidPropertiesFormatException
   *           the invalid properties format exception
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  static SimpleConnectionFactory loadFromXMLResourceStream(
      final Driver driver, final Class<?> c)
      throws InvalidPropertiesFormatException, IOException {
    InputStream in = c.getClassLoader().getResourceAsStream(
        c.getName() + ".properties");
    SimplePropertiesModel model = new SimplePropertiesModel();
    if (driver != null) {
      model.set(DRIVER, driver);
    }

    if (in == null) {
      model.getProperties().loadFromXML(in);
    } else {
      in = c.getClassLoader().getResourceAsStream(
          c.getName() + ".properties.xml");
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
   *           failure to coonect.
   */
  public Connection connect0() throws SQLException {
    SimplePropertiesModel properties0 = (SimplePropertiesModel) properties;
    validate();
    if (dataSource == null) {
      try {
        configureDataSource();
      } catch (Exception e) {
        throw new SQLException("Configuration failed", e);
      }
    }
    if (properties0.getProperties().containsKey(USER_NAME)
        || properties0.getProperties().containsKey(PASSWORD)) {
      String un = properties0.getProperties().getProperty(USER_NAME);
      String pw = properties0.getProperties().getProperty(PASSWORD);
      return dataSource.getConnection(un, pw);
    }
    return dataSource.getConnection();
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
    return dataSource.getLoginTimeout();
  }

  /**
   * {@inheritDoc}
   *
   * @see javax.sql.CommonDataSource#getLogWriter()
   */
  @Override
  public PrintWriter getLogWriter() throws SQLException {
    return dataSource.getLogWriter();
  }

  /**
   * {@inheritDoc}
   *
   * @see javax.sql.CommonDataSource#getParentLogger()
   */
  @Override
  public Logger getParentLogger() throws SQLFeatureNotSupportedException {
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
    return ((SimplePropertiesModel) properties).getProperties().get(property);
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
    return false;
  }

  /**
   * {@inheritDoc}
   *
   * @see java.sql.Wrapper#isWrapperFor(java.lang.Class)
   */
  @Override
  public boolean isWrapperFor(final Class<?> arg0) throws SQLException {
    if (arg0.isInstance(dataSource)) {
      return true;
    }
    if (dataSource != null) {
      return dataSource.isWrapperFor(arg0);
    }
    return false;
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
    dataSource.setLoginTimeout(seconds);

  }

  /**
   * {@inheritDoc}
   *
   * @see javax.sql.CommonDataSource#setLogWriter(java.io.PrintWriter)
   */
  @Override
  public void setLogWriter(final PrintWriter arg0) throws SQLException {
    dataSource.setLogWriter(arg0);

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

  }

  /**
   * Assigns a new value to a property.
   *
   * @param property
   *          the name of a property.
   * @param value
   *          the new value of the property.
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
   * Validate.
   */
  private void validate() {
  }

}
