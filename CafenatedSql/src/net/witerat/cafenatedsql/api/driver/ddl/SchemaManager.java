package net.witerat.cafenatedsql.api.driver.ddl;

import java.sql.SQLException;

import net.witerat.cafenatedsql.api.EntityManager;
import net.witerat.cafenatedsql.api.Schema;

/**
 * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
 *
 */
public interface SchemaManager extends EntityManager {

  /**
   * The NOUN property.
   */
  String NOUN = "schema";

  /**
   * Add a new schema to a database.
   * @param schema the schema to add.
   */
  void add(Schema schema);

  /**
   * Creates a new schema object.
   * @return a new anonymous Schema.
   * @see #add(Schema)
   */
  Schema getSchema();

  /**
   * Get a schema associated with the specified name.
   * @param name
   *          The name of an existing schema.
   * @return A representation of the specified schema entity.
   */
  Schema getSchema(String name);

  /**
   * Drop the specified schema entity.
   *
   * @param schema the schema to be dropped.
   * @throws SQLException
   *           if Schema is not bound to this manager..
   */
  void drop(Schema schema)throws SQLException;

}
