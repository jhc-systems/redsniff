package jhc.redsniff.wicket;

import static jhc.redsniff.wicket.ByWicketPath.byWicketPath;
import static jhc.redsniff.wicket.ByWicketPath.byWicketXPath;
import jhc.redsniff.core.MFinder;
import jhc.redsniff.webdriver.finders.ByFinder;

import org.hamcrest.Factory;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

public final class WicketFinders {
    private WicketFinders() {}
	@Factory
	public static MFinder<WebElement, SearchContext>  wicketItemByWXPath(String wicketXPath) {
		return new ByFinder(byWicketXPath(wicketXPath));
	  }

	@Factory
    public static MFinder<WebElement, SearchContext>  wicketItemByPath(String wicketPath) {
        return new ByFinder(byWicketPath(wicketPath));
      }
	
	@Factory
	public static MFinder<WebElement, SearchContext> authorityNotGrantedMessage() {
		return new ByFinder(byWicketXPath("//w{notGranted}"));
	}

}
