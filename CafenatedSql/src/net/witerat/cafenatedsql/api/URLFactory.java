package net.witerat.cafenatedsql.api;

import net.witerat.cafenatedsql.api.driver.UrlDef;

/**
 * A factory for creating URL objects.
 */
public interface URLFactory extends CafenatedFactory {

  /**
   * New url.
   *
   * @return the url def
   */
  UrlDef newUrl();

}
