package net.witerat.cafenatedsql.api;

/**
 * Encapsulates access to new and existing schemas.
 *
 */

public interface SchemaFactory extends CafenatedFactory {

  /**
   * gets a named schema from the data base .
   *
   * @param name
   *          the name of the schema to get.
   * @return the schema having the specified name.
   */
  Schema getSchema(String name);

  /**
   * creates a new schema reference .
   *
   * @return a new schema
   */
  Schema createSchema();

  /**
   * Instruct the underlying db to to drop a schema.
   * @param name name of the schema to drop.
   * @return A named empty schema.
   */
  Schema dropSchema(String name);
}
