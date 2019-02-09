package net.witerat.cafenatedsql.spi.driver.template.simple;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Properties;

import org.junit.Test;

import net.witerat.cafenatedsql.api.driver.template.ExpressionFailedException;
import net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel;
import net.witerat.cafenatedsql.api.mock.MockModelFactory;
import net.witerat.cafenatedsql.spi.driver.template.simple.Processor.Call;
import net.witerat.cafenatedsql.spi.driver.template.simple.Processor.LiteralFetch;
import net.witerat.cafenatedsql.spi.driver.template.simple.fixtures.FieldFetchIAEFixture;

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
  public void testCallOp() {
    Call call = new Processor.Call(7);
    final Processor processor = new Processor(new Processor.AbstractFetch[]{
        call, new Processor.Halt()
    });
    
    
    try {
      processor.execute();
    } catch (ExpressionFailedException e) {
      fail("Exception: " + e.toString());
    }
    assertEquals(7, processor.getIp());
    assertEquals(2, processor.getIp0());
    
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
  
  @Test
  public void testMap() {
    Processor.MapFetch mf = new Processor.MapFetch();
    Processor fixture = new Processor(new Processor.AbstractFetch[]{mf});
    HashMap<String, String> map = new HashMap<>();
    map.put("hello", "there");
    fixture.push(map);
    fixture.push("hello");
    try {
      fixture.execute();
    } catch (ExpressionFailedException efe){
      fail(efe.toString());
    }
    Object rValue = null;
    try {
      rValue = fixture.pop();
    } catch (ExpressionFailedException e) {
      fail(e.toString());
    }
    assertEquals("there", rValue);
  }

  @Test
  public void testMapNoMap() {
    Processor.MapFetch mf = new Processor.MapFetch();
    Processor fixture = new Processor(new Processor.AbstractFetch[]{mf});
    HashMap<String, String> map = new HashMap<>();
    map.put("hello", "there");
    fixture.push(null);
    fixture.push("hello");
    Exception fault = null;
    try {
      fixture.execute();
    } catch (ExpressionFailedException efe){
      fault = efe;
    }
    assertNotNull(fault);
  }
  
  @Test
  public void testMapNotMap() {
    Processor.MapFetch mf = new Processor.MapFetch();
    Processor fixture = new Processor(new Processor.AbstractFetch[]{mf});
    HashMap<String, String> map = new HashMap<>();
    map.put("hello", "there");
    fixture.push("bugger");
    fixture.push("hello");
    Exception fault = null;
    try {
      fixture.execute();
    } catch (ExpressionFailedException efe){
      fault = efe;
    }
    assertNotNull(fault);
  }
  
  @Test
  public void testFieldFetch(){
    Object obj = new Object (){
      @SuppressWarnings("unused")
      public String field = "TheField";
    };
    Processor.FieldFetch  ff = new Processor.FieldFetch("field");
    Processor processor = new Processor(new Processor.AbstractFetch[]{ff});
    processor.push(obj);
    Object rValue = null;
    try {
      processor.execute();
      rValue = processor.pop();
    } catch (ExpressionFailedException e) {
      fail(e.toString());
    }
    assertEquals("TheField", rValue);
  }
  
  @Test
  public void testFieldFetchNSF(){
    Object obj = new Object (){
      @SuppressWarnings("unused")
      public String field = "TheField";
    };
    Processor.FieldFetch  ff = new Processor.FieldFetch("wrongfield");
    Processor processor = new Processor(new Processor.AbstractFetch[]{ff});
    processor.push(obj);
    Exception fault = null;
    try {
      processor.execute();
    } catch (ExpressionFailedException e) {
      fault = e;
    }
    assertNotNull(fault);
  }

  @Test
  public void testFieldFetchIAE(){
    Object obj = new FieldFetchIAEFixture();
    Processor.FieldFetch  ff = new Processor.FieldFetch("field");
    Processor processor = new Processor(new Processor.AbstractFetch[]{ff});
    processor.push(obj);
    Exception fault = null;
    try {
      processor.execute();
    } catch (ExpressionFailedException e) {
      fault = e;
    }
    assertNotNull(fault);
  }

  @Test
  public void testCastFetch(){
    Processor.CastFetch  cf = new Processor.CastFetch(String.class);
    Processor processor = new Processor(new Processor.AbstractFetch[]{cf});
    processor.push("hello");
    Object rValue = null;
    try {
      processor.execute();
      rValue = processor.pop();
    } catch (ExpressionFailedException e) {
      fail(e.toString());
    }
    assertEquals("hello", rValue);
  }
  
  @Test
  public void testCastFetchCCE(){
    Processor.CastFetch  cf = new Processor.CastFetch(Number.class);
    Processor processor = new Processor(new Processor.AbstractFetch[]{cf});
    processor.push("hello");
    Object rValue = null;
    Exception fault = null;
    try {
      processor.execute();
      rValue = processor.pop();
    } catch (ExpressionFailedException e) {
      fault = e;
    }
    assertNotNull(fault);
    assertEquals(null, rValue);
  }
  
  @Test
  public void testPeek2(){
    Processor processor = new Processor(new Processor.AbstractFetch[]{});
    boolean complete = false;
    processor.push("hello");
    processor.push("there");
    try {
    Object rValue = processor.pop();
    assertEquals("there", rValue);
    rValue = processor.peek();
    assertEquals("hello", rValue);
    rValue = processor.pop();
    assertEquals("hello", rValue);
    complete = true;
    } catch (ExpressionFailedException e) {
      fail("exception"+e.toString());
    } finally {
      if (!complete){
        fail("incomplete");
      }
    }
  }

  @Test
  public void testPeek3(){
    Processor processor = new Processor(new Processor.AbstractFetch[]{});
    boolean complete = false;
    processor.push("hello");
    processor.push("there");
    processor.push("Oh arrrrh!");
    try {
      Object rValue = processor.pop();
      assertEquals("Oh arrrrh!", rValue);
      rValue = processor.pop();
      assertEquals("there", rValue);
      rValue = processor.peek();
      assertEquals("hello", rValue);
      rValue = processor.pop();
      assertEquals("hello", rValue);
      complete = true;
    } catch (ExpressionFailedException e) {
      fail("exception"+e.toString());
    } finally {
      if (!complete){
        fail("incomplete");
      }
    }
  }

  @Test
  public void testPeek0(){
    Processor processor = new Processor(new Processor.AbstractFetch[]{});
    Exception fault = null;
    try {
    Object rValue = processor.peek();
    assertEquals("hello", rValue);
    } catch (ExpressionFailedException e) {
      fault = e;
    }
    assertNotNull(fault);
  }
  
  @Test public void testModelFetch(){
    Properties properties = new Properties();
    properties.put("bean", "theBean");
    TemplateEngineModel model = new MockModelFactory().newInstance(properties); 
    Processor.ModelFetch  mf = new Processor.ModelFetch(model, "bean");
    Processor processor = new Processor(new Processor.AbstractFetch[]{mf});
    Object rValue = null;
    try {
      processor.execute();
      rValue = processor.pop();
    } catch (ExpressionFailedException e) {
      fail(e.toString());
    }
    assertEquals("theBean", rValue);
  }

  @Test public void testModelFetchNoModel() {
    Processor.ModelFetch  mf = new Processor.ModelFetch(null, "bean");
    Processor processor = new Processor(new Processor.AbstractFetch[]{mf});
    Object rValue = null;
    Exception fault = null;
    try {
      processor.execute();
      rValue = processor.pop();
    } catch (ExpressionFailedException e) {
      fault = e;
    }
    assertNotNull(fault);
    assertNull(rValue);
  }

  @Test public void testModelFetchNoBean() {
    Properties properties = new Properties();
    TemplateEngineModel model = new MockModelFactory().newInstance(properties); 
    Processor.ModelFetch  mf = new Processor.ModelFetch(model, "bean");
    Processor processor = new Processor(new Processor.AbstractFetch[]{mf});
    Object rValue = Boolean.FALSE;
    try {
      processor.execute();
      rValue = processor.pop();
    } catch (ExpressionFailedException e) {
      fail(e.toString());
    }
    assertEquals(null, rValue);
  }

  @Test
  public void testBranchIfFalse() {
    Object[][] fixtures = new Object[][]{
      new Object[]{null,Boolean.FALSE, "", (short)0, 0l,(byte)0, 0, '\u0000',0.0f,0.0d,BigDecimal.ZERO, BigInteger.ZERO},
      new Object[]{true, "stuff", (short)1,1l,(byte)1,1,'X',1.0f,1.0d, BigDecimal.ONE,BigInteger.ONE}
    };
    int ifx = 0, expectedIp=3;
    for(Object[] fa:fixtures){
      for(Object fav:fa) {
        Processor.BranchIfFalse bif = new Processor.BranchIfFalse(3);
        Processor.Halt halt= new Processor.Halt();
        Processor processor = new Processor(new Processor.AbstractFetch[]{bif, halt,halt, halt});
        processor.push(fav);
        try {
          processor.execute();
        } catch (ExpressionFailedException e) {
          fail("exception for value "+fav+": "+e.toString());
        } catch (ClassCastException e) {
          fail("exception for value "+fav+": "+e.toString());
          
        }
        final String msg = "for value ".concat((fav==null?"null":("".equals(fav)?"empty string":fav.toString())))
            .concat(" expected ")
            .concat(""+expectedIp).concat(" but was ").concat(""+processor.getIp());
        assertTrue(msg , 
            expectedIp == processor.getIp());
      }
      assertTrue(ifx++<2);
      expectedIp = 2;
    }
  }
}
