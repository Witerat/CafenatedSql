package net.witerat.cafenatedsql.api.driver;

/**
 * The Interface ParameterizedRequest.
 */
public interface ParameterizedRequest {

  /**
   * Adds a parameter to this request.
   *
   * @param p
   *          the parameter
   */
  void add(ParameterType p);

}
