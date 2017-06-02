package net.witerat.cafenatedsql.spi.driver.tags;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

// TODO: Auto-generated Javadoc
/**
 * The Class ParameterTag.
 */
@XmlRootElement(name="param",namespace="-//org.witerat/cafenated/sql")
@XmlType(name="ParamType", namespace="-//org.witerat/cafenated/sql")
public class ParameterTag {
	
	/** The type. */
	String type;
	
	/** The name. */
	String name;
	
	/** The optional. */
	boolean optional;
	
	/** The usual(default) value for this parameter. */
	private String usual;

	/**
	 * Checks if is optional.
	 *
	 * @return true, if is optional
	 */
	public boolean isOptional() {
		return optional;
	}

	
	/**
	 * Sets the optional.
	 *
	 * @param optional the new optional
	 */
	@XmlAttribute
	public void setOptional(boolean optional) {
		this.optional = optional;
	}

	/**
	 * Gets the class name for the type of a model parameter for template
	 * processing. if left unset, any object may be provided to the model,
	 * otherwise arguments for this parameter must pass the instanceof test.
	 * 
	 * @return the class-name of the parameter.
	 */

	public String getType() {
		return type;
	}

	/**
	 * Defines the type for a model parameter for template processing. If left
	 * unset, any object may be provides to the model, otherwise arguments for
	 * this parameter must pass the instanceof test.
	 * 
	 * @param type the name or identifier a type.
	 */
	@XmlAttribute
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * gets the name of the parameter;.
	 *
	 * @return the parameter name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the parameter.
	 * 
	 * @param name
	 *            the new name of the parameter.
	 */
	@XmlAttribute
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the default.
	 *
	 * @return the default
	 */
	@XmlAttribute(name="usual")
	public String getUsual() {
		return usual;
	}
	
	/**
	 * Sets the default.
	 *
	 * @param usual the new default
	 */
	public void setUsual(String usual) {
		this.usual = usual;
	}
}
