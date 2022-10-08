package net.witerat.cafenatedsql.spi.driver.template.simple;

/**
 * The TokenConsumer interface.
 */
interface TokenConsumer {

   /**
    * Consume token.
    *
    * @param tkStart0 the start of token in expression
    * @param tkEnd0 the end of the token in expression
    * @param token0 the token description object
    */
   void consumeToken(int tkStart0, int tkEnd0, Object token0);

 }
