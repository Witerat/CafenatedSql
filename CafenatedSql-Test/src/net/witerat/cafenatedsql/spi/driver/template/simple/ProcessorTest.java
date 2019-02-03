package net.witerat.cafenatedsql.spi.driver.template.simple;

import static org.junit.Assert.*;

import org.junit.Test;

import net.witerat.cafenatedsql.api.driver.template.ExpressionFailedException;
import net.witerat.cafenatedsql.spi.driver.template.simple.Processor.LiteralFetch;

public class ProcessorTest {

  @Test
  public void test() {
    new Processor(new Processor.AbstractFetch[]{
        new Processor.LiteralFetch("hello", String.class) 
    });
  }

  @Test
  public void testLiteral() {
    final String value = "hello";
    final Class<String> type = String.class;
    final LiteralFetch lit = new Processor.LiteralFetch(value, type);
    assertEquals(value, lit.getLiteral());
    assertEquals(type, lit.getType());
  }
  
  @Test
  public void testCall() {
    final Processor processor = new Processor(new Processor.AbstractFetch[]{});
    
    processor.call(3);
    processor.execute0();
    assertEquals(3, processor.getIp());
    assertEquals(0, processor.getIp0());
    
    processor.call(7);
    processor.execute0();
    assertEquals(7, processor.getIp());
    assertEquals(3, processor.getIp0());
    assertEquals(0, processor.getIp1());
  }
  @Test
  public void testEndCall() {
    final Processor processor = new Processor(new Processor.AbstractFetch[]{});
    
    processor.call(3);
    processor.execute0();
    processor.call(7);
    processor.execute0();
    processor.call(15);
    processor.execute0();
    assertEquals(15, processor.getIp());
    assertEquals(7, processor.getIp0());
    assertEquals(3, processor.getIp1());
    assertEquals(1, processor.ipStack().size());
    assertEquals(0, (int)processor.ipStack().get(0));
    Exception fault = null;
    try {
      processor.endCall();
    } catch (ExpressionFailedException e) {
      fault = e;
    }
    assertNull("Exception:", fault);
    processor.execute0();
    assertEquals(7, processor.getIp());
    
    fault = null;
    try {
      processor.endCall();
    } catch (ExpressionFailedException e) {
      fault = e;
    }
    assertNull("Exception:", fault);
    processor.execute0();
    assertEquals(3, processor.getIp());
    
    fault = null;
    try {
      processor.endCall();
    } catch (ExpressionFailedException e) {
      fault = e;
    }
    assertNull("Exception", fault);
    processor.execute0();
    assertEquals(0, processor.getIp());
    
  }
}
