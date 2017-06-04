package net.witerat.cafenatedsql.spi.driver.tags;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * The Class DriverTag.
 */
@XmlRootElement(name="driver",namespace="-//org.witerat/cafenated/sql" )
public class DriverTag extends AbstractMacroLocator {
	
	/** The defs. */
	Map<String, DefTag> defs;
	
	
	/** The dialects by id. */
	Map<String, DialectTag> dialectsById;
	
	/** The connections by method. */
	private Map<String, ConnectionTag> connectionsByMethod;
	
	/** The ddl. */
	DDL ddl;
	
	/** The dml. */
	DmlTag dml;

	private MetaTag meta;

	private String templateEngine;


	/**
	 * Adds the.
	 *
	 * @param def the def
	 */
	void add(DefTag def) {
		defs.put(def.name, def);
	}

	/**
	 * Gets the connections.
	 *
	 * @return the connections
	 */
	public Collection<ConnectionTag> getConnections() {
		if(connectionsByMethod==null) connectionsByMethod=new LinkedHashMap<>();

		return connectionsByMethod.values();
	}

	/**
	 * Gets the ddl.
	 *
	 * @return the ddl
	 */
	@XmlElement(namespace="-//org.witerat/cafenated/sql")
	public DDL getDdl() {
		return ddl;
	}

	/**
	 * Gets the defs.
	 *
	 * @return the defs
	 */
	@XmlTransient
	public Map<String, DefTag> getDefs() {
		if(defs==null)defs=new HashMap<>();
		return defs;
	}

	/**
	 * Gets the dml.
	 *
	 * @return the dml
	 */
	@XmlElement(name="dml", namespace="-//org.witerat/cafenated/sql", type=DmlTag.class)
	public DmlTag getDml() {
		return dml;
	}

	/* (non-Javadoc)
	 * @see net.witerat.cafenatedsql.spi.driver.MacroLocator#getMacro(java.lang.String)
	 */
	@Override
	public MacroTag getMacro(String name) {
		return defs.get(name);
	}

	/**
	 * Sets the connections.
	 *
	 * @param connection the new connection
	 */
	@XmlElement(type=ConnectionTag.class, name="connection", namespace="-//org.witerat/cafenated/sql")
	public void setConnection(ConnectionTag connection) {
		if(connectionsByMethod==null)connectionsByMethod = new LinkedHashMap<>();
		connectionsByMethod.put(connection.getMethod(), connection);
	}

	
	/**
	 * Sets the dialect.
	 *
	 * @param dialect the new dialect
	 */
	@XmlElement(name="dialect", namespace="-//org.witerat/cafenated/sql", type=DialectTag.class)
	public void setDialect(DialectTag dialect) {
		if(dialectsById==null) dialectsById=new HashMap<>();
		dialectsById.put(dialect.getId(), dialect);
	}


	/**
	 * Sets the ddl.
	 *
	 * @param ddl the new ddl
	 */
	public void setDdl(DDL ddl) {
		this.ddl = ddl;
	}

	/**
	 * Sets the defs.
	 *
	 * @param defs the defs
	 */
	public void setDefs(Map<String, DefTag> defs) {
		this.defs = defs;
	}

	/**
	 * @param def a new def tag
	 */
	@XmlElement(name="def", type=DefTag.class, namespace="-//org.witerat/cafenated/sql")
	public void setDef(DefTag def){
		if(defs==null)defs=new HashMap<>();
		defs.put(def.name, def);
	}
	/**
	 * Sets the dml.
	 *
	 * @param dml the new dml
	 */
	public void setDml(DmlTag dml) {
		this.dml = dml;
	}

	/**
	 * @param name name of dialect
	 * @return the dialect associated with name
	 */
	public DialectTag getDialectByName(String name){
		return dialectsById==null?null:dialectsById.get(name);
	}
	/**
	 * Gets the dialects.
	 *
	 * @return the dialects
	 */
	public Collection<DialectTag> getDialects() {
		if(dialectsById==null) dialectsById=new HashMap<>();
		return dialectsById.values();
	}

	/**
	 * @return The meta tag.
	 */
	@XmlElement(namespace="-//org.witerat/cafenated/sql",type=MetaTag.class)
	public MetaTag getMeta() {
		// TODO Auto-generated method stub
		return meta;
	}

	/**
	 * @param meta the meta tag.
	 */
	public void setMeta(MetaTag meta) {
		this.meta = meta;
	}
	/**
	 * @return  the template engine id.
	 */
	@XmlElement(namespace="-//org.witerat/cafenated/sql", name="template-engine", type=String.class)
	public String getTemplateEngine(){
		return templateEngine;
	}

	/**
	 * @param templateEngine The template engine id.
	 */
	public void setTemplateEngine(String templateEngine) {
		this.templateEngine = templateEngine;
	}
	
}
