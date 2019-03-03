package net.witerat.cafenatedsql.spi.driver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.junit.Test;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import net.witerat.cafenatedsql.spi.driver.tags.ConnectionTag;
import net.witerat.cafenatedsql.spi.driver.tags.DefTag;
import net.witerat.cafenatedsql.spi.driver.tags.DialectTag;
import net.witerat.cafenatedsql.spi.driver.tags.DriverTag;
import net.witerat.cafenatedsql.spi.driver.tags.GrammarTag;
import net.witerat.cafenatedsql.spi.driver.tags.NounTag;
import net.witerat.cafenatedsql.spi.driver.tags.ObjectFactory;
import net.witerat.cafenatedsql.spi.driver.tags.ParamTag;
import net.witerat.cafenatedsql.spi.driver.tags.UrlTag;
import net.witerat.cafenatedsql.spi.driver.tags.UseDialectTag;
import net.witerat.cafenatedsql.spi.driver.tags.UseTag;
import net.witerat.cafenatedsql.spi.driver.tags.VerbTag;;

public class DriverLoaderTest {

	/**
	 * 
	 */
	static {
		ResourceProtocol.init();
	}

	/**
	 * @throws MalformedURLException
	 */
	public void testDriver1resource() throws MalformedURLException {
		URL rsc=new URL("classpath:driver1.csql.xml");
		InputStream is;
		try {
			is = rsc.openStream();
			for(int b=is.read();b!=-1;b=is.read()){
				System.out.write(b);
			}
			is.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		System.out.println("<EOF>");
	}

	@Test
	public void testLoad() throws JAXBException, MalformedURLException{
		testDriver1resource();
		try {
			JAXBContext jc = JAXBContext.newInstance(DriverTag.class,
			    ObjectFactory.class);
			
			Unmarshaller u = jc.createUnmarshaller();
			
			// Create catalog resolver and set a catalog list.
//			CatalogManager cm = new CatalogManager();
//			cm.debug.setDebug(Integer.MAX_VALUE);
//			CatalogResolver resolver = new CatalogResolver(cm);
//			
//
//			XMLReader xr = null;
//		        try {
//		          xr = XMLReaderFactory.createXMLReader();
//		        } catch (SAXException e) {
//		          // TODO Auto-generated catch block
//		          e.printStackTrace();
//		          }
//			xr.setEntityResolver(resolver);
//			xr.setErrorHandler(new ErrorHandler() {
//                          
//                          @Override
//                          public void warning(SAXParseException exception) throws SAXException {
//                            Logger.getAnonymousLogger().log(Level.INFO, "warning", exception.toString());
//                            
//                          }
//                          
//                          @Override
//                          public void fatalError(SAXParseException exception) throws SAXException {
//                            Logger.getAnonymousLogger().log(Level.INFO, "fatal", exception.toString());
//                            
//                          }
//                          
//                          @Override
//                          public void error(SAXParseException exception) throws SAXException {
//                            Logger.getAnonymousLogger().log(Level.INFO, "error", exception.toString());
//                            
//                          }
//                        });
			Logger.getAnonymousLogger().log(Level.INFO, "CWD: "+new File(".").getAbsolutePath());
			SchemaFactory sf=SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
                        try {
                          final URL schemaURL = new URL("classpath:schemas/cafenated.xsd");
                          final Schema schema = sf.newSchema(schemaURL);
                          assertNotNull("schema not found", schema);
                          u.setSchema(schema);
                        } catch (SAXException e) {
                           fail("Exception: "+e.toString());
                        }
			assertNotNull("No schema (cafenated.xsd) found", u.getSchema());
			DriverTag  driver = (DriverTag) u.unmarshal(new URL("classpath:driver1.csql.xml"));
			assertNotNull(driver);
			assertEquals("velocity", driver.getTemplateEngine());
			
			assertNotNull(driver.getMeta());
			assertEquals("wrong name", "H2-Database",driver.getMeta().getName());
			assertEquals("wrong protocol", "jdbc:h2:", driver.getMeta().getProtocol());
			assertEquals("wrong description", "H2 pure java DB", driver.getMeta().getDescription());
			assertEquals("wrong class name", "org.h2.Driver", driver.getMeta().getClassName());

			assertNotNull("no defs", driver.getDefs());
			assertNotNull("no params def", driver.getDefs().get("params"));
			DefTag dparams = driver.getDefs().get("params");
			assertEquals("params", dparams.getName());
			assertEquals("\n\t\t#foreach($key in ${params.keys})#{set($value ="+
						"\n\t\t$params[$key])};${key}=$!{value}#{end}"+
						"\n\t", 
					 driver.getDefs().get("params").getRawContent().get(0));
			
			DialectTag diaTag=driver.getDialectByName("h2");
			assertNotNull(diaTag);
			assertEquals("h2", diaTag.getId());
			
			assertNotNull(diaTag.getGrammars());
			assertFalse(diaTag.getGrammars().isEmpty());
			
			GrammarTag [] aGrammars=diaTag.getGrammars().toArray(new GrammarTag[diaTag.getGrammars().size()]);
			assertNotNull(aGrammars[0]);
			GrammarTag gram0=aGrammars[0];
			assertEquals("ddl-common", gram0.getId());
			assertEquals(3, gram0.getNouns().size());
			NounTag[] g0Nouns = gram0.getNouns().toArray(new NounTag[3]);
			NounTag g0n0 = (g0Nouns)[0];
			assertNotNull(g0n0);
			assertEquals("Schema", g0n0.getName());
			
			assertEquals(1, g0n0.getParameters().size());
			ParamTag[] g0n0p = g0n0.getParameters().toArray(new ParamTag[1]);
			assertNotNull(g0n0p[0]);
			assertEquals("schema", g0n0p[0].getName());
			assertEquals("string", g0n0p[0].getType());
			assertEquals(2, g0n0.getVerbs().size());
			VerbTag[] g0n0v = g0n0.getVerbs().toArray(new VerbTag[2]);
			VerbTag g0n0v0 = g0n0v[0];
			assertNotNull(g0n0v0);
			assertEquals("create", g0n0v0.getName());
			assertEquals(1, g0n0v0.getParameters().size());
			ParamTag[] g0n0v0p=g0n0v0.getParameters().toArray(new ParamTag[1]);
			assertNotNull(g0n0v0p[0]);
			assertEquals("whenMissing", g0n0v0p[0].getName());
			VerbTag g0n0v1 = g0n0v[1];
			assertNotNull(g0n0v1);
			assertEquals("drop", g0n0v1.getName());
			assertEquals(1, g0n0v1.getParameters().size());
			ParamTag[] g0n0v1p=g0n0v1.getParameters().toArray(new ParamTag[1]);
			assertNotNull(g0n0v1p[0]);
			assertEquals("whenPresent", g0n0v1p[0].getName());

			NounTag g0n1 = (g0Nouns)[1];
			assertNotNull(g0n1);
			assertEquals("sequence", g0n1.getName());
			assertEquals(1, g0n1.getParameters().size());
			ParamTag[] g0n1p = g0n1.getParameters().toArray(new ParamTag[1]);
			assertNotNull(g0n1p[0]);
			assertEquals("sequenceName", g0n1p[0].getName());
			assertEquals(1, g0n1.getVerbs().size());
			VerbTag[] g0n1v = g0n1.getVerbs().toArray(new VerbTag[1]);
			VerbTag g0n1v0 = g0n1v[0];
			assertNotNull(g0n1v0);
			assertEquals("drop", g0n1v0.getName());

			NounTag g0n2 = (g0Nouns)[2];
			assertNotNull(g0n2);
			assertEquals("user", g0n2.getName());
			
			assertEquals(1, g0n2.getParameters().size());
			ParamTag[] g0n2p = g0n2.getParameters().toArray(new ParamTag[1]);
			assertNotNull(g0n2p[0]);
			assertEquals("userName", g0n2p[0].getName());
			assertEquals("string", g0n2p[0].getType());
			assertEquals(2, g0n2.getVerbs().size());
			VerbTag[] g0n2v = g0n2.getVerbs().toArray(new VerbTag[2]);
			VerbTag g0n2v0 = g0n2v[0];
			assertNotNull(g0n2v0);
			assertEquals("create", g0n2v0.getName());
			assertEquals(4, g0n2v0.getParameters().size());
			ParamTag[] g0n2v0p=g0n2v0.getParameters().toArray(new ParamTag[4]);
			assertNotNull(g0n2v0p[0]);
			assertEquals("whenMissing", g0n2v0p[0].getName());
			assertEquals("boolean",     g0n2v0p[0].getType());
			assertNotNull(g0n2v0p[1]);
			assertEquals("password",    g0n2v0p[1].getName());
			assertEquals("string",      g0n2v0p[1].getType());
			assertNotNull(g0n2v0p[2]);
			assertEquals("salt",        g0n2v0p[2].getName());
			assertEquals("string",      g0n2v0p[2].getType());
			assertNotNull(g0n2v0p[3]);
			assertEquals("hash",        g0n2v0p[3].getName());
			assertEquals("string",      g0n2v0p[3].getType());
			VerbTag g0n2v1 = g0n2v[1];
			assertNotNull(g0n2v1);
			assertEquals("drop", g0n2v1.getName());
			ParamTag[] g0n2v1p=g0n2v1.getParameters().toArray(new ParamTag[1]);
			assertNotNull(g0n2v1p);
			assertEquals("whenPresent", g0n2v1p[0].getName());
			assertEquals("boolean",     g0n2v1p[0].getType());

			assertEquals(7, driver.getConnections().size());
			ConnectionTag[] cons= driver.getConnections().toArray(new ConnectionTag[6]);
			ConnectionTag c0=cons[0];
			assertNotNull(c0);
			assertEquals("memory", c0.getMethod());
			UseDialectTag c0d = c0.getUseDialect();
			assertNotNull("no use-dialect for connection: " + c0.getMethod(),c0d);
			assertEquals("$mode", c0d.getName());
			assertEquals("h2", c0d.getDefaultName());
			UrlTag c0u=c0.getUrl();
			assertEquals(2, c0u.getRawContent().size());
			Object[] c0ur=c0u.getRawContent().toArray(new Object[2]);
			assertTrue(c0ur[0]instanceof String);
			assertEquals("\n\t\t\tjdbc:h2:mem:\n\t\t\t", (String)c0ur[0]);
			assertTrue(c0ur[1]instanceof UseTag);
			UseTag c0uru=(UseTag)c0ur[1];
			assertEquals("params", c0uru.getMacro());
			
		} catch (JAXBException e) {
			throw e;
		} catch (MalformedURLException e) {
			throw e;
		}
	}

	@Test
	public void testParse() {
		Exception exception=null;
		XMLReader xr=null;
		try {
			xr = XMLReaderFactory.createXMLReader();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		try {
			xr.parse("resource/driver1.csql.xml");
		} catch (IOException e) {
			exception=e;
		} catch (SAXException e) {
			exception=e;
					
		}
		assertNull(exception);
	}
	 
}
