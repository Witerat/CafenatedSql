package net.witerat.cafenatedsql.api;

import net.witerat.cafenatedsql.api.driver.GrammarDef;
import net.witerat.cafenatedsql.api.driver.DialectDef;

// TODO: Auto-generated Javadoc
/**
 * A factory contract for creating Grammar objects.
 */
public interface GrammarFactory extends CafenatedFactory {
	
	/**
	 * New grammar.
	 *
	 * @param dd the dd
	 * @param name the name
	 * @return A new grammar definition
	 */
	GrammarDef newGrammar(DialectDef dd, String name);
}
