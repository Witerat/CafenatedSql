package net.witerat.cafenatedsql.api;

import net.witerat.cafenatedsql.api.driver.DriverCreationException;
import net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel;

public interface DatabaseFactory extends CafenatedFactory {

	Database newDatabase(TemplateEngineModel model) throws DriverCreationException;

}