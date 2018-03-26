package net.witerat.cafenatedsql.api;

import net.witerat.cafenatedsql.api.driver.NounDef;
import net.witerat.cafenatedsql.api.driver.VerbDef;

/**
 * A factory for creating Verb objects.
 */
public interface VerbFactory extends CafenatedFactory {

  /**
   * New verb.
   *
   * @param nn
   *          the nn noun that will own produced verbs
   * @param name
   *          the name the verb id
   * @return the verb def
   */
  VerbDef newVerb(NounDef nn, String name);

}
