package net.witerat.cafenatedsql.spi.driver;

import net.witerat.cafenatedsql.api.driver.ConnectionType;
import net.witerat.cafenatedsql.api.driver.UrlDef;

// TODO: Auto-generated Javadoc
/**
 * The Class SimpleConnectionType.
 */
public class SimpleConnectionType implements ConnectionType {
	
	/** The dialect selector. */
	DialectSelector dialectSelector;
	
	/** The name. */
	String name;

	/** The description. */
	String description;


	/** The url def. */
	private UrlDef urlDef;
	
	/* (non-Javadoc)
	 * @see net.witerat.cafenatedsql.api.driver.ConnectionType#getDescription()
	 */
	@Override
	public String getDescription() {
		return description;
	}

	/* (non-Javadoc)
	 * @see net.witerat.cafenatedsql.api.driver.ConnectionType#getDialectSelector()
	 */
	@Override
	public DialectSelector getDialectSelector() {
		return dialectSelector;
	}

	/* (non-Javadoc)
	 * @see net.witerat.cafenatedsql.api.driver.ConnectionType#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see net.witerat.cafenatedsql.api.driver.ConnectionType#setDialectSelector(net.witerat.cafenatedsql.spi.driver.DialectSelector)
	 */
	@Override
	public void setDialectSelector(DialectSelector dialectSelector) {
		this.dialectSelector = dialectSelector;
	}

	/* (non-Javadoc)
	 * @see net.witerat.cafenatedsql.api.driver.ConnectionType#setUrlDef(net.witerat.cafenatedsql.api.driver.UrlDef)
	 */
	@Override
	public void setUrlDef(UrlDef url) {
		urlDef=url;
		
	}
	
	/* (non-Javadoc)
	 * @see net.witerat.cafenatedsql.api.driver.ConnectionType#getUrlDef()
	 */
	@Override
	public UrlDef getUrlDef() {
		return urlDef;
	}
}
