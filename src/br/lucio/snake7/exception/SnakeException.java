package br.lucio.snake7.exception;

/**
 * Exception of the application
 * @author lucio
 *
 */
public class SnakeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SnakeException() {
	}

	public SnakeException(String message) {
		super(message);
	}

	public SnakeException(Throwable cause) {
		super(cause);
	}

	public SnakeException(String message, Throwable cause) {
		super(message, cause);
	}

	public SnakeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
