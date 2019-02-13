package net.witerat.cafenatedsql.api.mock;

import java.util.LinkedHashMap;

import net.witerat.cafenatedsql.api.Schema;
import net.witerat.cafenatedsql.api.SchemaFactory;
import net.witerat.cafenatedsql.spi.mock.MockSchema;

public class MockSchemaFactory implements SchemaFactory {
  LinkedHashMap<String, Schema> schemas = new LinkedHashMap<>();
  @Override
  public Schema getSchema(String name) {
    if( !schemas.containsKey(name)) {
      schemas.put(name, new MockSchema(name));
    }
    return schemas.get(name);
  }

  @Override
  public Schema createSchema() {
    return new MockSchema();
  }

  @Override
  public Schema dropSchema(String name) {
    return schemas.remove(name);
  }

}
