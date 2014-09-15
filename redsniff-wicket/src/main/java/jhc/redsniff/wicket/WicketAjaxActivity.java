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
