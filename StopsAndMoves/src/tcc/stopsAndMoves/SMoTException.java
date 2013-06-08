package tcc.stopsAndMoves;

public class SMoTException extends Exception {

	private static final long serialVersionUID = 1L;

	public SMoTException() {
	}

	public SMoTException(String message) {
		super(message);
	}

	public SMoTException(Throwable cause) {
		super(cause);
	}

	public SMoTException(String message, Throwable cause) {
		super(message, cause);
	}

	public SMoTException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
