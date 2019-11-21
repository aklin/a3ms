package gr.arma3.arma.modarchiver.api.v1.util;

import gr.arma3.arma.modarchiver.api.v1.interfaces.Describable;

public interface ExitCondition extends Describable {

	default int getExitCode() {
		return ExitCode.App.INIT_FAILURE.getExitCode();
	}

	;
}
