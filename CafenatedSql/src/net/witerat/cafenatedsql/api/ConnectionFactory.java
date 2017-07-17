package net.witerat.cafenatedsql.api;

import java.sql.Connection;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating Connection objects.
 */
public interface ConnectionFactory extends CafenatedFactory {
	
	/** The default property name for identifying a database.  */
	final static String DATABASE_NAME="DATABASE_NAME";  
	
	/**  The property for holding the user name. */
	final static String USER_NAME="USER";  
	
	/** The property name for the password. */
	final static String PASSWORD="PASSWORD";  
	
	/**
	 * Sets a specified  property.
	 *
	 * @param property the property identity
	 * @param Value the value the new value of the property.
	 */
	void setProperty(Object property, Object Value);
	
	/**
	 * Gets the identified property.
	 *
	 * @param property the property
	 * @return the property
	 */
	Object getProperty(Object property);
	
	/**
	 * Establish a connection.
	 *
	 * @return the connection
	 */
	Connection connect();

	Class<?> getPropertyType(Object property);

	boolean isPropertyRequired(Object property);

}
