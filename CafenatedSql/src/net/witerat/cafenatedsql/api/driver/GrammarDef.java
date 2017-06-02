package net.witerat.cafenatedsql.api.driver;

// TODO: Auto-generated Javadoc
/**
 * The Interface GrammarDef.
 */
public interface GrammarDef extends ParameterizedRequest {

	/**
	 * Adds a noun to this grammar.
	 *
	 * @param nn the noun
	 */
	void addNoun(NounDef nn);

	/**
	 * Sets a name for this grammar.
	 *
	 * @param name a new name
	 */
	void setName(String name);

	/**
	 * Gets the name for this grammar.
	 *
	 * @return the name
	 */
	String getName();
	
	/**
	 * Sets a named parameter.
	 *
	 * @param type the type constraint of  the parameter.
	 */
	@Override
	void add( ParameterType type );

}
