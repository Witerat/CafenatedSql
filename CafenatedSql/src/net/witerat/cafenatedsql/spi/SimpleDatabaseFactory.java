package net.witerat.cafenatedsql.spi;

import net.witerat.cafenatedsql.api.Database;
import net.witerat.cafenatedsql.api.DatabaseFactory;
import net.witerat.cafenatedsql.api.driver.DriverCreationException;
import net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel;

public class SimpleDatabaseFactory implements DatabaseFactory {

	@Override
	public Database newDatabase(TemplateEngineModel model) throws DriverCreationException {
		// TODO Auto-generated method stub
		return new SimpleDatabase((String)model.get("driver"),(String)model.get("method"),model);
	}

}
