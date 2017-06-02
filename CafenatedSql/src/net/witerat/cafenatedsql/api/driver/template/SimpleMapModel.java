package net.witerat.cafenatedsql.api.driver.template;

import java.util.LinkedHashMap;
import java.util.Map;

// TODO: Auto-generated Javadoc
/**
 * The Class SimpleMapModel.
 */
public class SimpleMapModel implements TemplateEngineModel {
	
	/** The properties. */
	final Map<String, Object> properties;
	
	/**
	 * Instantiates a new simple map model.
	 *
	 * @param p the p
	 */
	SimpleMapModel(Map<String, Object> p){
		properties=p==null?new LinkedHashMap<>():p;
	}
	
	/**
	 * Instantiates a new simple map model.
	 */
	public SimpleMapModel() {
		properties=new LinkedHashMap<>();
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
		return properties.get(key);
	}
	
	/* (non-Javadoc)
	 * @see net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel#set(java.lang.String, java.lang.Object)
	 */
	@Override
	public void set(String property, Object value) {
		properties.put(property, (String) value);
	}

	/* (non-Javadoc)
	 * @see net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel#evaluateContextExpression(java.lang.String)
	 */
	@Override
	public Object evaluateContextExpression(String expression) throws ExpressionFailedException {
		return get(expression);
	}

}
