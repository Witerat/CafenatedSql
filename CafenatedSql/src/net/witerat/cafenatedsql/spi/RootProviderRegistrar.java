package net.witerat.cafenatedsql.spi;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import net.witerat.cafenatedsql.api.Provider;
import net.witerat.cafenatedsql.api.ProviderRegistrar;

/**
 * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
 *
 */
public class RootProviderRegistrar extends SimpleProviderRegistrar {
	private Map<String, ProviderRegistrar> registrars = new LinkedHashMap();
	private Set<ProviderRegistrar> anons = new LinkedHashSet<>();

	void addRegistrar(ProviderRegistrar pr) {
		registrars.put(getDefaultName(pr), pr);
		anons.add(pr);
	}

	public void addRegistrar(String name, ProviderRegistrar pr) {
		int d = name.indexOf("/");
		if (d == -1) {
			registrars.put(name, pr);
		} else {
			((RootProviderRegistrar) registrars.get(name.substring(0, d))).addRegistrar(name.substring(d + 1), pr);
		}
	}

	/**
	 * @param pr
	 * @return a name used by anonymous registrars.
	 */
	public static String getDefaultName(ProviderRegistrar pr) {
		return "{@" + System.identityHashCode(pr) + "}";
	}

	/**
	 * remove the specified registrar.
	 * @param pr
	 */
	void removeRegistrar(ProviderRegistrar pr) {
		while(registrars.values().remove(pr));
		while(anons.remove(pr));
	}

	/**
	 * Removes the named registrar
	 * @param name the name of the registrar to be removed.
	 */
	public void removeRegistrar(String name) {
		registrars.remove(name);
	}

	@Override
	public String getDescription(String pathName) {
		String[] path = pathName.split("/");
		RootProviderRegistrar rpr = this;
		ProviderRegistrar pr = this;
		for (int i = 0; i < path.length - 1; i++) {
			rpr = (RootProviderRegistrar) pr;
			pr = rpr.registrars.get(path[i]);
		}
		if (pr == this)
			return super.getDescription(path[path.length - 1]);
		return pr.getDescription(path[path.length - 1]);
	}
	@Override
	public Provider getProvider(String name) {
		if(name.indexOf('/')==-1){
			Provider p = super.getProvider(name);
			if(p==null){
				for(ProviderRegistrar pr: anons){
					p=pr.getProvider(name);
					if(p!=null) break;
				}
			}
			return p;
		}else{
			String[] path=name.split("/");
			RootProviderRegistrar rpr=this;
			ProviderRegistrar pr=this;
			for (int i = 0; i < path.length - 1; i++) {
				rpr = (RootProviderRegistrar) pr;
				pr = rpr.registrars.get(path[i]);
			}
			return pr==this?super.providers.get(path[path.length-1])
					:pr.getProvider(path[path.length-1]);
			
		}
	}
}
