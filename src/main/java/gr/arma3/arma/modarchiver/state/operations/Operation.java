package gr.arma3.arma.modarchiver.state.operations;

import gr.arma3.arma.modarchiver.api.v1.interfaces.ApiObject;

public interface Operation<E extends ApiObject> extends Runnable {

	E getResource();

	String getUser();

	String getEventTime();

	boolean isDryRun();

	boolean isRequireLock();
}
