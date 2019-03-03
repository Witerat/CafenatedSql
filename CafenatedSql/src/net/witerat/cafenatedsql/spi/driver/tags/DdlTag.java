package net.witerat.cafenatedsql.spi.driver.tags;

import java.util.Collection;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * The Class DdlTag marks a dialect as definition language.
 * @see DmlTag
 */
@XmlType(name = "DDLType", namespace = "-//org.witerat/cafenated/sql")
public class DdlTag {

  /** The implies commit. */
  private boolean impliesCommit;

  /** The parameters. */
  private Collection<ParamTag> parameters;

  /**
   * Checks if is implies commit.
   *
   * @return true, if is implies commit
   */
  @XmlAttribute(name = "impliesCommit")
  public boolean isImpliesCommit() {
    return impliesCommit;
  }

  /**
   * Sets the implies commit.
   *
   * @param impliesCommit0
   *          the new implies commit state.
   */
  public void setImpliesCommit(final boolean impliesCommit0) {
    this.impliesCommit = impliesCommit0;
  }

  /**
   * Gets the parameters.
   *
   * @return the parameters
   */
  @XmlTransient
  public Collection<ParamTag> getParameters() {
    return parameters;
  }

  /**
   * Sets the parameters.
   *
   * @param parameters0
   *          the new parameters
   */
  public void setParameters(final Collection<ParamTag> parameters0) {
    this.parameters = parameters0;
  }

}
