package net.witerat.cafenatedsql.spi.mock;

import net.witerat.cafenatedsql.api.Database;
import net.witerat.cafenatedsql.api.DatabaseFactory;
import net.witerat.cafenatedsql.api.DriverFactory;
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

public class MockDriverFactory implements DriverFactory, DatabaseFactory {

	@Override
	public Database newDatabase(TemplateEngineModel model) throws DriverCreationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Driver newDriver(TemplateEngineModel model) throws DriverCreationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConnectionType newConnectionType(Driver driver, String method) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DialectSelector newDialectExpressionSelector(ConnectionType ct, String nameExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DialectSelector newDialectNameSelector(ConnectionType ct, String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GrammarDef newGrammar(DialectDef dd, String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VerbDef newVerb(NounDef nn, String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NounDef newNoun(GrammarDef gg, String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DialectDef newDialectDefinition(Driver driver, String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UrlDef newUrlDef(ConnectionType ct) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ParameterType newParameter() {
		// TODO Auto-generated method stub
		return null;
	}

}
