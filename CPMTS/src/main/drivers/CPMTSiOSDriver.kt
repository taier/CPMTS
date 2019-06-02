package main.drivers

import main.interfaces.CPMTSDriver
import io.appium.java_client.AppiumDriver
import io.appium.java_client.PerformsTouchActions
import io.appium.java_client.TouchAction
import io.appium.java_client.ios.IOSDriver
import io.appium.java_client.touch.offset.PointOption
import main.Const
import main.Logger
import org.openqa.selenium.WebElement
import java.util.HashMap

class CPMTSiOSDriver(override val driver: IOSDriver<WebElement>,
                     override val locale: String,
                     override val orientation: String) : CPMTSDriver, CPMTSBaseDriver() {

    private val startTime: Long

    override val deviceName: String
        get() = this.driver.capabilities.getCapability("device").toString()

    override val platformName: String
        get() = Const.DEVICE_OS.IOS.name

    init {
        this.startTime = System.currentTimeMillis()

        Logger.log(
            "create device - $deviceName $orientation $locale",
            Logger.OPERATION_LEVEL.IMPORTANT
        )
    }

    override fun getTextFromElement(elementID: String): String {
        val text = this.driver.findElementByAccessibilityId(elementID).getText()
        Logger.log("getTextFromElement - $elementID text - $text", Logger.OPERATION_LEVEL.TRIVIAL)
        return text
    }

    override fun setTextToElement(elementID: String, text: String) {
        Logger.log("setTextToElement - $elementID text - $text", Logger.OPERATION_LEVEL.TRIVIAL)
        performAction { this.driver.findElementByAccessibilityId(elementID).sendKeys(text) }
    }

    override fun clickOnElement(elementID: String) {
        Logger.log("clickOnElement - $elementID", Logger.OPERATION_LEVEL.TRIVIAL)
        performAction { this.driver.findElementByAccessibilityId(elementID).click() }
    }

    override fun clickOnElementWithText(elementText: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clickOnElement(elementID: String, alternativeID: String) {
        Logger.log("clickOnElement - $elementID", Logger.OPERATION_LEVEL.TRIVIAL)
        performAction { driver.findElementByAccessibilityId(elementID).click() }
        Logger.log("element - $elementID not found", Logger.OPERATION_LEVEL.TRIVIAL)
        performAction { driver.findElementByAccessibilityId(alternativeID).click() }
        Logger.log("clickOnElement - $alternativeID", Logger.OPERATION_LEVEL.TRIVIAL)
    }

    override fun scrollToElementWithText(text: String) {
        Logger.log("scrollToElementWithText - $text", Logger.OPERATION_LEVEL.TRIVIAL)
        performAction {
            val js = driver
            val scrollObject = HashMap<String, String>()
            scrollObject["direction"] = "down"
            scrollObject["name"] = text
            js.executeScript("mobile: scroll", scrollObject)
        }
    }

    override fun scrollToElementWithID(elementID: String) {
        //TODO
        scrollToBottom()
    }

    override fun goBack() {
        Logger.log("goBack", Logger.OPERATION_LEVEL.TRIVIAL)
        performAction { driver.navigate().back() }
    }

    override fun pressOnMenu() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun goBackFromModalWindow() {
        Logger.log("goBackFromModalWindow", Logger.OPERATION_LEVEL.TRIVIAL)

        performAction {
            var pointOption = PointOption.point(20,20)
            if (orientation == "LANDSCAPE") {
                pointOption = PointOption.point(10,10)
            }

            PlatformTouchAction(driver)
                .press(pointOption)
                .waitAction()
                .release()
                .perform()
        }
    }

    override fun scrollToBottom() {
        Logger.log("scrollToBottom", Logger.OPERATION_LEVEL.TRIVIAL)
        performAction {
            val js = driver
            val params = HashMap<String, Any>()
            params["direction"] = "down"
            js.executeScript("mobile: scroll", params)
        }
    }

    override fun scrollToTop() {
        Logger.log("scrollToTop", Logger.OPERATION_LEVEL.TRIVIAL)
        performAction {
            val js = driver
            val params = HashMap<String, Any>()
            params["direction"] = "up"
            js.executeScript("mobile: scroll", params)
        }
    }

    override fun quit() {
        this.driver.quit()
        val endTime = System.currentTimeMillis()

        var seconds = (endTime - startTime) / 1000
        val minutes = seconds % 3600 / 60
        seconds %= 60

        Logger.log(
            "driver quit, been alive for: " + String.format("%02d:%02d", minutes, seconds),
            Logger.OPERATION_LEVEL.IMPORTANT
        )
    }

    fun driver(): AppiumDriver<WebElement> {
        return this.driver
    }

    class PlatformTouchAction(performsTouchActions: PerformsTouchActions) : TouchAction<PlatformTouchAction>(performsTouchActions)
}