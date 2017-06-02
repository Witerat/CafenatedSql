package net.witerat.cafenatedsql.api.driver.template;

import java.util.Properties;

// TODO: Auto-generated Javadoc
/**
 * A simple implementation that backs a template model with a <code>java.util.Properties</code>
 * it has the limitation that values can only be strings.
 * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
 *
 */
public class SimplePropertiesModel implements TemplateEngineModel {
	
	/** The properties. */
	private final Properties properties;
	
	/**
	 * Instantiates a new simple properties model.
	 *
	 * @param p the p
	 */
	public SimplePropertiesModel(Properties p){
		properties=p;
	}
	
	/**
	 * Instantiates a new simple properties model.
	 */
	public SimplePropertiesModel() {
		properties=new Properties();
	}
	
	/* (non-Javadoc)
	 * @see net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel#getByExpression(java.lang.String)
	 */
	@Override
	public Object getByExpression(String name) {
		try {
			return evaluateContextExpression(name);
		} catch (ExpressionFailedException e) {
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel#get(java.lang.String)
	 */
	@Override
	public Object get(String key) {
		return getProperties().getProperty(key);
	}
	
	/* (non-Javadoc)
	 * @see net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel#set(java.lang.String, java.lang.Object)
	 */
	@Override
	public void set(String property, Object value) {
		 getProperties().setProperty(property, (String) value);
	}

	/* (non-Javadoc)
	 * @see net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel#evaluateContextExpression(java.lang.String)
	 */
	@Override
	public Object evaluateContextExpression(String expression) throws ExpressionFailedException {
		return get(expression);
	}

	/**
	 * Gets the properties.
	 *
	 * @return the properties
	 */
	public Properties getProperties() {
		return properties;
	}

}
