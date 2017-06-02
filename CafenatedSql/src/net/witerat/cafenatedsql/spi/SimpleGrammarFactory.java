package net.witerat.cafenatedsql.spi;

import net.witerat.cafenatedsql.api.GrammarFactory;
import net.witerat.cafenatedsql.api.driver.DialectDef;
import net.witerat.cafenatedsql.api.driver.GrammarDef;
import net.witerat.cafenatedsql.spi.SimpleGrammerDef;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating SimpleGrammar objects.
 */
public class SimpleGrammarFactory implements GrammarFactory {

	/* (non-Javadoc)
	 * @see net.witerat.cafenatedsql.api.GrammarFactory#newGrammar()
	 */
	@Override
	public GrammarDef newGrammar(DialectDef dialect, String name) {
		return new SimpleGrammerDef();
	}


}
