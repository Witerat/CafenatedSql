package net.witerat.cafenatedsql.api;

import net.witerat.cafenatedsql.api.driver.DialectDef;
import net.witerat.cafenatedsql.api.driver.GrammarDef;

/**
 * A factory contract for creating Grammar objects from dialect definitions.
 */
public interface GrammarFactory extends CafenatedFactory {

  /**
   * New grammar.
   *
   * @param dd
   *          the dialect definition.
   * @param name
   *          the name for the new grammar, null for anonymous grammars.
   * @return A new grammar definition
   */
  GrammarDef newGrammar(DialectDef dd, String name);
}
