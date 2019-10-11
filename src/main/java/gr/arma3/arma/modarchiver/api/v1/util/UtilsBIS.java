package gr.arma3.arma.modarchiver.api.v1.util;

import gr.arma3.arma.modarchiver.api.v1.Mod;
import lombok.experimental.UtilityClass;
import lombok.extern.java.Log;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * BIS file format reader.
 */
@Log
@UtilityClass
public class UtilsBIS {
	private static final String[] keywords;
	private static final Pattern cpp;

	static {
		//keyword = "value";
		cpp = Pattern.compile("\\s*(\\w+)\\s*=\\s*\"(.*)\"\\s*;");
		keywords = new String[]{
			"name",
			"description",
			"timestamp",
			"publishedid",
		};
	}

	public Mod.ModBuilder readMod(
		final File modDirectory,
		final Mod.ModBuilder builder
	) {
		final Map<String, String> info;
		if (!modDirectory.isDirectory() || !modDirectory.getName().startsWith(
			"@")) {
			throw new RuntimeException("Invalid path: " + modDirectory.getPath());
		}

		try {
			info = readCppFile(new File(modDirectory, "mod.cpp"),
				new File(modDirectory, "meta.cpp"));
			Utils.fromMap(info);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return builder.folderName(modDirectory.getName());
	}

	public static Map<String, String> readCppFile(final File... files) throws
		IOException {
		final List<String> lines = new ArrayList<>();

		for (File file : files) {
			lines.addAll(Files.lines(file.toPath())
				.filter(s -> anyOf(s, keywords))
				.collect(Collectors.toList()));
		}

		return lines.stream()
			.map(cpp::matcher)
			.collect(Collectors.toMap(
				matcher -> matcher.group(1),
				matcher -> matcher.group(2))
			);
	}

	private boolean anyOf(final String test, final String... match) {
		return Arrays.asList(match).contains(test);
	}

	public Mod.ModBuilder readMod(final File modDirectory) {
		return readMod(modDirectory, Mod.builder());
	}
}
