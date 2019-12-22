package gr.arma3.arma.modarchiver.api.v1.util;

import gr.arma3.arma.modarchiver.api.v1.Meta;
import gr.arma3.arma.modarchiver.api.v1.Mod;
import lombok.experimental.UtilityClass;
import lombok.extern.java.Log;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * BIS file format reader.
 */
@Log
@UtilityClass
public class UtilsBIS {
	private static final Pattern cpp;

	static {
		//keyword = "value";
		cpp = Pattern.compile("\\s*(\\w+)\\s*=\\s*\"([[^\"].]*)\"\\s*;");

	}

	public Mod.ModBuilder readMod(
		final File modDirectory
	) {
		final Map<String, String> info;
		final Meta.MetaBuilder meta;
		if (!modDirectory.isDirectory() || !modDirectory.getName().startsWith(
			"@")) {
			throw new RuntimeException("Invalid path: " + modDirectory.getPath());
		}

		meta = Meta.builder()
			.name(modDirectory.getName());

		try {
			info = readCppFile(
				new File(modDirectory, "mod.cpp"),
				new File(modDirectory, "meta.cpp")
			);


			System.out.println("----------======After=========------");
			info.forEach((k, v) -> System.out.println(k + ": " + v));

			System.out.println("Read " + info.size() + " entries");


		} catch (IOException e) {
			log.severe(e.getLocalizedMessage());
			return null;
		}

		final String lastRevision = Instant.ofEpochSecond(Long.parseLong(
			info.getOrDefault("timestamp", "0")) / 100000).toString();

		meta.description(info.getOrDefault("description", ""));

		return Mod.builder()
			.version(lastRevision)
//				.folderStructure()
			.lastRevision(lastRevision)
			.meta(meta.build());
	}

	public static Map<String, String> readCppFile(final File... files) throws
		IOException {
		final List<String> lines = new ArrayList<>();

		for (File file : files) {
			System.out.println("Loading " + file.getPath());
			lines.addAll(Files.lines(file.toPath())
				.collect(Collectors.toList()));
			System.out.println("\tNew list size: " + lines.size());
		}

		System.out.println("Lines-------");
		lines.forEach(System.out::println);

		return lines.stream()
			.map(cpp::matcher)
			.filter(Matcher::matches)
			.collect(Collectors.toMap(
				matcher -> matcher.group(1),
				matcher -> matcher.group(2),
				(s, s2) -> s
			));
	}

}
