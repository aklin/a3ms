package gr.arma3.arma.modarchiver.state.operations;

import gr.arma3.arma.modarchiver.api.v1.interfaces.ApiObject;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

/**
 * An operation that interacts with the server state. Can be queued.
 *
 * @param <E>
 */
public interface Operation<E extends ApiObject>
	extends Runnable, AutoCloseable {

	/**
	 * Resource to be operated on. Must not be null.
	 *
	 * @return
	 */
	@NotNull
	E getResource();

	/**
	 * User ID of the user who submitted this request.
	 *
	 * @return
	 */
	@NotEmpty
	String getUser();

	/**
	 * Get date and time of when this request was submitted to the server.
	 *
	 * @return
	 */
	@PastOrPresent
	String getEventTime();

	/**
	 * Pretend to do things but don't do them. Does not set global state.
	 *
	 * @return
	 */
	boolean isDryRun();

	/**
	 * True if the operation needs to lock the global state to itself.
	 *
	 * @return
	 */
	boolean isRequireLock();

	/**
	 * @return Object has the global lock.
	 */
	boolean isLocked();

}
