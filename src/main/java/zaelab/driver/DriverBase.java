package zaelab.driver;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import java.nio.file.Paths;
import java.util.Arrays;

import zaelab.utilities.file.ReadPropertyFile;

public final class DriverBase {

    private static final String CONFIG_FILE = "config.properties";
    private static final ThreadLocal<Playwright> playwright = new ThreadLocal<>();
    private static final ThreadLocal<Browser> browser = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> browserContext = new ThreadLocal<>();
    private static final ThreadLocal<Page> page = new ThreadLocal<>();

    /**
     * Setting the reference of Playwright
     */
    private static void setPlaywright(){
        playwright.set(Playwright.create());
    }

    /**
     * Setting the browser reference
     */
    private static void setBrowser(){
        String binary = ReadPropertyFile.getProperty("binary", CONFIG_FILE);
        String channel = ReadPropertyFile.getProperty("channel", CONFIG_FILE);
        String headless = ReadPropertyFile.getProperty("headless", CONFIG_FILE);
        String windowSize = ReadPropertyFile.getProperty("screen-size", CONFIG_FILE);
        
        if (binary == null || channel == null || headless == null || windowSize == null) {
            throw new RuntimeException("Failed to load browser configuration from " + CONFIG_FILE);
        }
        
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
            .setChannel(channel)
            .setHeadless(Boolean.valueOf(headless))
            .setArgs(Arrays.asList(
                "--window-size="+windowSize,
                "--start-maximized"
            ))
            .setDownloadsPath(Paths.get(System.getProperty("user.dir")+"/src/test/resources/downloads"));
        
        Playwright pw = playwright.get();
        Browser b;
        switch (binary){
            case "chromium":
                b = pw.chromium().launch(launchOptions);
                break;
            case "webkit":
                b = pw.webkit().launch(launchOptions);
                break;
            case "firefox":
                b = pw.firefox().launch(launchOptions);
                break;
            default:
                b = pw.chromium().launch(launchOptions);
                break;
        }
        browser.set(b);
    }

    /**
     * Setting the browser context
     */
    private static void setBrowserContext(){
        browserContext.set(browser.get().newContext());
    }

    /**
     * Setting the Page
     */
    protected static void setPage(){
        Page p = browserContext.get().newPage();
        // Set viewport size at page level
        p.setViewportSize(1920, 1080);
        // Navigate to login URL
        try {
            String loginURL = ReadPropertyFile.getProperty("loginURL", CONFIG_FILE);
            if (loginURL != null) {
                p.navigate(loginURL);
                p.waitForLoadState(LoadState.NETWORKIDLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        page.set(p);
    }

    /**
     * Getting the Playwright reference
     */
    public static Playwright getPlaywright() {
        return playwright.get();
    }

    /**
     * Getting the Browser object
     */
    public static Browser getBrowser() {
        return browser.get();
    }

    /**
     * Getting the Browser Context object
     */
    public static BrowserContext getBrowserContext() {
        return browserContext.get();
    }

    /**
     * Getting the Page object
     */
    public static Page getPage() {
        return page.get();
    }

    /**
     * Initialize browser and page
     */
    public static void initialize() {
        System.out.println("[DriverBase] Initializing on thread: " + Thread.currentThread().getId());
        setPlaywright();
        setBrowser();
        setBrowserContext();
        setPage();
    }

    /**
     * This method returns the page object
     */
    public static Page getDriver(){
        if (page.get() == null) {
            initialize();
        }
        return page.get();
    }

    /**
     * Clean up resources
     */
    public static void cleanup() {
        System.out.println("[DriverBase] Cleaning up resources on thread: " + Thread.currentThread().getId());
        try {
            if (page.get() != null) {
                page.get().close();
                page.remove();
            }
            if (browserContext.get() != null) {
                browserContext.get().close();
                browserContext.remove();
            }
            if (browser.get() != null) {
                browser.get().close();
                browser.remove();
            }
            if (playwright.get() != null) {
                playwright.get().close();
                playwright.remove();
            }
        } catch (Exception e) {
            System.err.println("[DriverBase] Error during cleanup: " + e.getMessage());
        }
    }
}
