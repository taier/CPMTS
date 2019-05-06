package test

import main.Const
import main.drivers.DriversFactory
import main.Helpers
import org.junit.Test
import java.io.IOException

class TestScreensLayout {
    @Test
    @Throws(InterruptedException::class, IOException::class)
    fun testLayout() {

        val testName = Thread.currentThread().stackTrace[1].methodName // name of method

        for (lang in Const.SUPPORTED_LANGUAGES.values()) { // for every language
            for (device in Const.SUPPORTED_DEVICES.values()) { // for every device
                for (orientation in Const.SUPPORTED_ORIENTATIONS.values()) { // for every orientation

                    val driver = DriversFactory.getDriver(device, lang, orientation)
                    Thread.sleep(5000)
                    Helpers.takeScreenshot(driver, testName, "main-screen")

                    driver.quit()
                }
            }
        }

        assert(true)
    }
}