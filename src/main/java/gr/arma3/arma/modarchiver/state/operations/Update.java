package gr.arma3.arma.modarchiver.state.operations;

import gr.arma3.arma.modarchiver.api.v1.interfaces.ApiObject;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.java.Log;

@Log
@Getter
@SuperBuilder(toBuilder = true)
public class Update<E extends ApiObject> extends AbstractOperation<E> {

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