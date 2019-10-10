package gr.arma3.arma.modarchiver.api.v1.util;

import lombok.experimental.UtilityClass;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.MessageFormat;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Utility class which holds information from config files.
 */
@UtilityClass
public final class Config {
	private static final Logger logger;
	private static final Properties properties;

	static {
		logger = Logger.getLogger(Config.class.getName());
		properties = new Properties();

		final String[] configFiles = {
			"language.properties",
			"application.properties"
		};

		for (final String file : configFiles) {
			try (final Reader reader = new FileReader(
				ClassLoader.getSystemResource(file).getFile())) {

				properties.load(reader);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static String lang(final String langKey, final Object... args) {
		if (!contains(langKey)) {
			logger.severe(MessageFormat.format(
				"No such language key: [{0}]",
				langKey
			));
			return langKey;
		}
		return MessageFormat.format(get(langKey), args);
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
		return properties.contains(key);
	}
}
