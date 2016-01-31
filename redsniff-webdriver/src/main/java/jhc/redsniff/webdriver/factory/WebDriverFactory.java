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

import com.gargoylesoftware.htmlunit.BrowserVersion;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Duration;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.concurrent.TimeUnit.SECONDS;

public final class WebDriverFactory {

    protected interface WebDriverCreator {
        WebDriver createWebDriver(URL url, String driverClassName, Capabilities capabilities);
    }

    protected static class RemoteWebDriverCreator implements WebDriverCreator {

        @Override
        public WebDriver createWebDriver(URL url, String driverClassName, Capabilities capabilities) {
            return new RemoteWebDriver(url, capabilities);
        }

    }

    protected static class LocalWebDriverCreator implements WebDriverCreator {

        @Override
        public WebDriver createWebDriver(URL url, String driverClassName, Capabilities capabilities) {
            String driverMode = capabilities.getBrowserName();
            WebDriver driver;
            if ((driverClassName != null) && (!driverClassName.isEmpty())) {
                try {
                    Class<?> driverClass = Class.forName(driverClassName);
                    driver = (WebDriver) driverClass.newInstance();
                } catch (Exception e) {
                    throw new AssertionError("Unable to load driver: " + driverClassName + " due to " + e, e);
                }
            } else if (driverMode.equals(BrowserType.IE)) {
                driver = new InternetExplorerDriver(); // flaky
            } else if (driverMode.equals(BrowserType.FIREFOX)) {
                driver=new FirefoxDriver();
            } else if (driverMode.equals(BrowserType.CHROME)) {
                driver=new ChromeDriver();
            } else if (driverMode.equals(BrowserType.HTMLUNIT)) {
                HtmlUnitDriver htmlUnitdriver = new HtmlUnitDriver(BrowserVersion.FIREFOX_38);
                htmlUnitdriver.setJavascriptEnabled(true);
                driver=htmlUnitdriver;
            } else {
                throw new AssertionError("driverMode: " + driverMode + " not recognised");
            }
            return driver;
        }
    }

    protected static class DefaultWebDriverCreator implements WebDriverCreator {

        private final WebDriverCreator localWebDriverCreator;
        private final WebDriverCreator remoteWebDriverCreator;

        public DefaultWebDriverCreator(
                WebDriverCreator localWebDriverCreator,
                WebDriverCreator remoteWebDriverCreator) {
            this.localWebDriverCreator = localWebDriverCreator;
            this.remoteWebDriverCreator = remoteWebDriverCreator;
        }

        @Override
        public WebDriver createWebDriver(URL url, String driverClassName, Capabilities capabilities) {
            return url != null ?
                    remoteWebDriverCreator.createWebDriver(url, driverClassName, capabilities) :
                    localWebDriverCreator.createWebDriver(url, driverClassName, capabilities);
        }

    }

    private static class BasicDriverHolder {

        private WebDriver theDriver;

        private  WebDriver get() {
            return theDriver;
        }

        private  void set(WebDriver driver) {
            theDriver = driver;
        }
    }

    private final Log log = LogFactory.getLog(WebDriverFactory.class);

    private final WebDriverFactoryConfiguration configuration;
    private final WebDriverCreator webDriverCreator;
    private final BasicDriverHolder driverHolder = new BasicDriverHolder();
    //private static ThreadLocal<WebDriver> driverHolder = new ThreadLocal<WebDriver>();
    private final AtomicInteger instanceCount = new AtomicInteger();

    private static WebDriverFactoryConfiguration defaultConfiguration = defaultConfiguration();
    private static WebDriverFactory defaultInstance = new WebDriverFactory(defaultConfiguration);
    private static Map<WebDriverFactoryConfiguration, WebDriverFactory> instanceByConfiguration =
            new HashMap<WebDriverFactoryConfiguration, WebDriverFactory>();

    static {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                WebDriverFactory.cleanup();
            }
        });
    }

    protected WebDriverFactory(WebDriverFactoryConfiguration configuration, WebDriverCreator webDriverCreator) {
        this.configuration = configuration;
        this.webDriverCreator = webDriverCreator;
    }

    private WebDriverFactory(WebDriverFactoryConfiguration configuration) {
        this(configuration, defaultWebDriverCreator());
    }

    public static WebDriverFactory getInstance(WebDriverFactoryConfiguration configuration) {
        if ((configuration == null) || (configuration == defaultConfiguration)) {
            return defaultInstance;
        }
        synchronized (instanceByConfiguration) {
            WebDriverFactory factory = instanceByConfiguration.get(configuration);
            if (factory == null) {
                factory = new WebDriverFactory(configuration);
                instanceByConfiguration.put(configuration.clone(), factory);
            }
            return factory;
        }
    }

    public static WebDriverFactory getInstance() {
        return defaultInstance;
    }

    private static WebDriverCreator defaultWebDriverCreator() {
        return new DefaultWebDriverCreator(
                new LocalWebDriverCreator(),
                new RemoteWebDriverCreator());
    }

    protected void setInstance(WebDriverFactory factory) {
        defaultInstance = factory;
    }

    protected void setInstance(WebDriverFactoryConfiguration configuration, WebDriverFactory factory) {
        synchronized (instanceByConfiguration) {
            instanceByConfiguration.put(configuration.clone(), factory);
        }
    }

    //TODO - make this use capabilities syntax - investigate
    /**
     * @deprecated use {@link #requireDriver()} and {@link #releaseDriver()}
     */
    @Deprecated
    public static WebDriver initDriver() {
        return getInstance().getOrCreateDriver();
    }

    public WebDriver requireDriver() {
        instanceCount.incrementAndGet();
        return getOrCreateDriver();
    }

    public void releaseDriver() {
        if (instanceCount.decrementAndGet() == 0) {
            this.end();
        }
    }

    public static void cleanup() {
        defaultInstance.doCleanup();
        synchronized (instanceByConfiguration) {
            for (WebDriverFactory factory: instanceByConfiguration.values()) {
                factory.doCleanup();
            }
            instanceByConfiguration.clear();
        }
    }

    private static WebDriverFactoryConfiguration defaultConfiguration() {
        return WebDriverFactoryConfiguration.forSystemProperties();
    }

    private WebDriver getOrCreateDriver() {
        return activeDriver() != null ? activeDriver() : createDriver(this.configuration);
    }

    private WebDriver createDriver(WebDriverFactoryConfiguration configuration) {
        WebDriver driver = webDriverCreator.createWebDriver(
                configuration.getUrl(),
                configuration.getDriverClassName(),
                configuration.getCapabilities());
        driverHolder.set(driver);
        log.info("CREATED DRIVER: " + driver.getClass().getName());
        return driver;
    }

    public void doCleanup() {
        if (this.activeDriver() != null) {
            log.info("driver not ended, endinging it on shutdown: " + this.activeDriver());
            end();
            instanceCount.set(0);
        }
    }

    public void end() {
        log.info("KILLED DRIVER");
        activeDriver().quit();
        driverHolder.set(null);
    }

    // TODO use non-static method
    @Deprecated
    public static WebDriver getWebDriver() {
        return getInstance().getDriver();
    }

    public WebDriver getDriver() {
        WebDriver driver = this.activeDriver();
        if (driver == null) {
            log.info("driver is null");
        }
        return driver;
    }

    public void setWebDriver(WebDriver driver) {
        driverHolder.set(driver);
    }

    public Duration getInitialAjaxDelayForDriver() {
        WebDriver webDriver = activeDriver();
        if (webDriver instanceof HtmlUnitDriver) {
            return null;
          //new Duration(0, MILLISECONDS);
        } else if (webDriver instanceof ChromeDriver) {
            return null;
        } else {
            return new Duration(1, SECONDS);
        }
    }

    protected WebDriver activeDriver() {
        return driverHolder.get();
    }

    public Class<? extends WebDriver> getDriverClass() {
        WebDriver driver = driverHolder.get();
        return driver == null ? null : driver.getClass();
    }

  

    protected WebDriverFactoryConfiguration getConfiguration() {
        return configuration;
    }

    protected WebDriverCreator getWebDriverCreator() {
        return webDriverCreator;
    }

}