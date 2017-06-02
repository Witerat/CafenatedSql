package net.witerat.cafenatedsql.api;

import net.witerat.cafenatedsql.api.driver.UrlDef;

// TODO: Auto-generated Javadoc
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
