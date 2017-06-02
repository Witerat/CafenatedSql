package net.witerat.cafenatedsql.api.driver;

// TODO: Auto-generated Javadoc
/**
 * The Interface ParameterType.
 */
public interface ParameterType {

	/**
	 * Gets the default.
	 *
	 * @return the default
	 */
	Object getDefault();

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	String getName();

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	Class<?> getType();

	/**
	 * Checks if is optional.
	 *
	 * @return true, if is optional
	 */
	boolean isOptional();

	/**
	 * Sets the default.
	 *
	 * @param difault the new default
	 */
	void setDefault(Object difault);

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	void setName(String name);

	/**
	 * Sets the optional.
	 *
	 * @param optional the new optional
	 */
	void setOptional(boolean optional);

	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	void setType(Class<?> type);

}
