package net.witerat.cafenatedsql.spi;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import net.witerat.cafenatedsql.api.ConnectionFactory;
import net.witerat.cafenatedsql.api.Database;
import net.witerat.cafenatedsql.api.Provider;
import net.witerat.cafenatedsql.api.ProviderRegistrar;
import net.witerat.cafenatedsql.api.Refactor;
import net.witerat.cafenatedsql.api.Schema;
import net.witerat.cafenatedsql.api.driver.DriverCreationException;
import net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel;
import net.witerat.cafenatedsql.spi.driver.Driver;

/**
 * Base inplementaion of database system to be refactored..
 * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
 *
 */
public class SimpleDatabase implements Database {

	private ConnectionFactory connectionFactory;
	private Connection connection;
	private Driver driver;
	private Provider provider;
	private List<Refactor> refactors;

	private SimpleDatabase() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Instantiate a(n) SimpleDatabase object.
	 * @param providerName
	 * @param method
	 * @param properties
	 * @throws DriverCreationException
	 */
	public SimpleDatabase(String providerName, String method, TemplateEngineModel properties)
			throws DriverCreationException {
		ProviderRegistrar root = ProviderRegistrar.ROOT_REGISTRAR;
		this.provider = root.getProvider(providerName);
		TemplateEngineModel tem = provider.getModelFactory().newInstance(properties);
		driver = this.provider.getDriverFactory().newDriver(tem);
		connectionFactory = provider.getConnectionFactory(method);
	}

	public void add(Schema schema) {
		refactors.add((Refactor)schema);
	}

	/**
	 * @return an anonymous Schema. 
	 */
	public Schema getSchema() {
		Schema newSchema = provider.getSchemaFactory().createSchema();
		newSchema.setJournal(refactors);
		return newSchema;

	}

	/**
	 * @param name The name of an existing schema.
	 * @return A representation of the specified schema entity.
	 */
	public Schema getSchema(String name) {
		Schema schema = provider.getSchemaFactory().getSchema(name);
		schema.setJournal(refactors);
		return schema;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.witerat.cafenatedsql.api.Database#connect()
	 */
	@Override
	public void connect() {
		connection = connectionFactory.connect();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.witerat.cafenatedsql.api.Database#close()
	 */
	@Override
	public void close() throws SQLException {
		connection.close();
		connection = null;

	}
}
