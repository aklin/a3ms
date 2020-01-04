package gr.arma3.arma.modarchiver.cli.verbs;

/**
 * Constants for the common CLI arguments.
 */
interface Args {
	String FILE_D = "Folder or files to process.";

	String DRUN_D = "Do not change server state, but pretend to do so.";

	String TYPE_D = "Resource type. Required.";
	String TYPE_L = "TYPE";
	String TYPE_ARITY = "0..1";
	String TYPE_INDEX = "0";

	String NAME_D = "Resource name.";
	String NAME_L = "NAME";
	String NAME_ARITY = "0..1";
	String NAME_INDEX = "1";
}
