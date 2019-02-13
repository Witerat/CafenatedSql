package net.witerat.cafenatedsql.spi;

import static org.junit.Assert.*;

import org.junit.Test;

import net.witerat.cafenatedsql.api.Provider;
import net.witerat.cafenatedsql.api.mock.MockProvider;

public class RootProviderRegistrarTest {

	@Test
	public void testGetProvider() {
		RootProviderRegistrar rpr = new RootProviderRegistrar();
		SimpleProviderRegistrar pr=new SimpleProviderRegistrar();
		rpr.addRegistrar(pr);		
		MockProvider anonProvided=new MockProvider();
		pr.registerProvider(anonProvided, "baz", "(c)1967", "W E Coyote", "WEC Ingenuitec Performance Obfuscator", "RR Bird");
		pr.registerVendor("RR Bird", "Road Runner Bird", "Road Runner Bird LLC Incorporated");
		SimpleProviderRegistrar spr=new SimpleProviderRegistrar();
		MockProvider mockProvider = new MockProvider();
		spr.registerProvider(mockProvider, "bar", "(C) 1966", "Acme data munter", "That's all Folk's", "Acme Inc.");
		spr.registerVendor("Acme Inc.", "Acme", "Acme incorporated LLC");
		rpr.addRegistrar("foo", spr);
		Provider mp=rpr.getProvider("foo/bar");
		assertSame(mockProvider, mp);
		Provider amp=rpr.getProvider("baz");
		assertSame(anonProvided, amp);
	}

}
