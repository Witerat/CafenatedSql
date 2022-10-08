package net.witerat.cafenatedsql.api;

import static org.junit.Assert.*;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;

public class EntityManagerTest {

  @Test
  public void testRefactor() {
    // no-op - target method is abstract.
    Logger.getLogger(getClass().getName()).log(Level.INFO, "target method is abstract");
    
  }

  @Test 
  public void testCalculus(){
    assertEquals("ALTER",EntityManager.Calculus.ALTER.name());
  }
}
