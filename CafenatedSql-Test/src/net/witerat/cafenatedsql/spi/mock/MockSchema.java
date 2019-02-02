package net.witerat.cafenatedsql.spi.mock;

import java.util.List;

import net.witerat.cafenatedsql.api.Refactor;
import net.witerat.cafenatedsql.api.Schema;

public class MockSchema implements Schema {
  String name;
  public MockSchema() {
    name=null;
  }
  public MockSchema(String name0){
    this.name=name0;
  }
  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(String name0) {
    this.name = name0;

  }

  @Override
  public void setJournal(List<Refactor> refactors) {
    // TODO Auto-generated method stub

  }

  @Override
  public void update() {
    // TODO Auto-generated method stub

  }

}
