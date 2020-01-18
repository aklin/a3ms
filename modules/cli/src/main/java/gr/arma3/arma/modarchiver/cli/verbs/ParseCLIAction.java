package gr.arma3.arma.modarchiver.cli.verbs;


import gr.arma3.arma.modarchiver.api.v1.OpResult;
import gr.arma3.arma.modarchiver.api.v1.interfaces.OperationResult;
import gr.arma3.arma.modarchiver.api.v1.util.ExitCode;
import gr.arma3.arma.modarchiver.api.v1.util.Utils;
import lombok.Getter;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;

@CommandLine.Command(
	description = "Get existing resource",
	name = GetActionCLI.verb,
	mixinStandardHelpOptions = true,
	version = "1.0"
)
@Getter
public class ParseCLIAction extends AbstractCLIAction {
	protected static final String verb = "parse";
	private static final OperationResult noFile;
	private static final OperationResult noReadPerm;

	static {
		noFile = new OpResult(verb, ExitCode.Parser.MISSING_PARAM);
		noReadPerm = new OpResult(verb, ExitCode.App.READ_PERM);
	}

	@CommandLine.Option(
		names = {"-f", "--file"},
		description = Args.FILE_D
	)
	private File modFolder;

	public ParseCLIAction() {
		super(verb);
	}

	public boolean isDryRun() {
		return false;
	}

	public String getResourceType() {
		return "";
	}

	public String getResourceIdentifier() {
		return "";
	}


	@Override
	protected OperationResult persistResult() {
		if (modFolder == null) {
			return noFile;
		}

		if (!modFolder.canRead()) {
			return noReadPerm;
		}

		try {
			Files.walk(modFolder.toPath(), FileVisitOption.FOLLOW_LINKS).
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			Utils.parseFile(modFolder.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
}
