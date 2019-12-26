package state.operations;


public class OperationException extends Exception {
	public OperationException(String s) {
		super(s);
	}

	public OperationException(Throwable throwable) {
		super(throwable);
	}
}
