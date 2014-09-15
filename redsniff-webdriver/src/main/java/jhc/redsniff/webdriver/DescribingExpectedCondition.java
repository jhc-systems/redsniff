package jhc.redsniff.webdriver;

import static jhc.redsniff.internal.expectations.ExpectationChecker.checkerFor;
import jhc.redsniff.core.Describer;
import jhc.redsniff.internal.core.TypedQuantity;
import jhc.redsniff.internal.expectations.ExpectationCheckResult;
import jhc.redsniff.internal.expectations.FindingExpectation;
import jhc.redsniff.internal.util.StringHolder;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class DescribingExpectedCondition<T, E, Q extends TypedQuantity<E, T>>
		implements ExpectedCondition<T> {
	private final FindingExpectation<E, Q, SearchContext> expectation;
	private final StringHolder errorHolder;

	DescribingExpectedCondition(
			FindingExpectation<E, Q, SearchContext> expectation,
			StringHolder errorHolder) {
		this.expectation = expectation;
		this.errorHolder = errorHolder;
	}

	@Override
	public T apply(WebDriver driver) {
		//while waiting for something we want fail-fast- 
		//i.e. don't do the second unoptimized check - we don't care about nice failure descriptions yet
		ExpectationCheckResult<E, Q> resultOfChecking = checkerFor((SearchContext) driver).resultOfCheckingFailFast(expectation);
		if (resultOfChecking.meetsExpectation())
			return resultOfChecking.foundQuantity().get();
		else {
			errorHolder.setString(resultOfChecking.toString());
			return null;
		}
	}

	public String toString() {
		return Describer.newDescription().appendDescriptionOf(expectation)
				.toString();
	}
	
}