package net.witerat.cafenatedsql.api;

import net.witerat.cafenatedsql.api.driver.ConnectionType;
import net.witerat.cafenatedsql.api.driver.DialectDef;
import net.witerat.cafenatedsql.api.driver.DriverCreationException;
import net.witerat.cafenatedsql.api.driver.GrammarDef;
import net.witerat.cafenatedsql.api.driver.NounDef;
import net.witerat.cafenatedsql.api.driver.ParameterType;
import net.witerat.cafenatedsql.api.driver.UrlDef;
import net.witerat.cafenatedsql.api.driver.VerbDef;
import net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel;
import net.witerat.cafenatedsql.spi.driver.DialectSelector;
import net.witerat.cafenatedsql.spi.driver.Driver;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating Driver objects.
 */
public interface DriverFactory extends CafenatedFactory {

	/**
	 * New driver.
	 *
	 * @param model the model
	 * @return the driver
	 * @throws DriverCreationException the driver creation exception
	 */
	Driver newDriver(TemplateEngineModel model) throws DriverCreationException;

	/**
	 * New connection type.
	 *
	 * @param driver the driver
	 * @param method the method
	 * @return the connection type
	 */
	ConnectionType newConnectionType(Driver driver, String method);

	/**
	 * New dialect expression selector.
	 *
	 * @param ct the ct
	 * @param nameExpression the name expression
	 * @return the dialect selector
	 */
	DialectSelector newDialectExpressionSelector(ConnectionType ct, String nameExpression);

	/**
	 * New dialect name selector.
	 *
	 * @param ct the connection type 
	 * @param name the name used to identify the dialect to select.
	 * @return the dialect selector
	 */
	DialectSelector newDialectNameSelector(ConnectionType ct, String name);


	/**
	 * Creates a new Grammar to be part of a dialect.
	 * @param dd the parent dialect
	 * @param name Name of the grammar definition
	 * @return a new grammar definition
	 */
	GrammarDef newGrammar(DialectDef dd, String name);

	/**
	 * New verb.
	 *
	 * @param nn the noun to which the verb can apply
	 * @param name the name
	 * @return the verb def
	 */
	VerbDef newVerb(NounDef nn, String name);

	/**
	 * Create a new noun which maps to a database entity.
	 *
	 * @param gg the grammar to which the noun belongs.
	 * @param name the name of the noun
	 * @return the noun definition
	 */
	NounDef newNoun(GrammarDef gg, String name);

	/**
	 * Create a new dialect definition.
	 *
	 * @param driver the driver to which the definition applies
	 * @param name the name the name of the dialect
	 * @return the dialect definition
	 */
	DialectDef newDialectDefinition(Driver driver, String name);

	/**
	 * New url def.
	 *
	 * @param ct the ct
	 * @return the url def
	 */
	UrlDef newUrlDef(ConnectionType ct);

	/**
	 * New parameter.
	 *
	 * @return the parameter type
	 */
	ParameterType newParameter();

}
