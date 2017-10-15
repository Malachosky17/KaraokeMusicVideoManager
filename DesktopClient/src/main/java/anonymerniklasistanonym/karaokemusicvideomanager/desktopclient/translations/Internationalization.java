package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.translations;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Static language manager class.
 *
 * @author AnonymerNiklasistanonym <niklas.mikeler@gmail.com> | <a href=
 *         "https://github.com/AnonymerNiklasistanonym">https://github.com/AnonymerNiklasistanonym</a>
 */
public class Internationalization {

	/**
	 * The name of every bundle without language/locale code
	 */
	private static final String bundleName = "anonymerniklasistanonym/karaokemusicvideomanager/desktopclient/translations/ApplicationMessages";

	/**
	 * The current ResourceBundle
	 */
	public static ResourceBundle bundle = ResourceBundle.getBundle(bundleName);

	/**
	 * The current Locale/language code
	 */
	private static Locale locale = Locale.getDefault();

	/**
	 * Set/Reset the current bundle to the default bundle (English)
	 */
	public static void setBundle() {

		bundle = ResourceBundle.getBundle(bundleName);
		locale = Locale.US;

		System.out.println(">> Reset language: " + locale.toString());
	}

	/**
	 * Set the current bundle to the a special Locale/language
	 * 
	 * @param newLocale
	 *            (Locale | Language code)
	 */
	public static void setBundle(Locale newLocale) {

		// try to find a language package for the Locale
		try {

			System.out.println(">> Set new language: " + newLocale.toString());

			locale = newLocale;
			bundle = ResourceBundle.getBundle(bundleName, locale);

			System.out.println("<< New language package loaded");

		} catch (NullPointerException n) {
			n.printStackTrace();
			System.err.println("<< New language package not loaded!");
			setBundle();
		} catch (MissingResourceException m) {
			m.printStackTrace();
			System.err.println("<< New language package not loaded!");
			setBundle();
		}

	}

	/**
	 * Tries to translate the given String to the current language locale from the
	 * current ResourceBundle
	 * 
	 * @param text
	 *            (String | Text that should be translated)
	 * @return translated string or the input text if nothing was found
	 */
	public static String translate(String text) {

		// try to find the text in the current ResourceBundle/language package
		try {

			String translation = bundle.getString(text);

			if (translation != null && !translation.isEmpty()) {
				return translation;
			}

		} catch (NullPointerException e) {
			System.err.println("Key \"" + text + "\" is null!");
		} catch (MissingResourceException f) {
			System.err.println("No object could be found for the key \"" + text + "\"!");
		} catch (ClassCastException g) {
			System.err.println("The found object for the key \"" + text + "\" is not a String!");
		}

		return text;
	}

	public static String getLocaleString() {
		return locale.getLanguage();
	}

}
