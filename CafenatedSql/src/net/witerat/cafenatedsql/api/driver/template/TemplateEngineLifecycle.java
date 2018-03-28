package net.witerat.cafenatedsql.api.driver.template;

/**
 * The Interface TemplateEngineLifecycle.
 */
public interface TemplateEngineLifecycle {

  /**
   * An event to advise resources are loaded.
   */
  void onLoad();

  /**
   * an event to advise that an underling template service is about to be
   * created.
   */
  void beforeNew();

  /**
   * New engine.
   *
   * @return the template engine
   */
  TemplateEngine newEngine();

  /**
   * A template service object is about to be discarded.
   */
  void cleanup();

  /**
   * Dispose of the template service object.
   */
  void dispose();

  /**
   * cafenated SQL or the JVM on which it runs is shutting down. Time to release
   * resource in an orderly fashion and quickly.
   */
  void shutdown();

}
