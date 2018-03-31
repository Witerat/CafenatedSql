package net.witerat.cafenatedsql.spi;

import net.witerat.cafenatedsql.api.GrammarFactory;
import net.witerat.cafenatedsql.api.driver.DialectDef;
import net.witerat.cafenatedsql.api.driver.GrammarDef;

/**
 * A factory for creating SimpleGrammar objects.
 */
public class SimpleGrammarFactory implements GrammarFactory {

  /*
   * (non-Javadoc)
   *
   * @see net.witerat.cafenatedsql.api.GrammarFactory#newGrammar()
   */
  @Override
  public GrammarDef newGrammar(final DialectDef dialect, final String name) {
    return new SimpleGrammarDef();
  }

}
