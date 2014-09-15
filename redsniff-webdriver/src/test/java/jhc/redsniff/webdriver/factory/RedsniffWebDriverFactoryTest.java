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
package jhc.redsniff.webdriver.factory;

import jhc.redsniff.webdriver.factory.WebDriverFactory.DefaultWebDriverCreator;
import jhc.redsniff.webdriver.factory.WebDriverFactory.LocalWebDriverCreator;
import jhc.redsniff.webdriver.factory.WebDriverFactory.WebDriverCreator;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class RedsniffWebDriverFactoryTest {

    private final WebDriverFactoryConfiguration configuration = WebDriverFactoryConfiguration.forCapabilities(
            DesiredCapabilities.phantomjs());
    private final WebDriverCreator creator = Mockito.mock(WebDriverCreator.class);
    private final WebDriver driver = Mockito.mock(WebDriver.class);

    @Before
    public void setup() {
        Mockito.when(this.creator.createWebDriver(
                configuration.getUrl(),
                configuration.getDriverClassName(),
                configuration.getCapabilities())).thenReturn(driver);
    }

    @Before
    @After
    public void cleanup() {
        WebDriverFactory.cleanup();
    }

    @Test
    public void getInstanceWithoutParametersShouldReturnInstanceWithDefaultConfigurationAndDriverCreator() {
        MatcherAssert.assertThat("instance.configuration",
        		WebDriverFactory.getInstance().getConfiguration(),
                Matchers.is(WebDriverFactoryConfiguration.forSystemProperties()));
        MatcherAssert.assertThat("instance.webDriverCreator",
        		WebDriverFactory.getInstance().getWebDriverCreator(),
                Matchers.instanceOf(DefaultWebDriverCreator.class));
    }

    @Test
    public void getInstanceWithConfigurationShouldReturnInstanceWithSpecifiedConfiguration() {
        WebDriverFactoryConfiguration configuration = WebDriverFactoryConfiguration.forCapabilities(
                DesiredCapabilities.phantomjs());
        MatcherAssert.assertThat("driver",
        		WebDriverFactory.getInstance(configuration).getConfiguration(),
                Matchers.is(configuration));
    }

    @Test
    public void getDriverShouldRequiredDriver() {
    	WebDriverFactory instance = new WebDriverFactory(configuration, creator);
        WebDriver driver = instance.requireDriver();
        MatcherAssert.assertThat("instance.driver",
                instance.getDriver(),
                Matchers.is(driver));
    }

    @Test
    public void getDriverShouldNullAfterRequireAndReleaseDriver() {
    	WebDriverFactory instance = new WebDriverFactory(configuration, creator);
        instance.requireDriver();
        instance.releaseDriver();
        MatcherAssert.assertThat("instance.driver",
                instance.getDriver(),
                Matchers.nullValue());
    }

    @Test
    public void getDriverShouldDriverAfterRequireRequireAndReleaseDriver() {
    	WebDriverFactory instance = new WebDriverFactory(configuration, creator);
        instance.requireDriver();
        instance.requireDriver();
        instance.releaseDriver();
        MatcherAssert.assertThat("instance.driver",
                instance.getDriver(),
                Matchers.is(driver));
    }

    @SuppressWarnings({ "deprecation" })
    @Test
    public void initDriverShouldReturnSameAsRequireDriver() {
        MatcherAssert.assertThat("driver",
        		WebDriverFactory.initDriver(),
                Matchers.is(WebDriverFactory.getInstance().requireDriver()));
    }

    @Test
    public void requireDriverShouldReturnDriverReturnedByFactory() {
        MatcherAssert.assertThat("driver",
                new WebDriverFactory(configuration, creator).requireDriver(),
                Matchers.is(driver));
    }

    // ignore as this creates the actual chrome driver and is slow
    @Ignore
    @Test
    public void localWebDriverCreatorDriverShouldReturnChromeDriverForChromeCapability() {
        MatcherAssert.assertThat("driver",
                new LocalWebDriverCreator().createWebDriver(
                        null, null, DesiredCapabilities.chrome()),
                Matchers.instanceOf(ChromeDriver.class));
    }

}
