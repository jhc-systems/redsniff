package jhc.redsniff.webdriver.junit;


import jhc.redsniff.webdriver.factory.WebDriverFactory;

import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

/**
 * A runner for selenium tests that tries to instantiate one browser per entire run of tests. 
 * Use with caution where browser state (cookies etc) might be an issue across tests.
 * 
 * This works in eclipse but not in maven surefire since the runners are not created up front there - one
 * is created and run at a time so there is no way of telling if we have finished the entire run of things 
 * that need it. So in surefire it will just open and close the browser per Test class. 
 */
public class RedsniffWebDriverJUnitRunner extends BlockJUnit4ClassRunner {
static volatile int numActiveInstances;
	public RedsniffWebDriverJUnitRunner(Class<?> klass) throws InitializationError {
		
		super(klass);
		if(numActiveInstances==0){
		    try{
			WebDriverFactory.initDriver();
		    }catch(Exception e){
		        throw new InitializationError(e);
		    }
		}
		numActiveInstances++;
	}

	@Override
	public void run(final RunNotifier notifier) {
		notifier.addListener(treatAssumptionFailuresAsIgnoredLikeItsSupposedToListener(notifier));
		super.run(notifier);
		numActiveInstances--;
		if(numActiveInstances==0 && driverNeedsExplicitStopping()){
			WebDriverFactory.getInstance().end();
		}
	}

    protected boolean driverNeedsExplicitStopping() {
        Class<? extends WebDriver> driverClass = WebDriverFactory.getInstance().getDriverClass();
        return driverClass!=null && !(HtmlUnitDriver.class.isAssignableFrom(driverClass));
    }

    protected RunListener treatAssumptionFailuresAsIgnoredLikeItsSupposedToListener(final RunNotifier notifier) {
        return new RunListener(){
            @Override
            public void testAssumptionFailure(Failure failure) {
                System.out.println("!!ASSUMPTION VIOLATED!! - " +failure.getTestHeader() +":"  + failure.getMessage());
                notifier.fireTestIgnored(failure.getDescription());
                super.testAssumptionFailure(failure);
            }
		};
    }
}
