package net.witerat.cafenatedsql.spi.driver.tags;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import net.witerat.cafenatedsql.api.Cafenated;
import net.witerat.cafenatedsql.api.driver.template.TemplateEngine;
import net.witerat.cafenatedsql.api.driver.template.TemplateEngineModel;
import net.witerat.cafenatedsql.api.mock.MockModelFactory;

public class UseContentTest extends UseContent {

  private UseContent useContent;
  private AbstractMacroLocator mockMacros;

  @Before
  public void setUp(){
    useContent = new UseContent();
    mockMacros = new AbstractMacroLocator() {
      MacroTag mockMacro = new MacroTag() {
        
        @Override
        public AbstractContent getParent() {
          return null;
        }
        
        @Override
        public Collection<AbstractContent> getContent() {
          @SuppressWarnings("serial")
          ArrayList<AbstractContent> content = new ArrayList<AbstractContent>() {
            {
              add(new AbstractContent(){
                @Override
                public String getText(TemplateEngineModel model) {
                  return "value=$value";
                }
              });
            }
          };
          return content;
        }
      };
      @Override
      public MacroTag getMacro(String name) {
        return mockMacro;
      }
    };
    useContent.setMacros(mockMacros);
  }
  
  @Test
  public void testGetText() {
    @SuppressWarnings("serial")
    TemplateEngineModel tem = new MockModelFactory().newInstance(new Properties() {
      {
        put("value", "foo");
        put(Cafenated.TEMPLATE_ENGINE, new TemplateEngine() {
          TemplateEngineModel model;
          StringBuilder stringBuilder = new StringBuilder();
          @Override
          public void setModel(TemplateEngineModel tem) {
            model = tem;
            
          }
          
          @Override
          public String produce(Object id, TemplateEngineModel tem) {
            TemplateEngineModel tem0 = null == tem ? model : tem;
            StringBuilder product = new StringBuilder();
            boolean slashbefore = false;
            boolean dollarbefore = false;
            boolean incurl = false;
            boolean inname = false;
            
            int exstart = -1;
            int txstart = -1;
            int exend = -1;
            int txend = -1;
            for (int i = 0; i <= stringBuilder.length(); i++){
              char c = i < stringBuilder.length() ? stringBuilder.charAt(i) : (char)0xffff;
              if(incurl) {
                if (exstart == -1){
                  exstart=i;
                }
                if (c == '}' || c == 0xffff) {
                  incurl = false;
                  exend = i;
                }
              }
              if (dollarbefore){
                if (c == '{'){
                  incurl = true;
                } else {
                  inname = true;
                  exstart = i;
                }
                dollarbefore=false;
              } else if (c == '$') {
                dollarbefore = true;
                txend = i;
              }
              if((inname == true && dollarbefore)
                  || inname == false && incurl == true){
                inname = true;
                exstart = i;
              }
              if(inname ) {
                if(c == 0xffff) exend=i;
                if( exend != -1) {
                  String expression = stringBuilder.substring(exstart, exend);
                  Object value = tem0.getByExpression(expression);
                  product.append(value.toString());
                  exend = -1;
                  exstart = -1;
                }
              }else {
                if(txstart == -1){
                  txstart = i;
                }
                if (c == '\\') {
                  if(txend == -1) txend = i;
                  slashbefore = !slashbefore;
                } else {
                  slashbefore = false;
                  if( c== -1){
                    if(txstart != -1) txend = i;
                  }
                }
                if(txend != -1){
                  product.append(stringBuilder.substring(txstart, txend));
                  txstart = -1;
                  txend = -1;
                }
                if (slashbefore){
                  if (c == -1) {
                    product.append('\\');
                  } else {
                    int e = "fnrt".indexOf(c);
                    if (e == -1) {
                      product.append('\\').append(c);
                    } else {
                      product.append("\f\n\r\t".charAt(e));
                    }
                  }
                }else{
                }
              }
            }
            return product.toString();
          }
          
          @Override
          public void injectFixture(String name, String content) {
            // no-op
            
          }
          
          @Override
          public void define(Object id) {
            // no-op
            
          }
          
          @Override
          public void appendText(String text) {
            stringBuilder.append(text);
            
          }
          
          @Override
          public void appendFixture(String name) {
            // no-op
            
          }
        
          
        });
      }
    });
    assertEquals("value=foo",useContent.getText(tem));
  }

  @Test
  public void testGetMacros() {
    assertSame(mockMacros, useContent.getMacros());
  }

  @Test
  public void testGetContent() {
    Collection<AbstractContent> content = useContent.getContent();
    for(AbstractContent ac : content){
     assertEquals("value=$value", ac.getText(null));
    }
  }

  @Test
  public void testSetMacro() {
    useContent.setMacro("mockMacro");
    assertEquals("mockMacro", useContent.getMacro());
  }

  @Test
  public void testSetMacros() {
    assertSame(mockMacros,useContent.getMacros());
  }

  @Test
  public void testGetParent() {
    assertNull(useContent.getParent());
  }

}
