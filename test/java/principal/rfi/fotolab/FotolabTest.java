package principal.rfi.fotolab;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import principal.rfi.fotolab.pageobjects.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;


@FixMethodOrder(MethodSorters.JVM)
public class FotolabTest {
    public static String configFile = "config/fotolab.properties";
    public static Properties config;
    private static Browser browser;

    @BeforeClass()
    public static void setup() throws IOException, FotolabException {
        config = Utils.getConfig(configFile);
        browser = new Browser(config.getProperty("webdriver.path"));
        browser.getDriver().get(config.getProperty("fotolab.url"));
    }

    @AfterClass
    public static void cleanup() {
        boolean shouldClose = Boolean.valueOf(
                config.getProperty("testDone.browser.close"));
        if (shouldClose)
            browser.close();
    }

    @Test
    public void testScenarioForRFI() throws Exception {
        List<String> expected = Arrays.asList(
                config.getProperty("fotoobrazy.expectedTypes").split(","));

        Fotolab fotolab = new Fotolab(browser.getDriver()).get();
        Fotoobrazy fotoobrazy = fotolab.selectFotoobrazy().get();
        fotoobrazy.checkAvailableTypes(expected);
        FotoNaPlatno fotoNaPlatno = fotoobrazy.selectFotoNaPlatno().get();
        ProductEditor editor = fotoNaPlatno.makeOrder().get();
        editor.uploadPhotoBackground(config.getProperty("user.photo.path"));
        ShoppingCart cart = editor.addOrderToCart().get();
        Registration registration = cart.proceedToRegistration();
    }
}