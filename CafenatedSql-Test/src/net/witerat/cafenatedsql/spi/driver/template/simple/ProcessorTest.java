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
    Processor processor = new Processor(new Processor.AbstractFetch[]{lit});
    assertEquals(value, lit.getLiteral());
    assertEquals(type, lit.getType());
    try {
      processor.execute();
    } catch (ExpressionFailedException e) {
      fail("Exception: " + e.toString());
    }
    assertEquals(type, processor.getType());
  }
  
  @Test
  public void testCall() {
    final Processor processor = new Processor(new Processor.AbstractFetch[]{});
    
    processor.call(3);
    processor.execute0();
    assertEquals(3, processor.getIp());
    assertEquals(1, processor.getIp0());
    
    processor.call(7);
    processor.execute0();
    assertEquals(7, processor.getIp());
    assertEquals(4, processor.getIp0());
    assertEquals(1, processor.getIp1());
  }
  @Test
  public void testEndCall5() {
    final Processor processor = new Processor(new Processor.AbstractFetch[]{});
    
    processor.call(3);
    processor.execute0();
    processor.call(7);
    processor.execute0();
    processor.call(15);
    processor.execute0();
    processor.call(31);
    processor.execute0();
    processor.call(63);
    processor.execute0();
    assertEquals(63, processor.getIp());
    assertEquals(32, processor.getIp0());
    assertEquals(16, processor.getIp1());
    assertEquals(3, processor.ipStack().size());
    assertEquals(1, (int)processor.ipStack().get(0));
    Exception fault = null;
    try {
      processor.endCall();
    } catch (ExpressionFailedException e) {
      fault = e;
    }
    assertNull("Exception:", fault);
    processor.execute0();
    assertEquals(32, processor.getIp());
    fault = null;
    try {
      processor.endCall();
    } catch (ExpressionFailedException e) {
      fault = e;
    }
    assertNull("Exception:", fault);
    processor.execute0();
    assertEquals(16, processor.getIp());
    fault = null;
    try {
      processor.endCall();
    } catch (ExpressionFailedException e) {
      fault = e;
    }
    assertNull("Exception:", fault);
    processor.execute0();
    assertEquals(8, processor.getIp());
    
    fault = null;
    try {
      processor.endCall();
    } catch (ExpressionFailedException e) {
      fault = e;
    }
    assertNull("Exception:", fault);
    processor.execute0();
    assertEquals(4, processor.getIp());
    
    fault = null;
    try {
      processor.endCall();
    } catch (ExpressionFailedException e) {
      fault = e;
    }
    assertNull("Exception", fault);
    processor.execute0();
    assertEquals(1, processor.getIp());
    
  }

  @Test
  public void testEndCall4() {
    final Processor processor = new Processor(new Processor.AbstractFetch[]{});
    
    processor.call(3);
    processor.execute0();
    processor.call(7);
    processor.execute0();
    processor.call(15);
    processor.execute0();
    processor.call(31);
    processor.execute0();
    assertEquals(31, processor.getIp());
    assertEquals(16, processor.getIp0());
    assertEquals(8, processor.getIp1());
    assertEquals(2, processor.ipStack().size());
    assertEquals(1, (int)processor.ipStack().get(0));
    Exception fault = null;
    try {
      processor.endCall();
    } catch (ExpressionFailedException e) {
      fault = e;
    }
    assertNull("Exception:", fault);
    processor.execute0();
    assertEquals(16, processor.getIp());
    fault = null;
    try {
      processor.endCall();
    } catch (ExpressionFailedException e) {
      fault = e;
    }
    assertNull("Exception:", fault);
    processor.execute0();
    assertEquals(8, processor.getIp());
    
    fault = null;
    try {
      processor.endCall();
    } catch (ExpressionFailedException e) {
      fault = e;
    }
    assertNull("Exception:", fault);
    processor.execute0();
    assertEquals(4, processor.getIp());
    
    fault = null;
    try {
      processor.endCall();
    } catch (ExpressionFailedException e) {
      fault = e;
    }
    assertNull("Exception", fault);
    processor.execute0();
    assertEquals(1, processor.getIp());
    
  }
  @Test
  public void testEndCall3() {
    final Processor processor = new Processor(new Processor.AbstractFetch[]{});
    
    processor.call(3);
    processor.execute0();
    processor.call(7);
    processor.execute0();
    processor.call(15);
    processor.execute0();
    assertEquals(15, processor.getIp());
    assertEquals(8, processor.getIp0());
    assertEquals(4, processor.getIp1());
    assertEquals(1, processor.ipStack().size());
    assertEquals(1, (int)processor.ipStack().get(0));
    Exception fault = null;
    try {
      processor.endCall();
    } catch (ExpressionFailedException e) {
      fault = e;
    }
    assertNull("Exception:", fault);
    processor.execute0();
    assertEquals(8, processor.getIp());
    fault = null;
    try {
      processor.endCall();
    } catch (ExpressionFailedException e) {
      fault = e;
    }
    assertNull("Exception:", fault);
    processor.execute0();
    assertEquals(4, processor.getIp());
    
    fault = null;
    try {
      processor.endCall();
    } catch (ExpressionFailedException e) {
      fault = e;
    }
    assertNull("Exception", fault);
    processor.execute0();
    assertEquals(1, processor.getIp());
    
  }
  @Test
  public void testEndCall0Underflow() {
    Processor.EndCall ec = new Processor.EndCall();
    final Processor processor = new Processor(new Processor.AbstractFetch[]{ec});
    assertEquals(0, processor.getIp());
    assertEquals(0, processor.getIp0());
    assertEquals(0, processor.getIp1());
    assertEquals(0, processor.ipStack().size());
    Exception fault = null;
    try {
      processor.execute();
    } catch (ExpressionFailedException e) {
      fault = e;
    }
    assertNotNull("No underflow Exception" , fault);
    processor.execute0();
    assertEquals(2, processor.getIp());
    
  }
  @Test
  public void testEndCall0() {
    Processor.EndCall ec = new Processor.EndCall();
    final Processor processor = new Processor(new Processor.AbstractFetch[]{
        null, new Processor.Halt(), null, null, null, null, null
        ,ec});
    processor.call(7);
    processor.execute0();
    assertEquals(7, processor.getIp());
    assertEquals(1, processor.getIp0());
    assertEquals(0, processor.getIp1());
    assertEquals(0, processor.ipStack().size());
    Exception fault = null;
    try {
      processor.execute();
    } catch (ExpressionFailedException e) {
      fault = e;
    }
    assertNull("Exception: " +((fault != null)?fault.toString():""), fault);
    assertEquals(1, processor.getIp());
    
  }

  @Test 
  public void testBranch() {
    final Processor.Branch branch = new Processor.Branch(4);
    Processor.AbstractFetch[] plan = new Processor.AbstractFetch[]{
        branch
    };
    assertEquals(4, branch.getBranch());
    Processor fixture = new Processor(plan);
    try {
    fixture.execute();
    } catch (Exception e) {
      fail("Exception:" + e.toString());
    }
    assertEquals(4, fixture.getIp());
    assertEquals(-1, fixture.getBranch());
  }
  
  @Test
  public void testOperand0(){
    final Processor processor = new Processor(new Processor.AbstractFetch[]{});
    
    Exception fault = null;
    try {
      processor.operand(0);
    } catch (ExpressionFailedException e) {
      fault = e;
    }
    assertNotNull("Exception", fault);
    processor.execute0();
    
    
  }
  
  @Test
  public void testPop0(){
    final Processor processor = new Processor(new Processor.AbstractFetch[]{});
    
    Exception fault = null;
    try {
      processor.pop();
    } catch (ExpressionFailedException e) {
      fault = e;
    }
    assertNotNull("Exception", fault);
    processor.execute0();
    
    
  }
  @Test
  public void testOperand1(){
    final Processor processor = new Processor(new Processor.AbstractFetch[]{});
    processor.push(0);
    Exception fault = null;
    Object r = null;
    try {
      r=processor.operand(0);
    } catch (ExpressionFailedException e) {
      fault = e;
    }
    assertNull("Exception", fault);
    assertEquals((Integer)0, r);
    
    
  }

  @Test
  public void testOperand2(){
    final Processor processor = new Processor(new Processor.AbstractFetch[]{});
    processor.push(3);
    processor.push(0);
    Exception fault = null;
    Object r1 = null;
    Object r2 = null;
    try {
      r1=processor.operand(0);
    } catch (ExpressionFailedException e) {
      fault = e;
    }
    assertNull("Exception", fault);
    try {
      r2=processor.operand(0);
    } catch (ExpressionFailedException e) {
      fault = e;
    }
    assertNull("Exception", fault);
    assertEquals((Integer)0, r1);
    assertEquals((Integer)3, r2);
    
    
  }
  
  @Test
  public void testOperand3(){
    final Processor processor = new Processor(new Processor.AbstractFetch[]{});
    processor.push("blah");
    processor.push(3);
    processor.push(0);
    Exception fault = null;
    Object r1 = null;
    Object r2 = null;
    Object r3 = null;
    try {
      r1=processor.operand(0);
    } catch (ExpressionFailedException e) {
      fault = e;
    }
    assertNull("Exception", fault);
    try {
      r2=processor.operand(0);
    } catch (ExpressionFailedException e) {
      fault = e;
    }
    assertNull("Exception", fault);

    try {
      r3=processor.operand(0);
    } catch (ExpressionFailedException e) {
      fault = e;
    }
    assertNull("Exception", fault);

    assertEquals((Integer)0, r1);
    assertEquals((Integer)3, r2);
    assertEquals("blah", r3);
    
    
  }
  @Test
  public void testPop3(){
    final Processor processor = new Processor(new Processor.AbstractFetch[]{});
    processor.push("blah");
    processor.push(3);
    processor.push(0);
    Exception fault = null;
    Object r1 = null;
    Object r2 = null;
    Object r3 = null;
    try {
      r1 = processor.pop();
    } catch (ExpressionFailedException e) {
      fault = e;
    }
    assertNull("Exception", fault);
    try {
      r2 = processor.pop();
    } catch (ExpressionFailedException e) {
      fault = e;
    }
    assertNull("Exception", fault);

    try {
      r3 = processor.pop();
    } catch (ExpressionFailedException e) {
      fault = e;
    }
    assertNull("Exception", fault);

    assertEquals((Integer)0, r1);
    assertEquals((Integer)3, r2);
    assertEquals("blah", r3);
    
    
  }

  @Test
  public void testOperand5(){
    final Processor processor = new Processor(new Processor.AbstractFetch[]{});
    processor.push("blah");
    processor.push(3);
    processor.push(0);
    Exception fault = null;
    Object r1 = null;
    try {
      r1=processor.operand(7);
    } catch (ExpressionFailedException e) {
      fault = e;
    }
    assertNotNull("Exception", fault);
    assertEquals(null, r1);
    
    
  }
  
  @Test
  public void testHalt() {
    Processor.Halt halt = new Processor.Halt();
    final Processor processor = new Processor(new Processor.AbstractFetch[]{halt});
    try {
      processor.execute();
    } catch (ExpressionFailedException e) {
      fail("Exception: "+e.toString());
    }
  }
  
  @Test
  public void testMethod0() {
    final boolean[] called=new boolean[]{false};
    Object obj = new Object(){
      @SuppressWarnings("unused")
      public void method0(){
        called[0] = true;
      }
    };
    Processor.MethodFetch meth = new Processor.MethodFetch(
        obj.getClass(), 
        "method0", 
        new Class[]{},0);
    Processor processor= new Processor(new Processor.AbstractFetch[]{meth});
    processor.push(obj);
    try {
      processor.execute();
    } catch (ExpressionFailedException e) {
      fail("Exception: " + e.toString());
    }
    assertTrue(called[0]);
  }
  
  @Test
  public void testMethod0TIE() {
    final boolean[] called=new boolean[]{false};
    Object obj = new Object(){
      @SuppressWarnings("unused")
      public void method0(){
        called[0] = true;
        throw new RuntimeException("bugger");
      }
    };
    Processor.MethodFetch meth = new Processor.MethodFetch(
        obj.getClass(), 
        "method0", 
        new Class[]{},0);
    Processor processor= new Processor(new Processor.AbstractFetch[]{meth});
    processor.push(obj);
    ExpressionFailedException fault = null;
    try {
      processor.execute();
    } catch (ExpressionFailedException e) {
      fault=e;
    }
    assertNotNull(fault);
    assertTrue(called[0]);
  }
  
  @Test
  public void testMethod1() {
    final boolean[] called=new boolean[]{false};
    Object obj = new Object(){
      @SuppressWarnings("unused")
      public int method1(int x){
        called[0] = true;
        assertEquals(10, x);
        return x;
      }
    };
    Processor.MethodFetch meth = new Processor.MethodFetch(
        obj.getClass(), 
        "method1", 
        new Class[]{int.class},
        1);
    Processor processor= new Processor(new Processor.AbstractFetch[]{meth});
    processor.push(obj);
    processor.push(10);
    Object xx = null;
    try {
      processor.execute();
      xx = processor.pop();
    } catch (ExpressionFailedException e) {
      fail("Exception: " + e.toString());
    }
    assertEquals((Integer)10, xx);
    assertTrue(called[0]);
  }
  @Test
  public void testMethod1Underflow() {
    final boolean[] called=new boolean[]{false};
    Object obj = new Object(){
      @SuppressWarnings("unused")
      public void method3(int x, int y,int z){
        called[0] = true;
        assertEquals(10, x);
      }
    };
    Processor.MethodFetch meth = new Processor.MethodFetch(
        obj.getClass(), 
        "method3", 
        new Class[]{int.class, int.class, int.class},
        3);
    Processor processor= new Processor(new Processor.AbstractFetch[]{meth});
    processor.push(obj);
    Exception fault = null;
    try {
      processor.execute();
    } catch (ExpressionFailedException e) {
      fault = e;
    }
    assertNotNull(fault);
    assertFalse(called[0]);
  }
  @Test
  public void testMethod1NSM() {
    final boolean[] called=new boolean[]{false};
    Object obj = new Object(){
      @SuppressWarnings("unused")
      public void method2(int x, int y){
        called[0] = true;
        assertEquals(10, x);
      }
    };
    Processor.MethodFetch meth = new Processor.MethodFetch(
        obj.getClass(), 
        "method3", 
        new Class[]{int.class, int.class, int.class},
        3);
    Processor processor= new Processor(new Processor.AbstractFetch[]{meth});
    processor.push(obj);
    Exception fault = null;
    try {
      processor.execute();
    } catch (ExpressionFailedException e) {
      fault = e;
    }
    assertNotNull(fault);
    assertFalse(called[0]);
  }
}
