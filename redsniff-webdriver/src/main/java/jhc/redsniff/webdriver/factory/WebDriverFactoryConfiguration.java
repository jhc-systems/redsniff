package jhc.redsniff.webdriver.factory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;

public class WebDriverFactoryConfiguration implements Cloneable {

    private final URL url;
    private final String driverClassName;
    private final Capabilities capabilities;

    public WebDriverFactoryConfiguration(URL url, String driverClassName, Capabilities capabilities) {
        this.url = url;
        this.driverClassName = driverClassName;
        this.capabilities = capabilities;
    }

    public WebDriverFactoryConfiguration(String driverClassName, Capabilities capabilities) {
        this(null, driverClassName, capabilities);
    }

    public WebDriverFactoryConfiguration(URL url, Capabilities capabilities) {
        this(url, null, capabilities);
    }

    @Override
    public WebDriverFactoryConfiguration clone() {
        return new WebDriverFactoryConfiguration(url,
                new DesiredCapabilities(this.capabilities));
    }

    public static WebDriverFactoryConfiguration forUrl(URL hubUrl, Capabilities capabilities) {
        return new WebDriverFactoryConfiguration(hubUrl, capabilities);
    }

    public static WebDriverFactoryConfiguration forDriver(String driverClass, Capabilities capabilities) {
        return new WebDriverFactoryConfiguration(driverClass, capabilities);
    }

    public static WebDriverFactoryConfiguration forCapabilities(Capabilities capabilities) {
        return new WebDriverFactoryConfiguration(null, null, capabilities);
    }

    public static WebDriverFactoryConfiguration forSystemProperties() {
        Map<String, String> capabilitiesMap = extractPrefixedCapabilitiesProperties(
                System.getProperties(), "selenium.capabilities.");
        String hub = System.getProperty("selenium.hub", "");
        URL hubUrl;
        try {
            hubUrl = hub.isEmpty() ? null : new URL(hub);
        } catch (MalformedURLException e) {
            throw new AssertionError("invalid hub url: " + hub, e);
        }
        String defaultDriver = hubUrl != null ? "" : "HTMLUNIT-FIREFOX_17";
        String driverName = System.getProperty("selenium.driver", defaultDriver);
        return hubUrl != null ?
                WebDriverFactoryConfiguration.forUrl(hubUrl, new DesiredCapabilities(capabilitiesMap))
                : WebDriverFactoryConfiguration.forDriver(
                        driverClassForDriver(driverName),
                        capabilitiesMap.isEmpty() ?
                            capabilitiesForDriver(driverName) :
                            new DesiredCapabilities(capabilitiesMap));
    }

    private static Map<String, String> extractPrefixedCapabilitiesProperties(Map<?, ?> properties, String prefix) {
        Map<String, String> map = new HashMap<String, String>();
        for (Map.Entry<?, ?> entry: properties.entrySet()) {
            String key = String.valueOf(entry.getKey());
            if (key.startsWith(prefix)) {
                map.put(key.substring(prefix.length()), String.valueOf(entry.getValue()));
            }
        }
        return map;
    }

    public Capabilities getCapabilities() {
        return capabilities;
    }

    @Override
    public String toString() {
        return "WebDriverFactoryConfiguration [url=" + url + ", capabilities=" + capabilities + "]";
    }

    private static String driverClassForDriver(String driver) {
        return driver.contains(".") ? driver : "";
    }

    private static Capabilities capabilitiesForDriver(String driverMode) {
        if (driverMode.equals("IE")) {
            return DesiredCapabilities.internetExplorer();
        } else if (driverMode.equals("FIREFOX")) {
            return DesiredCapabilities.firefox();
        } else if (driverMode.equals("CHROME")) {
            return DesiredCapabilities.chrome();
        } else if (driverMode.equals("HTMLUNIT-FIREFOX_17")) {
            return DesiredCapabilities.htmlUnitWithJs();
        } else if (driverMode.contains(".")) {
            return new DesiredCapabilities();
        } else {
            throw new AssertionError("driverMode: " + driverMode + " not recognised");
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((capabilities == null) ? 0 : capabilities.hashCode());
        result = prime * result + ((url == null) ? 0 : url.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        WebDriverFactoryConfiguration other = (WebDriverFactoryConfiguration) obj;
        if (capabilities == null) {
            if (other.capabilities != null)
                return false;
        } else if (!capabilities.equals(other.capabilities))
            return false;
        if (url == null) {
            if (other.url != null)
                return false;
        } else if (!url.equals(other.url))
            return false;
        return true;
    }

    public URL getUrl() {
        return url;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

}
