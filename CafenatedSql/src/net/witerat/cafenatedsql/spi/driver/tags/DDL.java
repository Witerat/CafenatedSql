package net.witerat.cafenatedsql.spi.driver.tags;

import java.util.Collection;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


// TODO: Auto-generated Javadoc
/**
 * The Class DDL.
 */
@XmlType(name="DDLType",  namespace="-//org.witerat/cafenated/sql" )
public class DDL {
		
		/** The implies commit. */
		boolean impliesCommit;
		
		/** The parameters. */
		Collection<ParameterTag> parameters;
		
		/**
		 * Checks if is implies commit.
		 *
		 * @return true, if is implies commit
		 */
		@XmlAttribute(name="impliesCommit" )
		public boolean isImpliesCommit() {
			return impliesCommit;
		}
		
		/**
		 * Sets the implies commit.
		 *
		 * @param _impliesCommit the new implies commit
		 */
		void setImpliesCommit(boolean _impliesCommit){
			impliesCommit=_impliesCommit;
		}
		
		/**
		 * Gets the parameters.
		 *
		 * @return the parameters
		 */
		@XmlTransient
		public Collection<ParameterTag> getParameters() {
			return parameters;
		}

		/**
		 * Sets the parameters.
		 *
		 * @param parameters the new parameters
		 */
		public void setParameters(Collection<ParameterTag> parameters) {
			this.parameters = parameters;
		}


		
}
