package net.witerat.cafenatedsql.api.driver;

/**
 * The Interface NounDef typically encapsulates a database entity.
 */
public interface NounDef {

  /**
   * Introduces a verb to this noun.
   *
   * @param vv
   *          the introduced verb
   */
  void addVerb(VerbDef vv);

  /**
   * Gets the name of this noun.
   *
   * @return the name
   */
  String getName();

  /**
   * Sets the name of this noun.
   *
   * @param name
   *          the new name
   */
  void setName(String name);

}
