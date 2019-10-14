package gr.arma3.arma.modarchiver.state.operations;

import gr.arma3.arma.modarchiver.api.v1.interfaces.ApiObject;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.java.Log;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

@Log
@Getter
@Builder(toBuilder = true)
public class Update<E extends ApiObject> implements Operation {

	@NotNull
	private final E resource;

	@NotNull
	@Builder.Default
	private final String user = "default";

	@NotNull
	@PastOrPresent
	@Builder.Default
	private final String eventTime = LocalDateTime.now().toString();

	@Builder.Default
	private final boolean dryRun = false;

	@Builder.Default
	private final boolean requireLock = true;

	/**
	 * When an object implementing interface <code>Runnable</code> is used
	 * to create a thread, starting the thread causes the object's
	 * <code>run</code> method to be called in that separately executing
	 * thread.
	 * <p>
	 * The general contract of the method <code>run</code> is that it may
	 * take any action whatsoever.
	 *
	 * @see Thread#run()
	 */
	@Override
	public void run() {

	}
}
