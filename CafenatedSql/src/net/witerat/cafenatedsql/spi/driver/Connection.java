package net.witerat.cafenatedsql.spi.driver;

import net.witerat.cafenatedsql.api.driver.ConnectionType;
import net.witerat.cafenatedsql.api.driver.DialectDef;
import net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel;

// TODO: Auto-generated Javadoc
/**
 * The Class Connection.
 *
 * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
 */
public class Connection {
	
	/** The type. */
	ConnectionType type;
	
	/** The model. */
	TemplateEngineModel model;

	/**
	 * 
	 * Instantiate a Connection object.
	 * @param type the connection type.
	 */
	public Connection(ConnectionType type) {
		this.type=type;
	}
	
	/**
	 * Gets the dialect that describes the SQL syntax used with this connection.
	 *
	 * @return the dialect
	 */
	DialectDef getDialect() {
		return type.getDialectSelector().getDialect(model);
	}

	/**
	 * Gets the model of properties that describe the connection and how it is used. .
	 *
	 * @return the model
	 */
	public TemplateEngineModel getModel() {
		return model;
	}

	/**
	 * Gets the type for the connection.
	 *
	 * @return the type
	 */
	public ConnectionType getType() {
		return type;
	}

	/**
	 * Sets the model of properties that describe the connection and how it is used. .
	 *
	 * @param model the new model
	 */
	public void setModel(TemplateEngineModel model) {
		this.model = model;
	}
}
