package net.witerat.cafenatedsql.api;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Interface Schema.
 */
public interface Schema {
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	String getName();
	
	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	void setName(String name);

	void setJournal(List<Refactor> refactors);
}
