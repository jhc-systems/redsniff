/*******************************************************************************
 * Copyright 2014 JHC Systems Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
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
