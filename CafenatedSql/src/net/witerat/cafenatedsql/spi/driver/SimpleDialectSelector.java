package net.witerat.cafenatedsql.spi.driver;

import java.util.Collection;
import java.util.Iterator;

import net.witerat.cafenatedsql.api.driver.DialectDef;
import net.witerat.cafenatedsql.api.driver.template.ExpressionFailedException;
import net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel;

// TODO: Auto-generated Javadoc
/**
 * The Class SimpleDialectSelector.
 */
public class SimpleDialectSelector implements DialectSelector {
	
	/** The name. */
	final String name;
	
	/** The driver loc. */
	DriverLocator driverLoc;

	/**
	 * Instantiates a new simple dialect selector.
	 *
	 * @param name the name
	 * @param driverLoc the driver loc
	 */
	public SimpleDialectSelector(String name, DriverLocator driverLoc ) {
		this.name = name;
		this.driverLoc=driverLoc;
	}

	/* (non-Javadoc)
	 * @see net.witerat.cafenatedsql.spi.driver.DialectSelector#getDialect(net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel)
	 */
	public DialectDef getDialect(TemplateEngineModel model) {
		Collection<DialectDef> dialects = driverLoc.getDriver().getDialects();
		Iterator<DialectDef> i = dialects.iterator();
		return i.hasNext()?i.next():null;
	}


	/* (non-Javadoc)
	 * @see net.witerat.cafenatedsql.spi.driver.DialectSelector#getDialectName(net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel)
	 */
	public String getDialectName(TemplateEngineModel model) throws ExpressionFailedException {
		return name;
	}

}
