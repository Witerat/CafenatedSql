package net.witerat.cafenatedsql.spi.driver.tags;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import net.witerat.cafenatedsql.spi.driver.tags.NounTag;

// TODO: Auto-generated Javadoc
/**
 * The Class GrammarTag.
 */
@XmlType(name="GrammarType",namespace="-//org.witerat/cafenated/sql")
public class GrammarTag {
	
	/** The name. */
	String name;
	
	/** The nouns. */
	Collection<NounTag> nouns;
	
	/** The params. */
	Map<String, ParameterTag> params;

	/**
	 * Sets the noun.
	 *
	 * @param noun the new noun
	 */
	@XmlElement(name="noun", type=NounTag.class, namespace="-//org.witerat/cafenated/sql")
	void setNoun(NounTag noun) {
		if(nouns==null)nouns=new ArrayList<>();
		nouns.add(noun);
	}

	/**
	 * Gets the nouns.
	 *
	 * @return the nouns
	 */
	@XmlTransient
	public Collection<NounTag> getNouns() {
		if(nouns==null)nouns=new ArrayList<>();
		return nouns;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	@XmlAttribute(name="name")
	public String getId() {
		return name;
	}

	/**
	 * Sets the id.
	 *
	 * @param name the new id
	 */
	public void setId(String name) {
		this.name = name;
	}

	/**
	 * Gets the params.
	 *
	 * @return the params
	 */
	@XmlTransient
	public Collection<ParameterTag> getParams() {
		if(params==null)params=new LinkedHashMap<>();
		return params.values();
	}
	
	/**
	 * Sets the parameter.
	 *
	 * @param p the new parameter
	 */
	@XmlElement(name="param", type=ParameterTag.class,namespace="-//org.witerat/cafenated/sql")
	void setParameter(ParameterTag p){
		if(params==null)params=new LinkedHashMap<>();
		params.put(p.getName(), p);
	}
}
