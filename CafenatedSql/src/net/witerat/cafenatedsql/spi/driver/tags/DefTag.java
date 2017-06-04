package net.witerat.cafenatedsql.spi.driver.tags;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlType;

// TODO: Auto-generated Javadoc
/**
 * The Class DefTag.
 */
@XmlType(name="DefType", namespace="-//org.witerat/cafenated/sql")
public class DefTag extends UrlTag { 
    
    /** The name. */
    String name;
    
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
    @XmlAttribute
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}
	@XmlElementRefs({
		@XmlElementRef(name="use", namespace="-//org.witerat/cafenated/sql", type=UseTag.class),
		@XmlElementRef(name="param", namespace="-//org.witerat/cafenated/sql", type=ParameterTag.class)})
	@XmlMixed
	public List<Object> getRawContent() {
		return super.getRawContent();
	}
	public void setRawContent(List<Object> raw) {
		super.setRawContent(raw);
	}
}
