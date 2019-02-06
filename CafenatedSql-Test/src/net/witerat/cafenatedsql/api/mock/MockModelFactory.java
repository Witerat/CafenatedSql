package net.witerat.cafenatedsql.api.mock;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import net.witerat.cafenatedsql.api.TemplateModelFactory;
import net.witerat.cafenatedsql.api.driver.template.ExpressionFailedException;
import net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel;

public class MockModelFactory implements TemplateModelFactory {

  @Override
  public TemplateEngineModel newInstance(final Properties properties) {
    return new TemplateEngineModel() {
      Map<String, Object> map = new LinkedHashMap<>();

      @Override
      public Object get(String property) {
        return get0(property);
      }

      private Object get0(String property) {
        if (map.containsKey(property)) {
          return map.get(property);
        }
        return properties.get(property);
      }
      @Override
      public void set(String property, Object value) {
        map.put(property, value);
      }

      @Override
      public Object getByExpression(String expression) {
        return get0(expression);
      }

      @Override
      public Object evaluateContextExpression(String expression) throws ExpressionFailedException {
        return get0(expression);
      }

      @Override
      public Class<?> getPropertyType(Object property) {
        Object obj = get0((String) property);
        if (obj == null) {
          return null;
        }
        return obj.getClass();
      }
    };
  }

  @Override
  public TemplateEngineModel newInstance(TemplateEngineModel model) {
    return new TemplateEngineModel() {
      Map<String, Object> map = new LinkedHashMap<>();

      @Override
      public Object get(String property) {
        return get0(property);
      }

      private Object get0(String property) {
        if (map.containsKey(property)) {
          return map.get(property);
        }
        return model.get(property);
      }

      @Override
      public void set(String property, Object value) {
        map.put(property, value);
      }

      @Override
      public Object getByExpression(String expression) {
        return get0(expression);
      }

      @Override
      public Object evaluateContextExpression(String expression) throws ExpressionFailedException {
        return get0(expression);
      }

      @Override
      public Class<?> getPropertyType(Object property) {
        Object obj = get0((String) property);
        if (obj == null) {
          return null;
        }
        return obj.getClass();
      }
    };
  }

}
