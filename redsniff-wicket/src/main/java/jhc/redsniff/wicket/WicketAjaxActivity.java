package jhc.redsniff.wicket;

import jhc.redsniff.webdriver.activity.Activity;
import jhc.redsniff.webdriver.activity.AjaxActivity;

public class WicketAjaxActivity extends AjaxActivity {

	@Override
	protected String ajaxBusyExpr() {
		String defineWicketDefined = String.format(
				"wicketDefined = function() {"
						+" return (typeof(Wicket) != \"undefined\");"
						+"};");
		String defineWicketAjaxBusy = String.format(
				"wicketAjaxBusy = function () {"
						+ "if(!wicketDefined()) return false;" 
						+ "for (var channelName in %1$s) {"
						+ "if (%1$s[channelName].busy) { return true; }" + "}"
						+ "return false;};", "Wicket.channelManager.channels");
		String defineWicketThrottlingInProgress = String
				.format("wicketThrottlingInProgress = function () {"
						+ "if(!wicketDefined()) return false;" 
						+ "for (var property in %1$s) {"
						+ "if (property.match(/th[0-9]+/) && %1$s[property] != undefined) { return true; }"
						+ "}" + "return false;};", "Wicket.throttler.entries");
		return defineWicketDefined + defineWicketAjaxBusy + defineWicketThrottlingInProgress
				+ "return wicketDefined() && ( wicketAjaxBusy() || wicketThrottlingInProgress());";
	}

	@Override
	public String toString() {
		return "wicket ajax";
	}
	
	public static Activity wicketAjax(){
		return new WicketAjaxActivity();
	}

}
