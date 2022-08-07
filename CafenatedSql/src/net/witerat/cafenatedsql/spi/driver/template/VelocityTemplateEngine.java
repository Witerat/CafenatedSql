package net.witerat.cafenatedsql.spi.driver.template;

import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import net.witerat.cafenatedsql.api.driver.template.TemplateEngine;
import net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;


/**
 * The Class VelocityTemplateEngine wraps a Velocity context..
 */
public class VelocityTemplateEngine implements TemplateEngine {

  /** The template. */
  private StringBuilder template = new StringBuilder();

  /** The fixtures. name text that can be inserted by invoking the Use tag. */
  private Map<String, String> fixtures = new LinkedHashMap<String, String>();

  /** The wrapped context context. */
  private Context context;

  /** The an engine reference to maintain resource information. */
  private VelocityEngine engine;

  /**
   * {@inheritDoc}
   * @see net.witerat.cafenatedsql.api.driver.template
   *    .TemplateEngine#injectFixture(java.lang.String, java.lang.String)
   */
  @Override
  public void injectFixture(final String name, final String content) {
    fixtures.put(name, content);
  }

  /**
   * {@inheritDoc}
   * @see net.witerat.cafenatedsql.api.driver.template
   *    .TemplateEngine#define(java.lang.Object)
   */
  @Override
  public void define(final Object id) {
    context.put(
        id.getClass().getName() + "@" + System.identityHashCode(id), null);
  }

  /**
   * Removes the fixture. The fixture will not be available for future
   * templating.
   *
   * @param name
   *          The name of the fixtre to be removed.
   */
  public void removeFixture(final String name) {
    fixtures.remove(name);
  }

  /**
   * {@inheritDoc}
   * @see
   * net.witerat.cafenatedsql.api.driver.template.TemplateEngine#setModel(net.
   * witerat.cafenatedsql.api.driver.template.TemplateEngineModel)
   */
  @Override
  public void setModel(final TemplateEngineModel tem) {
    context = ((VelocityTemplateModel) tem).getVelocity();
  }

  /**
   * {@inheritDoc}
   * @see TemplateEngine#produce(Object, TemplateEngineModel)
   */
  @Override
  public String produce(final Object id, final TemplateEngineModel model) {
    StringWriter sw = new StringWriter();
    engine.evaluate(context, sw, "cafenated", template.toString());
    return sw.getBuffer().toString();
  }

  /**
   * Append text to the velocity input that will be offer to velocity
   * by {@link #produce(Object, TemplateEngineModel)}.
   *
   * @see net.witerat.cafenatedsql.api.driver.template.TemplateEngine
   *    #appendText(java.lang.String)
   */
  @Override
  public void appendText(final String text) {
    template.append(text);

  }

  /**
   * Append the named fixture to the input to velocity.
   *
   * @see net.witerat.cafenatedsql.api.driver.template.TemplateEngine
   *    #appendFixture(java.lang.String)
   */
  @Override
  public void appendFixture(final String name) {
    template.append(fixtures.get(name));
  }

}
