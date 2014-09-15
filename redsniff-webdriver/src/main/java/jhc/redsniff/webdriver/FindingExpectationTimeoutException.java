package jhc.redsniff.webdriver;

import org.openqa.selenium.TimeoutException;


public class FindingExpectationTimeoutException extends TimeoutException {
	private static final long serialVersionUID = 1L;
	private String reason;
	private String originalMessage;

	public FindingExpectationTimeoutException() {
		super();
	}

	public FindingExpectationTimeoutException(String message, Throwable cause) {
		super(message, cause);
	}

	public FindingExpectationTimeoutException(String message) {
		super(message);
	}

	public FindingExpectationTimeoutException(Throwable cause) {
		super(cause);
	}

	public FindingExpectationTimeoutException(String originalMessage, String reason,
			TimeoutException e) {
		super(originalMessage +  "\nbecause:\n" + reason, e);
		this.originalMessage = originalMessage;
		this.reason = reason;
	}

	
	public String getOriginalMessage() {
		return originalMessage;
	}

	public String getReason() {
		return reason;
	}

	
}
