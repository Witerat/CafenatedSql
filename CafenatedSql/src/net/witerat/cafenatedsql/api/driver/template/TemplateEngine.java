package net.witerat.cafenatedsql.api.driver.template;

// TODO: Auto-generated Javadoc
/**
 * The Interface TemplateEngine.
 */
public interface TemplateEngine {

	/**
	 * Define.
	 *
	 * @param id
	 *            the id
	 */
	void define(Object id);

	/**
	 * Inject fixture so that it can be used later. this method back the
	 * <code>&lt;def&gt;</code> tag functionality
	 *
	 * @param name
	 *            the name
	 * @param content
	 *            the content
	 * @see #appendFixture(String)
	 */
	void injectFixture(String name, String content);

	/**
	 * Sets the model that will be offered to the underlying template system
	 * during {@link #produce(Object, TemplateEngineModel)}.
	 *
	 * @param tem
	 *            the new model
	 */
	void setModel(TemplateEngineModel tem);

	/**
	 * Present input to the underlying template system and return the result the
	 * Underlying template systems operation.
	 *
	 * @param id
	 *            the id
	 * @param model
	 *            the model
	 * @return the string
	 */
	String produce(Object id, TemplateEngineModel model);

	/**
	 * Append text to the underlying template systems input.
	 *
	 * @param text
	 *            the text
	 */
	void appendText(String text);

	/**
	 * Append text of a fixture to the underlying template systems input.
	 *
	 * @param name
	 *            the name
	 */
	void appendFixture(String name);
}
