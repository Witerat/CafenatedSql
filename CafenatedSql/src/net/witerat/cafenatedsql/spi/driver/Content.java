package net.witerat.cafenatedsql.spi.driver;

import net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel;

/**
 * Implementor return text dynamically using the provided model.
 *
 * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
 *
 */
public interface Content {

  /**
   * Gets dynamically generated text according to evaluation of model content.
   *
   * @param model
   *          the model
   * @return fully expanded text including referenced macro and model
   *         injections.
   */
  String getText(TemplateEngineModel model);
}
