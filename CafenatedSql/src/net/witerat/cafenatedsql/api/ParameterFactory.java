package net.witerat.cafenatedsql.api;

import net.witerat.cafenatedsql.api.driver.ParameterType;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating Parameter objects.
 */
public interface ParameterFactory extends CafenatedFactory {

	/**
	 * New parameter.
	 *
	 * @return the parameter type
	 */
	ParameterType newParameter();

}
