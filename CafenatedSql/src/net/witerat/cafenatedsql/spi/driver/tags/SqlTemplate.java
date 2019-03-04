package net.witerat.cafenatedsql.spi.driver.tags;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * The class SqlTemplate.
 */
@XmlType(name = "TemplateTag")
public class SqlTemplate {

  /** The parameters. */
  private Map<String, ParamTag> parametersByName;

  /**
   * Gets the parameters.
   *
   * @return the parameters
   */
  @XmlTransient
  public Collection<ParamTag> getParameters() {
    if (parametersByName == null) {
      parametersByName = new LinkedHashMap<>();
    }

    return parametersByName.values();
  }

  /**
   * Sets the parameters.
   *
   * @param parameters
   *          the new parameters
   */
  public void setParameters(final Collection<ParamTag> parameters) {
    if (parameters == null) {
      parametersByName = null;
    } else {
      Map<String, ParamTag> parsByName = new LinkedHashMap<>();
      for (ParamTag p : parameters) {
        parsByName.put(p.getName(), p);
      }
      parametersByName = parsByName;
    }
  }

  /**
   * Add parameters.
   * @param param
   *          a new parameter
   */
  public void setParameter(final ParamTag param) {
    if (parametersByName == null) {
      parametersByName = new LinkedHashMap<>();
    }
    parametersByName.put(param.getName(), param);
  }
}
