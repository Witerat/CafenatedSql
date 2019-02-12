package net.witerat.cafenatedsql.spi.driver.template.simple;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Stack;

import org.junit.Before;
import org.junit.Test;

import net.witerat.cafenatedsql.api.driver.template.ExpressionFailedException;
import net.witerat.cafenatedsql.spi.driver.template.simple.Compiler.CompileState;
import net.witerat.cafenatedsql.spi.driver.template.simple.Processor.AbstractFetch;

public class CompileStateTest  {
  CompileState fixture ;
  @Before 
  public void setup(){
    fixture = new Compiler().new CompileState();
  }
  
  
  @Test
  public void testEmit() {
    fail("Not yet implemented");
  }

  @Test
  public void testGetCh() {
    assertEquals('\uffff', fixture.getCh());
  }

  @Test
  public void testGetChx() {
    assertEquals(-1, fixture.getChx());
  }

  @Test
  public void testGeteSign() {
    assertEquals(' ', fixture.geteSign());
  }

  @Test
  public void testGetExpression() {
    assertNull(fixture.getExpression());
  }

  @Test
  public void testGetIdent() {
    assertNull(fixture.getIdent());
  }

  @Test
  public void testGetLiteral() {
    assertNull(fixture.getLiteral());
  }

  @Test
  public void testGetLiteralType() {
    assertNull(fixture.getLiteralType());
  }

  @Test
  public void testGetPlan() {
    assertNull(fixture.getPlan());
  }

  @Test
  public void testGetPriorities() {
    assertTrue(fixture.getPriorities() instanceof Stack);
  }

  @Test
  public void testGetRadix() {
    assertEquals(10, fixture.getRadix());
  }

  @Test
  public void testGetTkStart() {
    assertEquals(-1, fixture.getTkStart());
  }

  @Test
  public void testGetToken() {
    assertNull(fixture.getToken());
  }

  @Test
  public void testIsExpectE() {
    assertFalse(fixture.isExpectE());
  }

  @Test
  public void testIsExpectESign() {
    assertFalse(fixture.isExpectESign());
  }

  @Test
  public void testIsExpectPow10() {
    assertFalse(fixture.isExpectPow10());

  }

  @Test
  public void testIsExpectRadix() {
    assertFalse(fixture.isExpectRadix());

  }

  @Test
  public void testIsInFraction() {
    assertFalse(fixture.isInFraction());

  }

  @Test
  public void testIsInIdent() {
    assertFalse(fixture.isInIdent());

  }

  @Test
  public void testIsInLineComment() {
    assertFalse(fixture.isInLineComment());

  }

  @Test
  public void testIsInLongComment() {
    assertFalse(fixture.isInLongComment());
  }

  @Test
  public void testIsInMantissa() {
    assertFalse(fixture.isInMantissa());

  }

  @Test
  public void testIsInNumber() {
    assertFalse(fixture.isInNumber());
  }

  @Test
  public void testIsInPow10() {
    assertFalse(fixture.isInPow10());
  }

  @Test
  public void testIsInSymbol() {
    assertFalse(fixture.isInSymbol());
  }

  @Test
  public void testIsInToken() {
    assertFalse(fixture.isInToken());
  }

  @Test
  public void testIsRadixBefore() {
    assertFalse(fixture.isRadixBefore());
  }

  @Test
  public void testIsSlashBefore() {
    assertFalse(fixture.isSlashBefore());

  }

  @Test
  public void testIsStarBefore() {
    assertFalse(fixture.isStarBefore());
  }

  @Test
  public void testNumberTypeForSuffix() {
    fail("Not yet implemented");
  }

  @Test
  public void testOnIdent() {
    fail("Not yet implemented");
  }

  @Test
  public void testOnInLineComment() {
    fail("Not yet implemented");
  }

  @Test
  public void testOnLongComment() {
    fail("Not yet implemented");
  }

  @Test
  public void testOnNumber() {
    fail("Not yet implemented");
  }

  @Test
  public void testOnSlashBefore() {
    fail("Not yet implemented");
  }

  @Test
  public void testOnSymbol() {
    for (Object[] sym:Compiler.SYMBOL_LIST){
      String t = (String) sym[0];
      TokenType tt = (TokenType) sym[1];
      
      CompileState cs = new Compiler().new CompileState();
      cs.setExpression(t);
      for(cs.setChx(0);cs.getChx()<=t.length();cs.setChx(cs.getChx()+1)){
        try{
          cs.setCh(t.charAt(cs.getChx()));
        } catch (StringIndexOutOfBoundsException e){
          cs.setCh(SimpleExpressionLanguage.CHAR_NIL);
        }
        try {
          cs.onSymbol();
        } catch (ExpressionFailedException e) {
          fail("exception: "+ e.toString());
        }
        if (cs.isParse()){
          break;
        }
      }
      assertEquals(t, cs.getToken());
      assertEquals(0, cs.getTkStart());
      assertEquals(tt, cs.getSymbolTrial().getToken());
      cs.setParse(false);
    }
  }

  @Test
  public void testOnToken() {
    fail("Not yet implemented");
  }

  @Test
  public void testOnWhiteSpace() {
    fail("Not yet implemented");
  }

  @Test
  public void testParse() {
    fail("Not yet implemented");
  }

  @Test
  public void testSetCh() {
    fixture.setCh('X');
    assertEquals('X', fixture.getCh());
  }

  @Test
  public void testSetChx() {
    fixture.setChx(32);
    assertEquals(32, fixture.getChx());
  }

  @Test
  public void testSeteSign() {
    fixture.seteSign('+');
    assertEquals('+', fixture.geteSign());
  }

  @Test
  public void testSetExpectE() {
    fixture.setExpectE(true);
    assertTrue(fixture.isExpectE());
  }

  @Test
  public void testSetExpectESign() {
    fixture.setExpectESign(true);
    assertTrue(fixture.isExpectESign());
  }

  @Test
  public void testSetExpectPow10() {
    fixture.setExpectPow10(true);
    assertTrue(fixture.isExpectPow10());
  }

  @Test
  public void testSetExpectRadix() {
    fixture.setExpectRadix(true);
    assertTrue(fixture.isExpectRadix());
  }

  @Test
  public void testSetExpression() {
    fixture.setExpression("pi^e");
    assertEquals("pi^e",fixture.getExpression());
  }

  @Test
  public void testSetIdent() {
    fixture.setIdent("id");
    assertEquals("id", fixture.getIdent());
  }

  @Test
  public void testSetInFraction() {
    fixture.setInFraction(true);
    assertTrue(fixture.isInFraction());
  }

  @Test
  public void testSetInIdent() {
    fixture.setInIdent(true);
    assertTrue(fixture.isInIdent());
  }

  @Test
  public void testSetInLineComment() {
    fixture.setInLineComment(true);
    assertTrue(fixture.isInLineComment());
  }

  @Test
  public void testSetInLongComment() {
    fixture.setInLongComment(true);
    assertTrue(fixture.isInLongComment());
  }

  @Test
  public void testSetInMantissa() {
    fixture.setInMantissa(true);
    assertTrue(fixture.isInMantissa());
  }

  @Test
  public void testSetInNumber() {
    fixture.setInNumber(true);
    assertTrue(fixture.isInNumber());
  }

  @Test
  public void testSetInPow10() {
    fixture.setInPow10(true);
    assertTrue(fixture.isInPow10());
  }

  @Test
  public void testSetInSymbol() {
    fixture.setInSymbol(true);
    assertTrue(fixture.isInSymbol());
  }

  @Test
  public void testSetInToken() {
    fixture.setInToken(true);
    assertTrue(fixture.isInToken());
  }

  @Test
  public void testSetLiteral() {
    Object obj = new Object();
    fixture.setLiteral(obj);
    assertEquals(obj, fixture.getLiteral());
  }

  @Test
  public void testSetLiteralType() {
    fixture.setLiteralType(String.class);
    assertEquals(String.class, fixture.getLiteralType());
  }

  @Test
  public void testSetPlan() {
    final ArrayList<AbstractFetch> plan0 = new ArrayList<>();
    fixture.setPlan(plan0);
    assertEquals(plan0, fixture.getPlan());
  }

  @Test
  public void testSetPriorities() {
    final Stack<Object[]> priorities0 = new Stack<>();
    fixture.setPriorities(priorities0);
    assertEquals(priorities0, fixture.getPriorities());
  }

  @Test
  public void testSetRadix() {
    fixture.setRadix(20);
    assertEquals(20,fixture.getRadix());
  }

  @Test
  public void testSetRadixBefore() {
    fixture.setRadixBefore(true);
    assertTrue(fixture.isRadixBefore());
  }

  @Test
  public void testSetSlashBefore() {
    fixture.setSlashBefore(true);
    assertTrue(fixture.isSlashBefore());
  }

  @Test
  public void testSetStarBefore() {
    fixture.setStarBefore(true);
    assertTrue(fixture.isStarBefore());
  }

  @Test
  public void testSetTkStart() {
    fixture.setTkStart(45);
    assertEquals(45, fixture.getTkStart());
  }

  @Test
  public void testSetToken() {
    fixture.setToken("token");
    assertEquals("token", fixture.getToken());
  }

}
