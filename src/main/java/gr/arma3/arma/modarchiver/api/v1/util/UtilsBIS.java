package gr.arma3.arma.modarchiver.api.v1.util;

import gr.arma3.arma.modarchiver.api.v1.Meta;
import gr.arma3.arma.modarchiver.api.v1.Mod;
import gr.arma3.arma.modarchiver.api.v1.interfaces.MetaInfo;
import lombok.experimental.UtilityClass;
import lombok.extern.java.Log;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.regex.Matcher;
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
		final File modDirectory//,
//		final Mod.ModBuilder builder
	) {
		final Map<String, String> info;
		final MetaInfo meta;
		if (!modDirectory.isDirectory() || !modDirectory.getName().startsWith(
			"@")) {
			throw new RuntimeException("Invalid path: " + modDirectory.getPath());
		}

		meta = Meta.builder()
			.name(modDirectory.getName())
			.type("Mod")
			.build();

		try {
			info = readCppFile(new File(modDirectory, "mod.cpp"),
				new File(modDirectory, "meta.cpp"));

			System.out.println("Read " + info.size() + " entries");

			info.forEach((s, s2) -> {
				System.out.println(s + ": " + s2);
			});


			return Objects.requireNonNull(Utils.<Mod>fromMap(info))
				.toBuilder().meta(meta);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static Map<String, String> readCppFile(final File... files) throws
		IOException {
		final List<String> lines = new ArrayList<>();

		for (File file : files) {
			lines.addAll(Files.lines(file.toPath())
				.collect(Collectors.toList()));
		}

		return lines.stream()
			.map(cpp::matcher)
			.filter(Matcher::matches)
			.filter(matcher -> anyOf(matcher.group(1), keywords))
			.collect(Collectors.toMap(
				matcher -> matcher.group(1),
				matcher -> matcher.group(2),
				(s, s2) -> s
			));
	}

	private boolean anyOf(final String test, final String... match) {
		return Arrays.asList(match).contains(test);
	}

/*	public Mod.ModBuilder readMod(final File modDirectory) {
		return readMod(modDirectory, Mod.builder());
	}*/
}
