package net.witerat.cafenatedsql.spi;

import net.witerat.cafenatedsql.api.DialectFactory;
import net.witerat.cafenatedsql.api.DriverFactory;
import net.witerat.cafenatedsql.api.GrammarFactory;
import net.witerat.cafenatedsql.api.NounFactory;
import net.witerat.cafenatedsql.api.ParameterFactory;
import net.witerat.cafenatedsql.api.Provider;
import net.witerat.cafenatedsql.api.URLFactory;
import net.witerat.cafenatedsql.api.VerbFactory;
import net.witerat.cafenatedsql.api.driver.ConnectionType;
import net.witerat.cafenatedsql.api.driver.DialectDef;
import net.witerat.cafenatedsql.api.driver.DriverCreationException;
import net.witerat.cafenatedsql.api.driver.GrammarDef;
import net.witerat.cafenatedsql.api.driver.NounDef;
import net.witerat.cafenatedsql.api.driver.ParameterType;
import net.witerat.cafenatedsql.api.driver.UrlDef;
import net.witerat.cafenatedsql.api.driver.VerbDef;
import net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel;
import net.witerat.cafenatedsql.spi.driver.DialectSelector;
import net.witerat.cafenatedsql.spi.driver.Driver;
import net.witerat.cafenatedsql.spi.driver.DynamicDialectSelector;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating DefaultDriver objects.
 */
public class DefaultDriverFactory implements DriverFactory {
	
	/** The provider. */
	final Provider provider;

	/**
	 * Instantiates a new default driver factory.
	 *
	 * @param provider the provider
	 */
	public DefaultDriverFactory(Provider provider) {
		this.provider = provider;
	}

	
	/* (non-Javadoc)
	 * @see net.witerat.cafenatedsql.api.DriverFactory#newDriver(net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel)
	 */
	@Override
	public Driver newDriver(TemplateEngineModel model) throws DriverCreationException {
		 Class<Driver> driverType;
		try {
			@SuppressWarnings("unchecked") 
			Class<Driver>dt = (Class<Driver>) Class.forName(provider.getDriverClass());
			driverType=dt;
			return driverType.newInstance();
		} catch (ClassNotFoundException e) {
			throw new DriverCreationException("Driver class " + provider.getDriverClass() + "not in classpath", e);
		} catch (InstantiationException e) {
			throw new DriverCreationException("Driver failed, may be misconfigured", e);
		} catch (IllegalAccessException e) {
			throw new DriverCreationException("attempt to improperly use driver", e);
		}
	}

	/* (non-Javadoc)
	 * @see net.witerat.cafenatedsql.api.DriverFactory#newConnectionType(net.witerat.cafenatedsql.spi.driver.Driver, java.lang.String)
	 */
	@Override
	public ConnectionType newConnectionType(Driver driver, String method) {
		return driver.getConnectionType(method);
	}

	/* (non-Javadoc)
	 * @see net.witerat.cafenatedsql.api.DriverFactory#newDialectExpressionSelector(net.witerat.cafenatedsql.api.driver.ConnectionType, java.lang.String)
	 */
	@Override
	public DialectSelector newDialectExpressionSelector(ConnectionType ct, String nameExpression) {
		DynamicDialectSelector dynamicDialectSelector = new DynamicDialectSelector(nameExpression, true);
		return dynamicDialectSelector;
	}

	/* (non-Javadoc)
	 * @see net.witerat.cafenatedsql.api.DriverFactory#newDialectNameSelector(net.witerat.cafenatedsql.api.driver.ConnectionType, java.lang.String)
	 */
	@Override
	public DialectSelector newDialectNameSelector(ConnectionType ct, String name) {
		DynamicDialectSelector dynamicDialectSelector = new DynamicDialectSelector(name, false);
		return dynamicDialectSelector;
	}

	/* (non-Javadoc)
	 * @see net.witerat.cafenatedsql.api.DriverFactory#newGrammar(net.witerat.cafenatedsql.api.driver.DialectDef, java.lang.String)
	 */
	@Override
	public GrammarDef newGrammar(DialectDef dd, String name) {
		GrammarFactory gf = provider.getGrammarFactory();
		GrammarDef gd = gf.newGrammar(dd, name);
		gd.setName(name);
		dd.addGrammar(gd);
		return gd;
	}

	/* (non-Javadoc)
	 * @see net.witerat.cafenatedsql.api.DriverFactory#newVerb(net.witerat.cafenatedsql.api.driver.NounDef, java.lang.String)
	 */
	@Override
	public VerbDef newVerb(NounDef nn, String name) {
		VerbFactory vf =provider.getVerbFactory();
		VerbDef vd=vf.newVerb(nn, name);
		vd.setName(name);
		nn.addVerb(vd);
		return vd;
	}

	/* (non-Javadoc)
	 * @see net.witerat.cafenatedsql.api.DriverFactory#newNoun(net.witerat.cafenatedsql.api.driver.GrammarDef, java.lang.String)
	 */
	@Override
	public NounDef newNoun(GrammarDef gg, String name) {
		NounFactory nf= provider.getNounFactory();
		NounDef nd=nf.newNoun(gg, name);
		nd.setName(name);
		return nd;
	}

	/* (non-Javadoc)
	 * @see net.witerat.cafenatedsql.api.DriverFactory#newDialectDefinition(net.witerat.cafenatedsql.spi.driver.Driver, java.lang.String)
	 */
	@Override
	public DialectDef newDialectDefinition(Driver driver, String name) {
		DialectFactory df=provider.getDialectFactory();
		DialectDef dd=df.newDialect(driver, name);
		dd.setName(name);
		return dd;
	}

	/* (non-Javadoc)
	 * @see net.witerat.cafenatedsql.api.DriverFactory#newUrlDef(net.witerat.cafenatedsql.api.driver.ConnectionType)
	 */
	@Override
	public UrlDef newUrlDef(ConnectionType ct) {
		URLFactory uf=provider.getUrlFactory();
		return uf.newUrl();
	}

	/* (non-Javadoc)
	 * @see net.witerat.cafenatedsql.api.DriverFactory#newParameter()
	 */
	@Override
	public ParameterType newParameter() {
		ParameterFactory parameterFactory = provider.getParameterFactory();
		return parameterFactory.newParameter();
	}
}
