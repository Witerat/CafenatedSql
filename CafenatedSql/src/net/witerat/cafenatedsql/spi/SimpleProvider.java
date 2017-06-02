package net.witerat.cafenatedsql.spi;

import net.witerat.cafenatedsql.api.CafenatedFactory;
import net.witerat.cafenatedsql.api.ConnectionFactory;
import net.witerat.cafenatedsql.api.DialectFactory;
import net.witerat.cafenatedsql.api.DriverFactory;
import net.witerat.cafenatedsql.api.GrammarFactory;
import net.witerat.cafenatedsql.api.NounFactory;
import net.witerat.cafenatedsql.api.ParameterFactory;
import net.witerat.cafenatedsql.api.Provider;
import net.witerat.cafenatedsql.api.SchemaFactory;
import net.witerat.cafenatedsql.api.TableFactory;
import net.witerat.cafenatedsql.api.URLFactory;
import net.witerat.cafenatedsql.api.VerbFactory;
import net.witerat.cafenatedsql.api.ViewFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class SimpleProvider. This provider can be configured use a single object
 * to implement any or all of the factory services offers.
 */
public class SimpleProvider implements Provider {

	/** The connection factory. */
	private ConnectionFactory connectionFactory;

	/** The dialect factory. */
	private DialectFactory dialectFactory;

	/** The driver class. */
	private String driverClass;

	/** The driver factory. */
	private DriverFactory driverFactory;

	/** The grammar factory. */
	private GrammarFactory grammarFactory;

	/** The noun factory. */
	private NounFactory nounFactory;

	/** The parameter factory. */
	private ParameterFactory parameterFactory;

	/** The schema factory. */
	private SchemaFactory schemaFactory;

	/** The table factory. */
	private TableFactory tableFactory;

	/** The URL factory. */
	private URLFactory urlFactory;

	/** The verb factory. */
	private VerbFactory verbFactory;

	/** The view factory. */
	private ViewFactory viewFactory;

	/**
	 * Configures this provider to expose the factory services implemented by a
	 * specified factory.
	 * 
	 * @param factory
	 *            an implementor of factory some of the services published by
	 *            the provider.
	 */
	public void configure(CafenatedFactory factory) {
		if (factory instanceof ConnectionFactory)
			connectionFactory = (ConnectionFactory) factory;
		if (factory instanceof SchemaFactory)
			schemaFactory = (SchemaFactory) factory;
		if (factory instanceof TableFactory)
			tableFactory = (TableFactory) factory;
		if (factory instanceof ViewFactory)
			verbFactory = (VerbFactory) factory;
		if (factory instanceof DriverFactory)
			driverFactory = (DriverFactory) factory;
		if (factory instanceof GrammarFactory)
			grammarFactory = (GrammarFactory) factory;
		if (factory instanceof DialectFactory)
			dialectFactory = (DialectFactory) factory;
		if (factory instanceof NounFactory)
			nounFactory = (NounFactory) factory;
		if (factory instanceof VerbFactory)
			verbFactory = (VerbFactory) factory;
		if (factory instanceof ParameterFactory)
			parameterFactory = (ParameterFactory) factory;
		if (factory instanceof URLFactory)
			urlFactory = (URLFactory) factory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.witerat.cafenatedsql.api.Provider#getConnectionFactory()
	 */
	@Override
	public ConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.witerat.cafenatedsql.api.Provider#getDialectFactory()
	 */
	@Override
	public DialectFactory getDialectFactory() {

		return dialectFactory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.witerat.cafenatedsql.api.Provider#getDriverClass()
	 */
	@Override
	public String getDriverClass() {
		return driverClass;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.witerat.cafenatedsql.api.Provider#getDriverFactory()
	 */
	@Override
	public DriverFactory getDriverFactory() {
		return driverFactory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.witerat.cafenatedsql.api.Provider#getGrammarFactory()
	 */
	@Override
	public GrammarFactory getGrammarFactory() {
		return grammarFactory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.witerat.cafenatedsql.api.Provider#getNounFactory()
	 */
	@Override
	public NounFactory getNounFactory() {
		return nounFactory;
	}

	/**
	 * Gets the paramater factory.
	 *
	 * @return the paramater factory
	 */
	public ParameterFactory getParamaterFactory() {
		return parameterFactory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.witerat.cafenatedsql.api.Provider#getParameterFactory()
	 */
	@Override
	public ParameterFactory getParameterFactory() {
		return parameterFactory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.witerat.cafenatedsql.api.Provider#getSchemaFactory()
	 */
	@Override
	public SchemaFactory getSchemaFactory() {
		return schemaFactory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.witerat.cafenatedsql.api.Provider#getTableFactory()
	 */
	@Override
	public TableFactory getTableFactory() {
		return tableFactory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.witerat.cafenatedsql.api.Provider#getUrlFactory()
	 */
	@Override
	public URLFactory getUrlFactory() {
		return urlFactory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.witerat.cafenatedsql.api.Provider#getVerbFactory()
	 */
	@Override
	public VerbFactory getVerbFactory() {
		return verbFactory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.witerat.cafenatedsql.api.Provider#getViewFactory()
	 */
	@Override
	public ViewFactory getViewFactory() {
		return viewFactory;
	}

	/**
	 * Sets the connection factory.
	 *
	 * @param connectionFactory
	 *            the new connection factory
	 */
	public void setConnectionFactory(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	/**
	 * Sets the dialect factory.
	 *
	 * @param dialectFactory
	 *            the new dialect factory
	 */
	public void setDialectFactory(DialectFactory dialectFactory) {
		this.dialectFactory = dialectFactory;
	}

	/**
	 * Sets the driver class.
	 *
	 * @param driverClass
	 *            the new driver class
	 */
	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	/**
	 * Sets the driver factory.
	 *
	 * @param driverFactory
	 *            the new driver factory
	 */
	public void setDriverFactory(DriverFactory driverFactory) {
		this.driverFactory = driverFactory;
	}

	/**
	 * Sets the grammar factory.
	 *
	 * @param grammarFactory
	 *            the new grammar factory
	 */
	public void setGrammarFactory(GrammarFactory grammarFactory) {
		this.grammarFactory = grammarFactory;
	}

	/**
	 * Sets the noun factory.
	 *
	 * @param nounFactory
	 *            the new noun factory
	 */
	public void setNounFactory(NounFactory nounFactory) {
		this.nounFactory = nounFactory;
	}

	/**
	 * Sets the paramater factory.
	 *
	 * @param paramaterFactory
	 *            the new paramater factory
	 */
	public void setParamaterFactory(ParameterFactory paramaterFactory) {
		this.parameterFactory = paramaterFactory;
	}

	/**
	 * Sets the schema factory.
	 *
	 * @param schemaFactory
	 *            the new schema factory
	 */
	public void setSchemaFactory(SchemaFactory schemaFactory) {
		this.schemaFactory = schemaFactory;
	}

	/**
	 * Sets the table factory.
	 *
	 * @param tableFactory
	 *            the new table factory
	 */
	public void setTableFactory(TableFactory tableFactory) {
		this.tableFactory = tableFactory;
	}

	/**
	 * Sets the url factory.
	 *
	 * @param urlFactory
	 *            the new url factory
	 */
	public void setUrlFactory(URLFactory urlFactory) {
		this.urlFactory = urlFactory;
	}

	/**
	 * Sets the verb factory.
	 *
	 * @param verbFactory
	 *            the new verb factory
	 */
	public void setVerbFactory(VerbFactory verbFactory) {
		this.verbFactory = verbFactory;
	}

	/**
	 * Sets the view factory.
	 *
	 * @param viewFactory
	 *            the new view factory
	 */
	public void setViewFactory(ViewFactory viewFactory) {
		this.viewFactory = viewFactory;
	}

}
