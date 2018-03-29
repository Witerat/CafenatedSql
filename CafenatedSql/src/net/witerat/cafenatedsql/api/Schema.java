package net.witerat.cafenatedsql.api;

import java.util.List;

/**
 * The Interface Schema.
 */
public interface Schema {

  /**
   * Gets the name.
   *
   * @return the name
   */
  String getName();

  /**
   * Sets the name.
   *
   * @param name
   *          the new name
   */
  void setName(String name);

  /**
   * @param refactors a list modifications
   */
  void setJournal(List<Refactor> refactors);

  /** Save changes. */
  void update();
}
