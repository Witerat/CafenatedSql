package net.witerat.cafenatedsql.spi.mock;

import java.sql.Connection;
import java.util.Map;

import net.witerat.cafenatedsql.api.ConnectionFactory;

public class MockConnectionFactory implements ConnectionFactory {
	String method; 
	Map<String, Object> properties;
	public MockConnectionFactory(Map<String, Object> properties) {
		this.method=(String) properties.get("connection_method");
		this.properties=properties;
	}

	@Override
	public void setProperty(Object property, Object value) {
		properties.put((String)property, value);

	}

	@Override
	public Object getProperty(Object property) {
		// TODO Auto-generated method stub
		return properties.get(property);
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

	@Override
	public Connection connect() {
		// TODO Auto-generated method stub
		return new MockConnection(properties, method);
	}

}
