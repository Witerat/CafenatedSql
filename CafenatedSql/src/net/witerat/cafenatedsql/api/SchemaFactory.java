package net.witerat.cafenatedsql.api;

// TODO: Auto-generated Javadoc
/**
 * 
 * Encapsulates access to new and existing schemas.
 *
 */

public interface SchemaFactory extends CafenatedFactory {
	
	/**
	 *  gets a named schema from the data base .
	 *
	 * @param name the name of the schema to get.
	 * @return the schema having the specified name.
	 */
	Schema getSchema(String name);

	/**
	 *  creates a new schema reference .
	 *
	 * @return a new schema
	 */
	Schema createSchema();
}
