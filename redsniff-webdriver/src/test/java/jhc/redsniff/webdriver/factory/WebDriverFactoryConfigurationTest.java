package jhc.redsniff.webdriver.factory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;


public class WebDriverFactoryConfigurationTest {

    @Before
    @After
    public void clear() {
        for (Object key: new ArrayList<>(System.getProperties().keySet())) {
            if (String.valueOf(key).startsWith("selenium.")) {
                System.clearProperty(String.valueOf(key));
            }
        }
    }

    @Test
    public void shouldReturnInternetExplorerConfiguration() {
        System.setProperty("selenium.driver", "IE");
        MatcherAssert.assertThat(
                WebDriverFactoryConfiguration.forSystemProperties(),
                Matchers.is(WebDriverFactoryConfiguration.forCapabilities(DesiredCapabilities.internetExplorer())));
    }

    @Test
    public void shouldReturnFirefoxConfiguration() {
        System.setProperty("selenium.driver", "FIREFOX");
        MatcherAssert.assertThat(
                WebDriverFactoryConfiguration.forSystemProperties(),
                Matchers.is(WebDriverFactoryConfiguration.forCapabilities(DesiredCapabilities.firefox())));
    }

    @Test
    public void shouldReturnChromeConfiguration() {
        System.setProperty("selenium.driver", "CHROME");
        MatcherAssert.assertThat(
                WebDriverFactoryConfiguration.forSystemProperties(),
                Matchers.is(WebDriverFactoryConfiguration.forCapabilities(DesiredCapabilities.chrome())));
    }

    @Test
    public void shouldReturnHtmlUnitConfiguration() {
        System.setProperty("selenium.driver", "HTMLUNIT-FIREFOX_17");
        MatcherAssert.assertThat(
                WebDriverFactoryConfiguration.forSystemProperties(),
                Matchers.is(WebDriverFactoryConfiguration.forCapabilities(DesiredCapabilities.htmlUnitWithJs())));
    }

    @Test
    public void shouldReturnConfigurationWithPassedInCapabilitiesWithoutHub() {
        System.setProperty("selenium.capabilities.browserName", "proprietyBrowser");
        System.setProperty("selenium.capabilities.proprietyProperty", "someValue");
        System.setProperty("selenium.capabilities.proprietyProperty.x", "someValueX");
        Map<String, String> capabilitiesMap = new HashMap<>();
        capabilitiesMap.put("browserName", "proprietyBrowser");
        capabilitiesMap.put("proprietyProperty", "someValue");
        capabilitiesMap.put("proprietyProperty.x", "someValueX");
        MatcherAssert.assertThat(
                WebDriverFactoryConfiguration.forSystemProperties(),
                Matchers.is(WebDriverFactoryConfiguration.forCapabilities(new DesiredCapabilities(capabilitiesMap))));
    }

    @Test
    public void shouldReturnConfigurationWithPassedInCapabilitiesWithHub() throws MalformedURLException {
        System.setProperty("selenium.hub", "http://server:80/wd/hub");
        System.setProperty("selenium.capabilities.browserName", "proprietyBrowser");
        System.setProperty("selenium.capabilities.proprietyProperty", "someValue");
        System.setProperty("selenium.capabilities.proprietyProperty.x", "someValueX");
        Map<String, String> capabilitiesMap = new HashMap<>();
        capabilitiesMap.put("browserName", "proprietyBrowser");
        capabilitiesMap.put("proprietyProperty", "someValue");
        capabilitiesMap.put("proprietyProperty.x", "someValueX");
        MatcherAssert.assertThat(
                WebDriverFactoryConfiguration.forSystemProperties(),
                Matchers.is(WebDriverFactoryConfiguration.forUrl(
                        new URL("http://server:80/wd/hub"), new DesiredCapabilities(capabilitiesMap))));
    }

}
