package net.witerat.cafenatedsql.spi;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import net.witerat.cafenatedsql.api.driver.GrammarDef;
import net.witerat.cafenatedsql.api.driver.NounDef;
import net.witerat.cafenatedsql.api.driver.ParameterType;

// TODO: Auto-generated Javadoc
/**
 * The Class SimpleGrammerDef.
 */
public class SimpleGrammerDef implements GrammarDef {

	/** The name of this grammar. */
	private String name;
	
	/** this grammer's nouns. */
	private Map<String, NounDef> nouns=new LinkedHashMap<String, NounDef>();
	
	/** This grammar's parameters. */
	private Map<String, ParameterType> params=new LinkedHashMap<String, ParameterType>();

	/**
	 * Adds a noun to this grammar definition.	
	 * @see net.witerat.cafenatedsql.api.driver.GrammarDef#addNoun(net.witerat.cafenatedsql.api.driver.NounDef)
	 */
	@Override
	public void addNoun(NounDef nn) {
		nouns.put(nn.getName(), nn);
	}

	/* (non-Javadoc)
	 * @see net.witerat.cafenatedsql.api.driver.GrammarDef#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.name=name;

	}

	/* (non-Javadoc)
	 * @see net.witerat.cafenatedsql.api.driver.GrammarDef#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * Adds a parameter to this grammar.
	 * @see net.witerat.cafenatedsql.api.driver.GrammarDef#add(net.witerat.cafenatedsql.api.driver.ParameterType)
	 */
	@Override
	public void add(ParameterType type) {
		params.put(type.getName(), type);
	}

	/**
	 * Gets the params.
	 *
	 * @return the params
	 */
	public Collection<ParameterType> getParams(){
		return params.values();
	}
}
