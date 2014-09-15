package jhc.redsniff.webdriver.activity;

import jhc.redsniff.webdriver.factory.WebDriverFactory;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Duration;

public abstract class AjaxActivity implements Activity {

    @Override
    public boolean busyOn(WebDriver driver) {
        JavascriptExecutor jsExec = (JavascriptExecutor) driver;
        Boolean ajaxBusy = (Boolean) jsExec
                .executeScript(ajaxBusyExpr());
        return ajaxBusy;
    }

    @Override
    public Duration initialDelay() {
        return WebDriverFactory.getInstance().getInitialAjaxDelayForDriver();
    }

    protected abstract String ajaxBusyExpr();

}
