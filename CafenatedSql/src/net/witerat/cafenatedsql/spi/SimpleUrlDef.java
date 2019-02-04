package net.witerat.cafenatedsql.spi;

import java.util.Collection;
import java.util.LinkedHashMap;

import net.witerat.cafenatedsql.api.driver.ParameterType;
import net.witerat.cafenatedsql.api.driver.UrlDef;
import net.witerat.cafenatedsql.api.driver.template.TemplateEngine;
import net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel;
import net.witerat.cafenatedsql.spi.driver.Content;
import net.witerat.cafenatedsql.spi.driver.tags.TextContent;

/**
 * Simply defines a URL template which may have parameters;.
 *
 * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
 */
public class SimpleUrlDef implements UrlDef {

  /** The parameters. */
  private LinkedHashMap<String, ParameterType> parameters;

  /** The content. */
  private Collection<Content> content;

  /**
   * Produce the URL by expanding the content - injecting model elements and
   * including referenced macro and invoking template language mechanisms.
   * This implementation uses the content from the tags of the XML template
   * driver description file.
   *
   * @param engine
   *          the template engine.
   * @param model
   *          the model
   * @return the string
   */
  public String expand(final TemplateEngine engine,
      final TemplateEngineModel model) {
    engine.setModel(model);
    for (Content o : content) {
      engine.appendText(((Content) o).getText(model));
    }
    return engine.produce(this, model);
  }

  /**
   * get a collection of the parameters described for this URL.
   *
   * @see UrlDef#getParameters()
   */
  @Override
  public Collection<ParameterType> getParameters() {
    return parameters.values();
  }

  /**
   * Adds a parameter.
   *
   * @see UrlDef#setParameter(ParameterType)
   */
  @Override
  public void setParameter(final ParameterType type) {
    parameters.put(type.getName(), type);
  }

  /**
   * Adds a parameter to this URL definition.
   *
   * @see net.witerat.cafenatedsql.api.driver.ParameterizedRequest#add(
   *    net.witerat.cafenatedsql.api.driver.ParameterType)
   * @see #setParameter(ParameterType)
   */
  @Override
  public void add(final ParameterType p) {
    setParameter(p);
  }

  /**
   * Adds a content element to this URL definition.
   *
   * @see net.witerat.cafenatedsql.api.driver.UrlDef#add(
   *    net.witerat.cafenatedsql.spi.driver.Content)
   */
  @Override
  public void add(final Content c) {
    content.add(c);
  }

  /**
   * Adds text to the template or the URL.
   *
   * @param c
   *          the c
   */
  public void addText(final String c) {
    content.add(new TextContent(c));
  }
}
