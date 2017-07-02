package net.witerat.cafenatedsql.api;

import java.util.Properties;

import net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel;

public interface TemplateModelFactory extends CafenatedFactory{

	TemplateEngineModel newInstance(Properties properties);

}
