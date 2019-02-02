package net.witerat.cafenatedsql.api.mock;

import java.sql.SQLException;

import net.witerat.cafenatedsql.api.Database;
import net.witerat.cafenatedsql.api.Schema;
import net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel;

public class MockDatabase implements Database {
  TemplateEngineModel model;
  public MockDatabase(final TemplateEngineModel model0) {
    this.model=model0;
  }

  @Override
  public void connect() {
    // TODO Auto-generated method stub

  }

  @Override
  public void close() throws SQLException {
    // TODO Auto-generated method stub

  }

  @Override
  public void add(Schema sch) {
    // TODO Auto-generated method stub

  }

  @Override
  public Schema getSchema(String name) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void update() throws SQLException {
    // TODO Auto-generated method stub

  }

}
