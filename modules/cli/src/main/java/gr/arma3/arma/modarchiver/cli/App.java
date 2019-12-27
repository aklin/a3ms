package gr.arma3.arma.modarchiver.cli;

import gr.arma3.arma.modarchiver.state.redis.RedisPersistentState;
import lombok.Getter;
import state.PersistedState;

@Getter
public class App {
	private static PersistedState state;

	public App() {
		state = state == null ? new RedisPersistentState() : state;
	}
}
