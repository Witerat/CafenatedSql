package net.witerat.cafenatedsql.spi.driver.template.simple;

import static org.junit.Assert.*;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Stack;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

import net.witerat.cafenatedsql.api.driver.template.ExpressionFailedException;
import net.witerat.cafenatedsql.spi.driver.template.simple.Compiler.CompileState;
import net.witerat.cafenatedsql.spi.driver.template.simple.Processor.AbstractFetch;
import net.witerat.cafenatedsql.spi.driver.template.simple.Processor.LiteralFetch;

public class CompileStateTest  {
  CompileState fixture ;
  @Before 
  public void setup(){
    fixture = new Compiler().new CompileState();
  }
  
  
  @Test
  public void testEmit() {
    final Object[] ZERO={Ops.LITERAL,Integer.valueOf(0), Integer.class};
    final AbstractFetch[] expected = {new LiteralFetch(Integer.valueOf(0), Integer.class)};
    fixture.setPlan(new ArrayList<AbstractFetch>());
    fixture.emit(ZERO);
    Object[] result=fixture.getPlan().toArray();
    int hc=result[0].hashCode();
    Logger.getAnonymousLogger().fine("hc: "+hc);
    assertArrayEquals(expected, result);
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
    final Object[][] expect={
        new Object[]{'s',Short.class}, 
        new Object[]{'l',Long.class},
        new Object[]{'i',Integer.class},
        new Object[]{'d', Double.class},
        new Object[]{'f',Float.class},
        new Object[]{'b', Byte.class}
        };
    CompileState cs = new Compiler().new CompileState();
    for (Object[] e:expect){
      char es = (char) e[0];
      Class<?> ec = (Class<?>) e[1];
      Class<?> ac = cs.numberTypeForSuffix(es);
      assertEquals("expected "+ac.getSimpleName()+"for "+es+": "+ac.getName(),ec, ac);
    }
  }

  @Test
  public void testOnIdent() {
    final String[] ta = new String[]{"flubbernuckle", "flubbernuckle.", "flubbernuckle ","int"};
    final String t0=ta[0];
    for (String t:ta){
      CompileState cs = new Compiler().new CompileState();
      cs.setExpression(t);
      for(cs.setChx(0); cs.getChx() <= t.length(); cs.setChx(cs.getChx()+1)){
        try{
          cs.setCh(t.charAt(cs.getChx()));
        } catch (StringIndexOutOfBoundsException e){
          cs.setCh(SimpleExpressionLanguage.CHAR_NIL);
        }
        cs.onIdent();
        if (cs.isParse()){
          break;
        }
      }
      assertTrue(MessageFormat.format("parse not set(t=''{0}'')",t), cs.isParse());
      if(t.startsWith("flub")) {
        assertNull(cs.getToken());
        assertEquals(t0, cs.getIdent());
        assertEquals(0, cs.getTkStart());
        assertEquals(TokenType.ID, cs.getSymbolTrial().getToken());
        cs.setParse(false);
      }
    }
  }

  @Test
  public void testOnInLineComment() {
    String t = "blah **\n";
    CompileState cs = new Compiler().new CompileState();
    cs.setExpression(t);
    int cycle = -1;
    cs.setInLineComment(true);
    for(cs.setChx(0); cs.getChx() <= t.length(); cs.setChx(cs.getChx()+1)){
      ++cycle;
      try{
        cs.setCh(t.charAt(cs.getChx()));
      } catch (StringIndexOutOfBoundsException e){
        cs.setCh(SimpleExpressionLanguage.CHAR_NIL);
      }

      if(cs.onInLineComment()) {
        assertTrue("cycle too high: "+cycle, cycle < 8);
        continue;
      }else {
        assertTrue("cycle to low: "+cycle, cycle >= 8);
        assertFalse(cs.isInLongComment());
        break;
      }
    }
  }

  @Test
  public void testOnKeyword() {
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
  /** test for straight forward end of comment. "/
  @Test
  public void testOnLongComment() {
    String t = "blah *\/\n";";
  */
  @Test
  public void testOnLongComment() {
      String t = "blah */\n";
    CompileState cs = new Compiler().new CompileState();
    cs.setExpression(t);
    int cycle = -1;
    cs.setInLongComment(true);
    for(cs.setChx(0); cs.getChx() <= t.length(); cs.setChx(cs.getChx()+1)){
      ++cycle;
      try{
        cs.setCh(t.charAt(cs.getChx()));
      } catch (StringIndexOutOfBoundsException e){
        cs.setCh(SimpleExpressionLanguage.CHAR_NIL);
      }

      try {
        if(!cs.onLongComment()) {
          assertTrue("cycle too high: "+cycle, cycle < 6);
          continue;
        }else {
          assertTrue("cycle to low: "+cycle, cycle >= 6);
          assertFalse(cs.isInLongComment());
          break;
        }
      } catch (ExpressionFailedException e) {
        fail(("cycle: "+cycle)+e.getMessage());
      };
    }
  }
  /** test for asterisk before comment end. **/
  @Test
  public void testOnLongComment2() {
    String t = "blah **/\n";
    CompileState cs = new Compiler().new CompileState();
    cs.setExpression(t);
    int cycle = -1;
    cs.setInLongComment(true);
    for(cs.setChx(0); cs.getChx() <= t.length(); cs.setChx(cs.getChx()+1)){
      ++cycle;
      try{
        cs.setCh(t.charAt(cs.getChx()));
      } catch (StringIndexOutOfBoundsException e){
        cs.setCh(SimpleExpressionLanguage.CHAR_NIL);
      }

      try {
        if(!cs.onLongComment()) {
          assertTrue("cycle too high: "+cycle, cycle < 7);
          continue;
        }else {
          assertTrue("cycle to low: "+cycle, cycle >= 7);
          assertFalse(cs.isInLongComment());
          break;
        }
      } catch (ExpressionFailedException e) {
        fail(("cycle: "+cycle)+e.getMessage());
      };
    }
  }
  /** Test for unterminated comment 
  @Test 
  public void testOnLongComment3() {
    String t = "blah **\n";
  */
  @Test
  public void testOnLongComment3() {
    String t = "blah **\n";
    CompileState cs = new Compiler().new CompileState();
    cs.setExpression(t);
    int cycle = -1;
    cs.setInLongComment(true);
    for(cs.setChx(0); cs.getChx() <= t.length(); cs.setChx(cs.getChx()+1)){
      ++cycle;
      try{
        cs.setCh(t.charAt(cs.getChx()));
      } catch (StringIndexOutOfBoundsException e){
        cs.setCh(SimpleExpressionLanguage.CHAR_NIL);
      }

      try {
        if(!cs.onLongComment()) {
          assertTrue("cycle too high: "+cycle, cycle < 8);
          continue;
        }else {
          assertTrue("cycle to low: "+cycle, cycle >= 8);
          assertFalse(cs.isInLongComment());
          break;
        }
      } catch (ExpressionFailedException e) {
        if(cycle!=8){
          fail(("cycle: "+cycle)+e.getMessage());
        }
      };
    }
 }

  @Test
  public void testOnNumber() {
    fixture.setExpression("0\r");
    fixture.setChx(0);
    Object fault = null;
    try {
      fixture.onNumber();
    } catch (ExpressionFailedException e) {
      fault = e;
    }
    assertNull(fault);
    assertTrue(fixture.isInToken());
    assertFalse(fixture.isInNumber());
    assertFalse(fixture.isInSymbol());
    
  }

  @Test
  public void testOnSlashBefore() {
    String t = "/";
    CompileState cs = new Compiler().new CompileState();
    cs.setExpression(t);
    int cycle = -1;
    cs.setSlashBefore(false);
    for(cs.setChx(0); cs.getChx() < t.length(); cs.setChx(cs.getChx()+1)){
      ++cycle;
      try{
        cs.setCh(t.charAt(cs.getChx()));
      } catch (StringIndexOutOfBoundsException e){
        cs.setCh(SimpleExpressionLanguage.CHAR_NIL);
      }

      final boolean r = cs.onSlashBefore();
      switch (cycle) {
      case 0:
        assertTrue(cs.isSlashBefore());
        assertFalse(r);
        break;
      }
    }
  }
  @Test
  public void testOnSlashBeforeSlash() {
    String t = "/";
    CompileState cs = new Compiler().new CompileState();
    cs.setExpression(t);
    int cycle = -1;
    cs.setSlashBefore(true);
    for(cs.setChx(0); cs.getChx() < t.length(); cs.setChx(cs.getChx()+1)){
      ++cycle;
      try{
        cs.setCh(t.charAt(cs.getChx()));
      } catch (StringIndexOutOfBoundsException e){
        cs.setCh(SimpleExpressionLanguage.CHAR_NIL);
      }

      final boolean r = cs.onSlashBefore();
      switch (cycle) {
      case 0:
        assertTrue(cs.isInLineComment());
        assertTrue(r);
        break;
      }
    }
  }

  @Test
  public void testOnSlashBeforeOther() {
    String t = "?";
    CompileState cs = new Compiler().new CompileState();
    cs.setExpression(t);
    int cycle = -1;
    cs.setSlashBefore(true);
    for(cs.setChx(0); cs.getChx() < t.length(); cs.setChx(cs.getChx()+1)){
      ++cycle;
      try{
        cs.setCh(t.charAt(cs.getChx()));
      } catch (StringIndexOutOfBoundsException e){
        cs.setCh(SimpleExpressionLanguage.CHAR_NIL);
      }

      final boolean r = cs.onSlashBefore();
      switch (cycle) {
      case 0:
        assertFalse(cs.isInLineComment());
        assertFalse(cs.isInLongComment());
        assertFalse(r);
        break;
      }
    }
  }

  @Test
  public void testOnSlashBeforeStar() {
    String t = "*";
    CompileState cs = new Compiler().new CompileState();
    cs.setExpression(t);
    int cycle = -1;
    cs.setSlashBefore(true);
    for(cs.setChx(0); cs.getChx() < t.length(); cs.setChx(cs.getChx()+1)){
      ++cycle;
      try{
        cs.setCh(t.charAt(cs.getChx()));
      } catch (StringIndexOutOfBoundsException e){
        cs.setCh(SimpleExpressionLanguage.CHAR_NIL);
      }

      final boolean r = cs.onSlashBefore();
      switch (cycle) {
      case 0:
        assertTrue(cs.isInLongComment());
        assertTrue(r);
        break;
      }
    }
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
    fixture.setExpression("token\r");
    fixture.setChx(0);
    Object fault = null;
    try {
      fixture.onToken();
    } catch (ExpressionFailedException e) {
      fault = e;
    }
    assertNull(fault);
    assertTrue(fixture.isInToken());
    assertFalse(fixture.isInNumber());
    assertFalse(fixture.isInSymbol());
    
    //fail("Not yet implemented");
  }

  @Test
  public void testOnWhiteSpace() {
    String t = ". \t\n";
    CompileState cs = new Compiler().new CompileState();
    cs.setExpression(t);
    int cycle = -1;
    cs.setInLineComment(true);
    for(cs.setChx(0); cs.getChx() < t.length(); cs.setChx(cs.getChx()+1)){
      ++cycle;
      try{
        cs.setCh(t.charAt(cs.getChx()));
      } catch (StringIndexOutOfBoundsException e){
        cs.setCh(SimpleExpressionLanguage.CHAR_NIL);
      }

      final boolean r = cs.onWhiteSpace();
      switch (cycle) {
      case 0:
        assertFalse(r);
        break;
      case 1:
        assertTrue(r);
        break;
      case 2:
        assertTrue(r);
        break;
      case 3:
        assertFalse(cs.isInLineComment());
        assertTrue(r);
        break;
      }
    }
  }
  

  @Test
  public void testParse() {
    fixture.setExpression("token\r");
    fixture.setChx(0);
    fixture.parse();
    assertEquals("token", fixture.getToken());
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
