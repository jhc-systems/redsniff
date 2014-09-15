package jhc.redsniff.webdriver;

import jhc.redsniff.internal.core.TypedQuantity;
import jhc.redsniff.internal.expectations.FindingExpectation;
import jhc.redsniff.internal.util.StringHolder;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Duration;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Sleeper;

public class Waiter {

    private final FluentWait<WebDriver> wait;

    public Waiter(FluentWait<WebDriver> wait) {
        this.wait = wait;
    }

    public <T, I, Q extends TypedQuantity<I, T>> T waitFor(
            final FindingExpectation<I, Q, SearchContext> expectation) {
        StringHolder errorHolder = new StringHolder("");
        ExpectedCondition<T> condition = new DescribingExpectedCondition<T, I, Q>(
                expectation, errorHolder);
        try {
            return wait.until(condition);
        } catch (TimeoutException e) {
            throw new FindingExpectationTimeoutException(e.getMessage() , errorHolder.toString(), e);
        }
    }

    public <V> V waitFor(final ExpectedCondition<V> con) {
        if (con instanceof DelayingExpectedCondition) {
            waitForInitialDelay((DelayingExpectedCondition<V>) con);
        }
        return wait.until(con);
    }

    private <V> void waitForInitialDelay(DelayingExpectedCondition<V> delayingExpectedCondition) {
        Duration initialDelay = delayingExpectedCondition.initialDelay();
        if (initialDelay != null) {
            sleep(initialDelay);
        }
    }

    public void sleep(Duration duration) {
        try {
            Sleeper.SYSTEM_SLEEPER.sleep(duration);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public <V> V waitForOrContinueAnyway(final ExpectedCondition<V> con) {
        try {
            return waitFor(con);
        } catch (TimeoutException e) {
            return null;
        }
    }

    public <T, I, Q extends TypedQuantity<I, T>> T waitForOrContinueAnyway(
            final FindingExpectation<I, Q, SearchContext> expectation) {
        try {
            return waitFor(expectation);
        } catch (TimeoutException e) {
            return null;
        }
    }

}
