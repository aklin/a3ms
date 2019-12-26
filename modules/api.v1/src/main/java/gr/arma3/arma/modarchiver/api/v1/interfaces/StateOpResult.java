package gr.arma3.arma.modarchiver.api.v1.interfaces;

import gr.arma3.arma.modarchiver.api.v1.UserInfoMessage;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;
import java.util.List;


public interface StateOpResult extends Typeable {

	@NotNull
	@PastOrPresent
	LocalDateTime getStart();

	@NotNull
	@PastOrPresent
	LocalDateTime getEnd();

	@NotNull
	List<UserInfoMessage> getMessages();

	@NotNull
	List<ApiObject> getResults();


	default boolean isStarted() {
		return getStart() != null;
	}

	default boolean isEnded() {
		return getEnd() != null;
	}

	boolean isSuccess();

	default boolean isFailure() {
		return !isSuccess();
	}
}
