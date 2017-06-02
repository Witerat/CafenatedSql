package net.witerat.cafenatedsql.spi.driver.tags;

import java.util.Collection;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import net.witerat.cafenatedsql.api.driver.Parameterized;

// TODO: Auto-generated Javadoc
/**
 * The Class ConnectionTag.
 */
@XmlType(name = "ConnectionType", namespace="-//org.witerat/cafenated/sql")
public class ConnectionTag implements Parameterized {
	
	/** The method name. */
	String method;
	
	/** The url. */
	UrlTag url;
	
	/** The dialect. */
	DialectTag dialect;
	
	/** The parameters descriptions. */
	private Collection<ParameterTag> parameters;

	/**
	 * Gets the name of the connection method.
	 *
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * Sets the name of the method.
	 *
	 * @param _method the new method
	 */
	@XmlAttribute
	public void setMethod(String _method) {
		this.method = _method;
	}

	/**
	 * Gets the url template description.
	 *
	 * @return the url
	 */
	@XmlElement(name = "url",namespace="-//org.witerat/cafenated/sql", type=UrlTag.class)
	public UrlTag getUrl() {
		return url;
	}

	/**
	 * Sets the url template description.
	 *
	 * @param url the new url
	 */
	public void setUrl(UrlTag url) {
		this.url = url;
	}

	/**
	 * Sets the associated dialect.
	 *
	 * @param _dialect the new dialect
	 */
	void setDialect(DialectTag _dialect) {
		dialect = _dialect;
	}

	/**
	 * Gets the associatd dialect.
	 *
	 * @return the dialect
	 */
	public DialectTag getDialect() {
		return dialect;
	}

	/* (non-Javadoc)
	 * @see net.witerat.cafenatedsql.api.driver.Parameterized#getParams()
	 */
	public Collection<ParameterTag> getParams() {
		return parameters;
	}
}
