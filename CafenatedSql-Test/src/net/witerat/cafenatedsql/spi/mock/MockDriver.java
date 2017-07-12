package net.witerat.cafenatedsql.spi.mock;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.logging.Logger;

public class MockDriver implements Driver {
    static final Logger logger=Logger.getLogger(MockDriver.class.getClass().getName()); 
	private DriverPropertyInfo[] propertyInfo;

	public void setPropertyInfo(DriverPropertyInfo[] propertyInfo) {
		this.propertyInfo = propertyInfo;
	}

	@Override
	public Connection connect(String url, Properties info) throws SQLException {
		return new MockConnection(url, new Properties(info));
	}

	@Override
	public boolean acceptsURL(String url) throws SQLException {
		return url.startsWith("jdbc:mock:");
	}

	@Override
	public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
		// TODO Auto-generated method stub
		return propertyInfo;
	}

	@Override
	public int getMajorVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMinorVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean jdbcCompliant() {
		return false;
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return logger;
	}

}
