package net.witerat.cafenatedsql.spi.driver;

import java.util.LinkedHashMap;

import net.witerat.cafenatedsql.api.driver.DialectDef;
import net.witerat.cafenatedsql.api.driver.GrammarDef;
import net.witerat.cafenatedsql.api.driver.ParameterType;
import net.witerat.cafenatedsql.api.driver.ParameterizedRequest;

// TODO: Auto-generated Javadoc
/**
 * The Class Dialect.
 */
public class Dialect implements ParameterizedRequest, DialectDef {

	/** The id. */
	private String id;
	
	/** The grammars. */
	LinkedHashMap<String, GrammarDef> grammars;
	
	/** The params. */
	LinkedHashMap<String, ParameterType> params;
	
	/* (non-Javadoc)
	 * @see net.witerat.cafenatedsql.spi.driver.DialectDef#setName(java.lang.String)
	 */
	@Override
	public void setName(String id) {
		this.id=id;
		
	}
	/* (non-Javadoc)
	 * @see net.witerat.cafenatedsql.spi.driver.DialectDef#getId()
	 */
	@Override
	public String getId() {
		return id;
	}
	/**
	 * Adds a grammar to this dialect
	 * @see DialectDef#addGrammar(GrammarDef)
	 */
	@Override
	public void addGrammar(GrammarDef g){
		grammars.put(g.getName(), g);
	}
	/**
	 * Adds a parameter to this <code>Dialect</code>.
	 * @see DialectDef#add(ParameterType)
	 */
	@Override
	public void add(ParameterType param){
		params.put(param.getName(), param);
	}
}
