package net.witerat.cafenatedsql.spi.driver.tags;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
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
    @XmlMixed
	@Override
	public LinkedList<Object> getRawContent() {
		// TODO Auto-generated method stub
		return super.getRawContent();
	}
	@Override
	public void setRawContent(List<Object> raw) {
		// TODO Auto-generated method stub
		super.setRawContent(raw);
	}
}
