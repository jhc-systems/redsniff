package jhc.redsniff.webdriver;

import org.openqa.selenium.support.ui.Duration;
import org.openqa.selenium.support.ui.ExpectedCondition;

public interface DelayingExpectedCondition<T> extends ExpectedCondition<T> {

    public Duration initialDelay();//so we don't check if busy before it's started..
    
}
