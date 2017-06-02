package net.witerat.cafenatedsql.spi.driver;

import net.witerat.cafenatedsql.api.driver.DialectDef;
import net.witerat.cafenatedsql.api.driver.template.ExpressionFailedException;
import net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel;

// TODO: Auto-generated Javadoc
/**
 * The Interface DialectSelector.
 */
public interface DialectSelector {

	/**
	 * Gets the dialect.
	 *
	 * @param model the model
	 * @return the dialect
	 */
	DialectDef getDialect(TemplateEngineModel model);

	/**
	 * Gets the dialect name.
	 *
	 * @param model the model
	 * @return the dialect name
	 * @throws ExpressionFailedException the expression failed exception
	 */
	String getDialectName(TemplateEngineModel model) throws ExpressionFailedException;

}