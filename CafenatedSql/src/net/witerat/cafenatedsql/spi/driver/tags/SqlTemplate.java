package net.witerat.cafenatedsql.spi.driver.tags;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * The class SqlTemplate.
 */
@XmlType(name="TemplateTag")
public class SqlTemplate {
	
	/** The parameters. */
	Map<String, ParameterTag> parametersByName;

	/**
	 * Gets the parameters.
	 *
	 * @return the parameters
	 */
	@XmlTransient
	public Collection<ParameterTag> getParameters() {
		if(parametersByName==null)parametersByName=new LinkedHashMap<>();
		
		return parametersByName.values();
	}

	/**
	 * Sets the parameters.
	 *
	 * @param parameters the new parameters
	 */
	public void setParameters(Collection<ParameterTag> parameters) {
		if(parameters==null) parametersByName=null;
		else {
			Map<String, ParameterTag> pBN=new LinkedHashMap<>();
			for(ParameterTag p:parameters) pBN.put(p.getName(), p);
			parametersByName = pBN;
		}
	}

	/**
	 * @param param a new parameter
	 */
	public void setParameter(ParameterTag param) {
		if(parametersByName==null)parametersByName=new LinkedHashMap<>();
		parametersByName.put(param.getName(), param);
	}
}
