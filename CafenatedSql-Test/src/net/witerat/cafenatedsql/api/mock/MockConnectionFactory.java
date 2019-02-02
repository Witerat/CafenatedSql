package net.witerat.cafenatedsql.api.mock;

import java.sql.Connection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import net.witerat.cafenatedsql.api.ConnectionFactory;
import net.witerat.cafenatedsql.spi.mock.MockConnection;

/**
 * Provides a non validating connection service to a database managed by a notional provider that 
 * produced the supplied properties offered to the constructor.. 
 * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
 *
 */
public class MockConnectionFactory implements ConnectionFactory {
  public static final String _MOCK_TYPE_INFO = "_mock_type_info";
  public static final String _MOCK_TYPE_REQUIRED = "_mock_type_required";
  String method;
  Map<String, Object> properties;
  Map<String, Class<?>> types;
  Set<String> required;


  public MockConnectionFactory(Map<String, Object> linkedHashMap) {
    this.method = (String) linkedHashMap.get("connection_method");
    this.properties = linkedHashMap;
    @SuppressWarnings("unchecked")
    Map<String, Class<?>> mock_types = (Map<String, Class<?>>) linkedHashMap.get(_MOCK_TYPE_INFO);
    this.types = mock_types;
    @SuppressWarnings("unchecked")
    Set<String> mock_require = (Set<String>) linkedHashMap.get(_MOCK_TYPE_REQUIRED);
    required = mock_require;
  }

  private Map<String, Class<?>> getTypes(){
    if(types==null){
      types = new LinkedHashMap<>();
    }
    return types;
  }
  
  private Set<String> getRequire(){
    if(required==null){
      required = new LinkedHashSet<String>();
    }
    return required;
  }
  @Override
  public void setProperty(Object property, Object value) {
    properties.put((String) property, value);

  }

  @Override
  public Object getProperty(Object property) {
    return properties.get(property);
  }

  @Override
  public Class<?> getPropertyType(Object property) {
    if(types==null) return null;
    return getTypes().get(property);
  }
  
  public void setPropertyType(Object property, Class<?> type){
    Map<String, Class<?>> t = getTypes();
    if(!t.containsKey(property)) {
      t.put((String) property, type);
    }
  }

  @Override
  public boolean isPropertyRequired(Object property) {
    if(required==null) return false;
    return getRequire().contains(property);
  }
  public void setRequired(Object property, boolean required0){
    Set<String> rqd=getRequire();
    if(required0 && !rqd.contains(property)){
      rqd.add((String) property);
    }else if(required0 && !rqd.contains(property)){
      rqd.remove((String) property);
    }
  }
  @Override
  public Connection connect() {
    return new MockConnection(properties, method);
  }

}
