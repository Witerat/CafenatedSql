package net.witerat.cafenatedsql.spi.driver.template;

import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;

import net.witerat.cafenatedsql.api.driver.template.TemplateEngine;
import net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel;

// TODO: Auto-generated Javadoc
/**
 * The Class VelocityTemplateEngine wraps a Velocity context..
 */
public class VelocityTemplateEngine implements TemplateEngine {

	/** The template. */
	StringBuilder template = new StringBuilder();

	/** The fixtures. name text that can be inserted by invoking the Use tag. */
	private Map<String, String> fixtures = new LinkedHashMap<String, String>();

	/** The wrapped context context. */
	private Context context;

	/** The an engine reference to maintain resource information. */
	VelocityEngine engine;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.witerat.cafenatedsql.api.driver.template.TemplateEngine#injectFixture
	 * (java.lang.String, java.lang.String)
	 */
	@Override
	public void injectFixture(String name, String content) {
		fixtures.put(name, content);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.witerat.cafenatedsql.api.driver.template.TemplateEngine#define(java.
	 * lang.Object)
	 */
	@Override
	public void define(Object id) {
		// TODO Auto-generated method stub

	}

	/**
	 * Removes the fixture. the fixture will not be available for future
	 * templating.
	 *
	 * @param name
	 *            the name
	 */
	void removeFixture(String name) {
		fixtures.remove(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.witerat.cafenatedsql.api.driver.template.TemplateEngine#setModel(net.
	 * witerat.cafenatedsql.api.driver.template.TemplateEngineModel)
	 */
	@Override
	public void setModel(TemplateEngineModel tem) {
		context = ((VelocityTemplateModel) tem).getVelocity();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see TemplateEngine#produce(Object, TemplateEngineModel)
	 */
	@Override
	public String produce(Object id, TemplateEngineModel model) {
		StringWriter sw = new StringWriter();
		engine.evaluate(context, sw, "cafenated", template.toString());
		return sw.getBuffer().toString();
	}

	/**
	 * Append text to the velocity input that will be offer to velocity by
	 * {@link #produce(Object, TemplateEngineModel)}
	 * 
	 * @see net.witerat.cafenatedsql.api.driver.template.TemplateEngine#appendText(
	 *      java.lang.String)
	 */
	@Override
	public void appendText(String text) {
		template.append(text);

	}

	/**
	 * Append the named fixture to the input to velocity.
	 * 
	 * @see net.witerat.cafenatedsql.api.driver.template.TemplateEngine#appendFixture
	 *      (java.lang.String)
	 */
	@Override
	public void appendFixture(String name) {
		template.append(fixtures.get(name));
	}

}
