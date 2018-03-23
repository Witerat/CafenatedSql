package net.witerat.cafenatedsql.api;

import java.sql.Connection;

/**
 * A factory for creating Connection objects.
 */
public interface ConnectionFactory extends CafenatedFactory {

  /** The default property name for identifying a database. */
  String DATABASE_NAME = "DATABASE_NAME";

  /** The property for holding the user name. */
  String USER_NAME = "USER";

  /** The property name for the password. */
  String PASSWORD = "PASSWORD";

  /**
   * Sets a specified property.
   *
   * @param property
   *          the property identity
   * @param value
   *          the value the new value of the property.
   */
  void setProperty(Object property, Object value);

  /**
   * Gets the identified property.
   *
   * @param property
   *          the property
   * @return the property
   */
  Object getProperty(Object property);

  /**
   * Establish a connection.
   *
   * @return the connection
   */
  Connection connect();

  /**
   * Get class for values of a specified property.
   * @param property an identifier for a property.
   * @return A class which may contain all possible values of the specified
   *    property.
   */
  Class<?> getPropertyType(Object property);

  /**
   * Reports whether a property is required.
   * @param property an identifier for a property.
   * @return <code>true</code> if the value of the property must be set for
   *    the model to pass validation.
   */
  boolean isPropertyRequired(Object property);

}
