package net.witerat.cafenatedsql.api;

import net.witerat.cafenatedsql.api.driver.NounDef;
import net.witerat.cafenatedsql.api.driver.VerbDef;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating Verb objects.
 */
public interface VerbFactory extends CafenatedFactory {
	   
   	/**
   	 * New verb.
   	 *
   	 * @param nn the nn
   	 * @param name the name
   	 * @return the verb def
   	 */
   	VerbDef newVerb(NounDef nn, String name);

}
