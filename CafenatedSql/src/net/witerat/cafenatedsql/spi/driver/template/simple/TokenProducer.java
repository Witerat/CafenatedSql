package net.witerat.cafenatedsql.spi.driver.template.simple;

/**
 * Process token event. push to a queue or compare with graph node.
 * @author John Hutcheson &lt;witerat.test@gmail.com&gt;
 *
 */
 interface TokenProducer {

  /**
   * Offer produced token to consumers.
   * @param tkStart start of token in expression.
   * @param tkEnd end of token in expression.
   * @param token Object describing token - result of lexical analysis.
   */
  void produceToken(int tkStart, int tkEnd, Object token);

  /**
   * Adds a consumer .
   *
   * @param consumer A consumer to be added.
   */
  void addConsumer(TokenConsumer consumer);

  /**
   * Removes a consumer.
   *
   * @param consumer the consumer
   */
  void removeConsumer(TokenConsumer consumer);

}
