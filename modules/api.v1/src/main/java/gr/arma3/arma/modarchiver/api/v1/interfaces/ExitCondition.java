package gr.arma3.arma.modarchiver.api.v1.interfaces;

import gr.arma3.arma.modarchiver.api.v1.util.ExitCode;

public interface ExitCondition extends Describable {

	default int getExitCode() {
		return ExitCode.App.INIT_FAILURE.getExitCode();
	}

	boolean isError();

	String getDescription();
}
