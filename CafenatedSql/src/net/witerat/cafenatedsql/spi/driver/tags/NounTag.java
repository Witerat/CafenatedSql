package net.witerat.cafenatedsql.spi.driver.tags;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * The Class NounTag.
 */
@XmlType(name="NounType",namespace="-//org.witerat/cafenated/sql")
public class NounTag extends SqlTemplate {

	private String name;
	private Map<String, VerbTag> verbsByName;
	/** The verbs. */

	public void setName(String name) {
		this.name = name;
	}

	@XmlAttribute(name="name")
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}
	
	
	@XmlTransient
	public Collection<VerbTag> getVerbs() {
		if(verbsByName==null)verbsByName=new LinkedHashMap<>();
		return verbsByName.values();
	}

	public void setVerbs(Collection<VerbTag> verbs) {
		Map<String, VerbTag> vbn=new LinkedHashMap<>();
		for(VerbTag v:verbs) vbn.put(v.name,v);
		verbsByName = vbn;
		
	}

	@XmlElement(namespace="-//org.witerat/cafenated/sql", type=VerbTag.class)
	void setVerb(VerbTag verb){
		if(verbsByName==null)verbsByName=new LinkedHashMap<>();
		verbsByName.put(verb.name,verb);
	}
	@XmlElement(name="param", type=ParameterTag.class, namespace="-//org.witerat/cafenated/sql")
	public void setParameter(ParameterTag param) {
		super.setParameter(param);
	}
}
