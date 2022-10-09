/*
 * @author nos John Hutcheson &lt;witerat.test@gmail.com&gt;
 * @created 03-Sep-2022 12:36:01
 */
package net.witerat.cafenatedsql.api.driver;

/**
 * The ParameterType interface.
 *
 * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
 */
public interface ParameterType {

  /**
   * Gets the current default value.
   *
   * @return The default value.
   */
  Object getDefault();

  /**
   * Gets this formal parameter's name.
   *
   * @return the parameter name.
   */
  String getName();

  /**
   * Gets the type.
   *
   * @return The formal type of the value.
   */
  Class<?> getType();

  /**
   * Checks if is optional.
   *
   * @return true, if parameter is optional.
   */
  boolean isOptional();

  /**
   * Sets the default value.
   *
   * @param difault
   *          A new default value
   */
  void setDefault(Object difault);

  /**
   * Sets this formal parameter's name.
   *
   * @param name
   *          A new parameter name
   */
  void setName(String name);

  /**
   * Sets the optional.
   *
   * @param optional
   *          A new optional state
   */
  void setOptional(boolean optional);

  /**
   * Sets the type.
   *
   * @param type
   *          A new formal type
   */
  void setType(Class<?> type);

}
