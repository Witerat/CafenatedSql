package net.witerat.cafenatedsql.api;

import java.util.Properties;

import net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel;

/**
 * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
 *
 */
public interface TemplateModelFactory extends CafenatedFactory {

  /**
   * Creates a new model.
   *
   * @param defaults default properties of the model.
   * @return a new model
   */
  TemplateEngineModel newInstance(Properties defaults);

  /**
   * Creates an extension of the specified model.
   *
   * @param model a model providing defaults.
   * @return a new model which draws defaults values from <code>model</code>
   */
  TemplateEngineModel newInstance(TemplateEngineModel model);

}
