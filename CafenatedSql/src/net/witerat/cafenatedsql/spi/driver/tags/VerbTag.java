package net.witerat.cafenatedsql.spi.driver.tags;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


/**
 * The Class <code>VerbTag</code> describes how instructions related to 
 * database entities - nouns are rendered and submitted to a backend database.
 */
@XmlType(name="VerbType", namespace="-//org.witerat/cafenated/sql")
public class VerbTag {
	Collection<Object> rawContent;
	Map<String, ParameterTag> parametersByName;
	/** The name. */
	String name;

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	@XmlAttribute(name="name")
	public String getName() {
		return name;
	}

	/**
	 * @return strings and tags
	 */
	public Collection<Object> getRawContent() {
		return rawContent;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param rawContent strings and tags
	 */
	@XmlElementRefs({
		@XmlElementRef(name="param",type=ParameterTag.class),
		@XmlElementRef(name="use", type=UseTag.class)})
	@XmlMixed
	public void setRawContent(Collection<Object> rawContent) {
		Collection<Object> rc=new ArrayList<>();
		for(Object o:rawContent){
			if(o instanceof ParameterTag)
				setParameter((ParameterTag)o);
			
			else if(!rc.isEmpty()|| (!(o instanceof String))||!(((String)o).trim().isEmpty())) 
				rc.add(o);
		}
		this.rawContent = rc;
	}
	
	private void setParameter(ParameterTag param) {
		if(parametersByName==null)parametersByName=new LinkedHashMap<>();
		parametersByName.put(param.name, param);
	}

	/**
	 * @param params the new parameters
	 */

	/**
	 * @return the parameters
	 */
	@XmlTransient
	public Collection<ParameterTag> getParameters() {
		if(parametersByName==null)parametersByName=new LinkedHashMap<>(); 
		return parametersByName.values();
	}
}
