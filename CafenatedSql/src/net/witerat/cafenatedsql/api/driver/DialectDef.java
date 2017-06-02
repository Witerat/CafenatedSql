package net.witerat.cafenatedsql.api.driver;


// TODO: Auto-generated Javadoc
/**
 * The Interface DialectDef.
 */
public interface DialectDef extends ParameterizedRequest {

	/**
	 * Sets the name of this dialect.
	 *
	 * @param id the new name
	 */
	void setName(String id);

	/**
	 * Gets the id of this dialect.
	 *
	 * @return the id
	 */
	String getId();

	/**
	 * Adds a grammar to this dialect.
	 *
	 * @param g the g
	 */
	void addGrammar(GrammarDef g);

	/**
	 * Adds a parameter to this dialect definition
	 * @see net.witerat.cafenatedsql.api.driver.ParameterizedRequest#add(net.witerat.cafenatedsql.api.driver.ParameterType)
	 */
	void add(ParameterType param);

}