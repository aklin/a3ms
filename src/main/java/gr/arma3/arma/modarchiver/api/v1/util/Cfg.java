package gr.arma3.arma.modarchiver.api.v1.util;

import lombok.ToString;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Utility class which holds information from config files.
 */
@UtilityClass
@ToString
public final class Cfg {
	private static final Logger logger;
	private static final Properties properties;

	static {
		logger = Logger.getLogger(Cfg.class.getName());
		properties = new Properties();

		final String[] configFiles = {
			"language.properties",
			"application.properties"
		};

		for (final String file : configFiles) {
			try (final Reader reader = new FileReader(
				new File(ClassLoader.getSystemResource(file).toURI()))) {

				properties.load(reader);

			} catch (IOException | URISyntaxException e) {
				logger.severe("Cannot open resource file: " + e.getLocalizedMessage());
			}
		}

	}

	public static String lang(final String langKey, final Object... args) {
		final String key = parseLangKey(langKey);

		if (!contains(key)) {
			logger.warning(MessageFormat.format(
				"No such language key: [{0}]",
				key
			));
			return key;
		}
		return MessageFormat.format(get(key), args);
	}

	private static String parseLangKey(final String langKey) {
		return (langKey == null ? "" : langKey).startsWith("lang.")
			? langKey
			: "lang." + langKey;
	}

	/**
	 * Get the value of a config key.
	 *
	 * @param key Key.
	 * @return Value or empty string.
	 */
	public static String get(final String key) {
		return properties.getProperty(key, "");
	}

	/**
	 * Get the boolean representation of a value corresponding to the given
	 * key.
	 *
	 * @param key Key.
	 * @return True if value is "true" (case insensitive).
	 */
	public static boolean getBoolean(final String key) {
		return Boolean.parseBoolean(get(key));
	}

	/**
	 * Get the integer representation of a value corresponding to the given
	 * key.
	 *
	 * @param key Key.
	 * @return Integer representation or 0.
	 */

	public static int getNumber(final String key) {
		try {
			return Integer.parseInt(get(key));
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	/**
	 * Check if a given key exists in the store.
	 *
	 * @param key Key.
	 * @return True if a value exists for this key.
	 */

	public static boolean contains(final String key) {
		return key != null && properties.contains(key);
	}

	public static int size() {
		return properties.size();
	}

	public void print() {
		properties.list(System.out);
	}
}
