package net.witerat.cafenatedsql.spi.driver.tags;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * The Class Meta.
 */
@XmlType(name="MetaTag")
public class MetaTag {
	/** The class name. */
	String className;

	String description;

	String name;

	String protocol;

	/**
	 * Gets the class name.
	 *
	 * @return the class name
	 */
	@XmlAttribute
	public String getClassName() {
		return className;
	}

	/**
	 * @return human readable description.
	 */
	@XmlAttribute
	public String getDescription() {
		return description;
	}

	/**
	 * @return a human name for the driver
	 */
	@XmlAttribute
	public String getName() {
		return name;
	}

	/**
	 * @return default protocol
	 */
	@XmlAttribute
	public String getProtocol() {
		return protocol;
	}

	/**
	 * Sets the class name.
	 *
	 * @param className
	 *            the new class name
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * Sets the human readable description of the driver.
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Sets the unique name of the driver.
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the driver's base protocol.
	 * @param protocol
	 */
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

}
