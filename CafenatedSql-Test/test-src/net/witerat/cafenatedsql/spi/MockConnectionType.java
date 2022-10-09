package net.witerat.cafenatedsql.spi;

import net.witerat.cafenatedsql.api.driver.ConnectionType;
import net.witerat.cafenatedsql.api.driver.UrlDef;
import net.witerat.cafenatedsql.spi.driver.DialectSelector;

public class MockConnectionType implements ConnectionType {

  @Override
  public String getName() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getDescription() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public DialectSelector getDialectSelector() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setDialectSelector(DialectSelector ds) {
    // TODO Auto-generated method stub

  }

  @Override
  public void setUrlDef(UrlDef url) {
    // TODO Auto-generated method stub

  }

  @Override
  public UrlDef getUrlDef() {
    // TODO Auto-generated method stub
    return null;
  }

}
