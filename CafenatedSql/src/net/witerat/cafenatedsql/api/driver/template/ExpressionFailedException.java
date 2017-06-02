package net.witerat.cafenatedsql.api.driver.template;

// TODO: Auto-generated Javadoc
/**
 * The Class ExpressionFailedException.
 */
public class ExpressionFailedException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new expression failed exception.
	 *
	 * @param msg the msg
	 * @param cause the cause
	 * @param enableSuppression the enable suppression
	 * @param printableSackTrace the printable sack trace
	 */
	public ExpressionFailedException(String msg, Throwable cause, boolean enableSuppression, boolean printableSackTrace) {
		super(msg, cause, enableSuppression, printableSackTrace);
	}

	/**
	 * Instantiates a new expression failed exception.
	 *
	 * @param msg the msg
	 * @param cause the cause
	 */
	public ExpressionFailedException(String msg, Throwable cause) {
		super(msg, cause);
	}

	/**
	 * Instantiates a new expression failed exception.
	 *
	 * @param msg the msg
	 */
	public ExpressionFailedException(String msg) {
		super(msg);
	}

	/**
	 * Instantiates a new expression failed exception.
	 *
	 * @param cause the cause
	 */
	public ExpressionFailedException(Throwable cause) {
		super(cause);
	}

	/**
	 * Instantiates a new expression failed exception.
	 */
	public ExpressionFailedException() {
		super();
	}

}
