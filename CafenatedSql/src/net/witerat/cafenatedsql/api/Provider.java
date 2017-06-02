package net.witerat.cafenatedsql.api;

import net.witerat.cafenatedsql.api.DialectFactory;
import net.witerat.cafenatedsql.api.NounFactory;
import net.witerat.cafenatedsql.api.VerbFactory;

/**
 * The Interface Provider.
 */
public interface Provider {
	
	/**
	 * Gets the connection factory.
	 *
	 * @return the connection factory
	 */
	public abstract ConnectionFactory	getConnectionFactory();
	
	/**
	 * Gets the schema factory.
	 *
	 * @return the schema factory
	 */
	public abstract SchemaFactory getSchemaFactory();
	
	/**
	 * Gets the table factory.
	 *
	 * @return the table factory
	 */
	public abstract TableFactory getTableFactory(); 
	
	/**
	 * Gets the view factory.
	 *
	 * @return the view factory
	 */
	public abstract ViewFactory getViewFactory(); 
	
	/**
	 * Gets the driver factory.
	 *
	 * @return the driver factory
	 */
	public abstract DriverFactory getDriverFactory();
	
	/**
	 * Gets the driver class.
	 *
	 * @return the driver class
	 */
	public abstract String getDriverClass();
	
	/**
	 * Gets the grammar factory.
	 *
	 * @return the grammar factory
	 */
	public abstract GrammarFactory getGrammarFactory();
	
	/**
	 * Gets the url factory.
	 *
	 * @return the url factory
	 */
	public abstract URLFactory getUrlFactory();
	
	/**
	 * Gets the parameter factory.
	 *
	 * @return the parameter factory
	 */
	public abstract ParameterFactory getParameterFactory();
	
	/**
	 * Gets the verb factory.
	 *
	 * @return the verb factory
	 */
	public abstract VerbFactory getVerbFactory();
	
	/**
	 * Gets the noun factory.
	 *
	 * @return the noun factory
	 */
	public abstract NounFactory getNounFactory();
	
	/**
	 * Gets the dialect factory.
	 *
	 * @return the dialect factory
	 */
	public abstract DialectFactory getDialectFactory();
}
