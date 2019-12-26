package state.operations;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.java.Log;
import state.StateUtils;

@Log
@Getter
@SuperBuilder(toBuilder = true)
public class Create extends AbstractOperation {

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
		StateUtils.create(getResource());

	}
}
