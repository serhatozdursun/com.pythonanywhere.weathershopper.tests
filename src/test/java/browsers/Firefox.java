package browsers;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.HashMap;

public class Firefox implements BrowserSelectable {

    @Override
    public MutableCapabilities getCapabilities() {
        FirefoxOptions options = new FirefoxOptions();
        var prefs = new HashMap<String, Object>();
        prefs.put("profile.default_content_setting_values.notifications", 2);
        options.addArguments("--kiosk");
        options.addArguments("--disable-notifications");
        options.addArguments("--start-fullscreen");
        options.setCapability(CapabilityType.BROWSER_NAME,"firefox");
        options.setCapability("perf", prefs);
        return options;
    }

    @Override
    public RemoteWebDriver getBrowser(URL remoteAddress) {
        return new RemoteWebDriver(remoteAddress, getCapabilities());
    }
}
