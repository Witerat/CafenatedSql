package net.witerat.cafenatedsql.api;

import net.witerat.cafenatedsql.api.driver.GrammarDef;
import net.witerat.cafenatedsql.api.driver.NounDef;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating Noun objects.
 */
public interface NounFactory extends CafenatedFactory {
   
   /**
    * New noun.
    *
    * @param gg the gg
    * @param name the name
    * @return the noun def
    */
   NounDef newNoun(GrammarDef gg, String name);
}
