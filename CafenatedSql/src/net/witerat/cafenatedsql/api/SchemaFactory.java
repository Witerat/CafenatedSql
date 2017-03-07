package net.witerat.cafenatedsql.api;

public interface SchemaFactory {
   Schema getSchema(String name);
   Schema createSchema();
}
