package net.witerat.cafenatedsql.spi.driver;

import java.util.Collection;
import java.util.Map;

import net.witerat.cafenatedsql.api.driver.ConnectionType;
import net.witerat.cafenatedsql.api.driver.DialectDef;
import net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel;

// TODO: Auto-generated Javadoc
/**
 * The Class Driver.
 */
public class Driver {
	static {
		ResourceProtocol.init();
	}
	
	/** The model. */
	TemplateEngineModel model;
	
	/** The connection types by method. */
	Map<String, ConnectionType> connectionTypesByMethod;
	
	/** The dialects by name. */
	Map<String, DialectDef> dialectsByName;

	/**
	 * Sets the model.
	 *
	 * @param _model the new model
	 */
	public void setModel(TemplateEngineModel _model) {
		model = _model;
	}

	/**
	 * Adds a dialect to this driver.
	 *
	 * @param dd the dd
	 */
	public void addDialect(DialectDef dd) {
		dialectsByName.put(dd.getId(), dd);
	}

	/**
	 * Gets the dialects.
	 *
	 * @return the dialects
	 */
	public Collection<DialectDef> getDialects() {
		return dialectsByName.values();
	}

	/**
	 * Gets the connection type.
	 *
	 * @param method the method
	 * @return the connection type
	 */
	public ConnectionType getConnectionType(String method) {
		return connectionTypesByMethod.get(model.getByExpression(method));
		
	}
}
