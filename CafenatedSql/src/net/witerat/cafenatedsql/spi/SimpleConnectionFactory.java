package net.witerat.cafenatedsql.spi;

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

import com.jolbox.bonecp.BoneCPConfig;
import com.jolbox.bonecp.BoneCPDataSource;

import net.witerat.cafenatedsql.api.ConnectionFactory;
import net.witerat.cafenatedsql.api.driver.ConnectionType;
import net.witerat.cafenatedsql.api.driver.template.SimplePropertiesModel;
import net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel;
import net.witerat.cafenatedsql.spi.driver.Driver;

// TODO: Auto-generated Javadoc
/**
 * 
 * This connection factory implementation uses BoneJS to establish connections
 * to database repositories.
 * 
 * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
 *
 */
public class SimpleConnectionFactory implements ConnectionFactory, DataSource {
	
	/** The properties. */
	TemplateEngineModel properties;
	
	/** The data source. */
	DataSource dataSource = this;
	
	/** The connection type. */
	ConnectionType connectionType;
	
	/** The driver. */
	Driver driver;

	/**
	 * A convenience method for establishing a factory using an XML properties
	 * stream.
	 *
	 * @param inputStream the input stream
	 * @return the simple connection factory
	 * @throws InvalidPropertiesFormatException the invalid properties format exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	static SimpleConnectionFactory loadFromXML(InputStream inputStream)
			throws InvalidPropertiesFormatException, IOException {
		return new SimpleConnectionFactory(inputStream, true);
	}

	/**
	 * Load from resource.
	 *
	 * @param packageName the package name
	 * @param resourceName the resource name
	 * @return the simple connection factory
	 * @throws InvalidPropertiesFormatException the invalid properties format exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	static SimpleConnectionFactory loadFromResource(String packageName, String resourceName)
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
	 * @param driver the driver
	 * @param cl the cl
	 * @param resourceName the resource name
	 * @return the simple connection factory
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	static SimpleConnectionFactory loadFromResourceStream(Driver driver, ClassLoader cl, String resourceName)
			throws IOException {
		InputStream in = cl.getResourceAsStream(resourceName);
		SimplePropertiesModel model = new SimplePropertiesModel();
		model.getProperties().load(in);
		return new SimpleConnectionFactory(model.getProperties());

	}

	/**
	 * Load from XML resource stream.
	 *
	 * @param driver the driver
	 * @param cl the cl
	 * @param resourceName the resource name
	 * @return the simple connection factory
	 * @throws InvalidPropertiesFormatException the invalid properties format exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	static SimpleConnectionFactory loadFromXMLResourceStream(Driver driver, ClassLoader cl, String resourceName)
			throws InvalidPropertiesFormatException, IOException {
		InputStream in = cl.getResourceAsStream(resourceName);
		SimplePropertiesModel model = new SimplePropertiesModel();
		model.getProperties().loadFromXML(in);
		return new SimpleConnectionFactory(model.getProperties());
	}

	/**
	 * Load from XML resource stream.
	 *
	 * @param driver the driver
	 * @param c the c
	 * @return the simple connection factory
	 * @throws InvalidPropertiesFormatException the invalid properties format exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	static SimpleConnectionFactory loadFromXMLResourceStream(Driver driver, Class<?> c)
			throws InvalidPropertiesFormatException, IOException {
		InputStream in = c.getClassLoader().getResourceAsStream(c.getName() + ".properties");
		SimplePropertiesModel model = new SimplePropertiesModel();
		if (in == null) {
			model.getProperties().loadFromXML(in);
		} else {
			in = c.getClassLoader().getResourceAsStream(c.getName() + ".properties.xml");
			model.getProperties().loadFromXML(in);
		}

		return new SimpleConnectionFactory(model.getProperties());
	}

	/**
	 * Instantiate a(n) SimpleConnectionFactory object.
	 */
	public SimpleConnectionFactory() {
		properties = new SimplePropertiesModel();
	}

	/**
	 * Instantiate a(n) SimpleConnectionFactory object using pre-loaded
	 * properties..
	 * 
	 * @param model connection properties for new connections.
	 */
	public SimpleConnectionFactory(Properties model) {
		this.properties = new SimplePropertiesModel(model);
	}

	/**
	 * 
	 * Instantiate a(n) SimpleConnectionFactory object extracting connection
	 * properties from an input stream.
	 * 
	 * @param inputStream
	 *            a source for connection properties.
	 * @throws IOException
	 *             fail to read the properties file.
	 */
	public SimpleConnectionFactory(InputStream inputStream) throws IOException {
		properties = new SimplePropertiesModel();
		((SimplePropertiesModel) properties).getProperties().load(inputStream);
	}

	/**
	 * 
	 * Instantiate a(n) SimpleConnectionFactory object extracting properties
	 * from n input that may contain XML.
	 * 
	 * @param in
	 *            a source for the properties.
	 * @param isXML
	 *            true if the source file is expected to be in XML format.
	 * @throws InvalidPropertiesFormatException
	 *             failed to read the properties file
	 * @throws IOException
	 *             failed to access the input strean while read the properties
	 *             file.
	 */
	public SimpleConnectionFactory(InputStream in, boolean isXML) throws InvalidPropertiesFormatException, IOException {
		SimplePropertiesModel model = new SimplePropertiesModel();

		if (isXML) {
			model.getProperties().loadFromXML(in);
		} else {
			model.getProperties().load(in);
		}
		this.properties = model;
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
	 * Set the connection type, using a specified connection template.
	 * 
	 * @param connectionType
	 *            the new connection type
	 */
	public void setConnectionType(ConnectionType connectionType) {
		this.connectionType = connectionType;
	}

	/**
	 * Assigns a new value to a property.
	 *
	 * @param property            the name of a property.
	 * @param value            the new value of the property.
	 */
	public void setProperty(String property, Object value) {
		properties.set(property, value);
	}

	/**
	 * Gets the name property.
	 * 
	 * @param property
	 *            name of the property
	 * @return the value of the property.
	 */
	public Object getProperty(String property) {
		return properties.get(property);
	}

	/* (non-Javadoc)
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

	/* (non-Javadoc)
	 * @see javax.sql.DataSource#getConnection()
	 */
	public Connection getConnection() throws SQLException {
		return connect0();
	}

	/**
	 * establish a connection from associated properties.
	 * 
	 * @return the new connection.
	 * @throws SQLException
	 *             failure to coonect.
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
		if (properties0.getProperties().containsKey(USER_NAME) || properties0.getProperties().containsKey(PASSWORD)) {
			String un = properties0.getProperties().getProperty(USER_NAME);
			String pw = properties0.getProperties().getProperty(PASSWORD);
			return dataSource.getConnection(un, pw);
		}
		return dataSource.getConnection();
	}

	/**
	 * Configure data source.
	 *
	 * @throws Exception the exception
	 */
	private void configureDataSource() throws Exception {
		BoneCPConfig boneCfg = new BoneCPConfig(((SimplePropertiesModel) properties).getProperties());
		BoneCPDataSource boneDS = new BoneCPDataSource(boneCfg);
		dataSource = boneDS;
	}

	/**
	 * Validate.
	 */
	private void validate() {
	}

	/* (non-Javadoc)
	 * @see javax.sql.DataSource#getConnection(java.lang.String, java.lang.String)
	 */
	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		if (username != null)
			setProperty(USER_NAME, username);
		if (password != null)
			setProperty(PASSWORD, password);
		return connect0();
	}

	/* (non-Javadoc)
	 * @see javax.sql.CommonDataSource#getLogWriter()
	 */
	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return dataSource.getLogWriter();
	}

	/* (non-Javadoc)
	 * @see javax.sql.CommonDataSource#getLoginTimeout()
	 */
	@Override
	public int getLoginTimeout() throws SQLException {
		return dataSource.getLoginTimeout();
	}

	/* (non-Javadoc)
	 * @see javax.sql.CommonDataSource#getParentLogger()
	 */
	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return dataSource.getParentLogger();
	}

	/* (non-Javadoc)
	 * @see javax.sql.CommonDataSource#setLogWriter(java.io.PrintWriter)
	 */
	@Override
	public void setLogWriter(PrintWriter arg0) throws SQLException {
		dataSource.setLogWriter(arg0);

	}

	/* (non-Javadoc)
	 * @see javax.sql.CommonDataSource#setLoginTimeout(int)
	 */
	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		dataSource.setLoginTimeout(seconds);

	}

	/* (non-Javadoc)
	 * @see java.sql.Wrapper#isWrapperFor(java.lang.Class)
	 */
	@Override
	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		if (arg0.isInstance(dataSource))
			return true;
		return dataSource == null ? false : dataSource.isWrapperFor(arg0);
	}

	/* (non-Javadoc)
	 * @see java.sql.Wrapper#unwrap(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T unwrap(Class<T> arg0) throws SQLException {
		if (arg0.isInstance(dataSource))
			return (T) dataSource;
		return dataSource == null ? null : dataSource.unwrap(arg0);
	}

	/* (non-Javadoc)
	 * @see net.witerat.cafenatedsql.api.ConnectionFactory#getProperty(java.lang.Object)
	 */
	@Override
	public Object getProperty(Object property) {
		return ((SimplePropertiesModel) properties).getProperties().get(property);
	}

	/* (non-Javadoc)
	 * @see net.witerat.cafenatedsql.api.ConnectionFactory#setProperty(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void setProperty(Object property, Object value) {
		setProperty((String) property, value);

	}

	@Override
	public Class<?> getPropertyType(Object property) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isPropertyRequired(Object property) {
		// TODO Auto-generated method stub
		return false;
	}

}
