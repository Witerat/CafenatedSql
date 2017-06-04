package net.witerat.cafenatedsql.spi.driver.tags;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * This class encapsulates the XML description of a dialect reference for a
 * collection.
 * 
 * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
 *
 */
@XmlType(name="DialectType")
public class DialectTag {

	/** The id. */
	String id;

	/** The name. */
	String name;

	/** The default name. */
	String defaultName;

	/** The expression. */
	boolean expression;

	private Map<String, GrammarTag> grammarsByName;

	/**
	 * Gets the default name.
	 *
	 * @return the default name
	 */
	public String getDefaultName() {
		return defaultName;
	}

	/**
	 * Gets the grammars.
	 *
	 * @return the grammars
	 */
	@XmlTransient
	public Collection<GrammarTag> getGrammars() {
		if(grammarsByName==null)grammarsByName=new LinkedHashMap<>();
		return grammarsByName.values();
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	@XmlAttribute(name="id")
	public String getId() {
		return id;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getNameExpression() {
		return expression ? name : null;
	}

	/**
	 * Gets the name expression.
	 *
	 * @return the name expression
	 */
	@XmlAttribute(name="name")
	public String getName() {
		return expression ? null : name;
	}

	/**
	 * Checks if is expression.
	 *
	 * @return true, if is expression
	 */
	public boolean isExpression() {
		return expression;
	}

	/**
	 * Sets the default name.
	 *
	 * @param defaultName
	 *            the new default name
	 */
	@XmlAttribute(name = "default")
	public void setDefaultName(String defaultName) {
		this.defaultName = defaultName;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Set a literal name for the dialect.
	 * 
	 * @param name
	 *            a literal name
	 */
	public void setName(String name) {
		this.name = name;
		expression = false;
	}

	/**
	 * The name property.
	 *
	 * @param name
	 *            a template language expression that is evaluable to the name
	 *            of a dialect.
	 */
	@XmlAttribute(name = "nameEx")
	public void setNameExpression(String name) {
		this.name = name;
		expression = true;
	}

	/**
	 * @param grammar a new grammar tag
	 */
	@XmlElement(namespace="-//org.witerat/cafenated/sql",type=GrammarTag.class)
	public void setGrammar(GrammarTag grammar){
		if(grammarsByName==null)grammarsByName=new LinkedHashMap<>();
		grammarsByName.put(grammar.name, grammar);
	}
}
