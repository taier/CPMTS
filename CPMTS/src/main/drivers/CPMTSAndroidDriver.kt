package main.drivers

import main.interfaces.CPMTSDriver
import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileBy
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.android.nativekey.AndroidKey
import io.appium.java_client.android.nativekey.KeyEvent
import io.appium.java_client.android.nativekey.KeyEventFlag
import main.Const
import main.Logger
import org.openqa.selenium.By
import org.openqa.selenium.WebElement

class CPMTSAndroidDriver(override val driver: AndroidDriver<WebElement>,
                         override val locale: String,
                         override val orientation: String) : CPMTSDriver, CPMTSBaseDriver() {

    private val startTime: Long

    override val deviceName: String
        get() = this.driver.capabilities.getCapability("device").toString()

    override val platformName: String
        get() = Const.DEVICE_OS.ANDROID.name

    init {
        this.startTime = System.currentTimeMillis()

        Logger.log(
            "create device - $deviceName $orientation $locale",
            Logger.OPERATION_LEVEL.IMPORTANT
        )
    }

    override fun getTextFromElement(elementID: String): String {
        val text = this.driver.findElementById(elementID).getText()
        Logger.log("getTextFromElement - $elementID text - $text", Logger.OPERATION_LEVEL.TRIVIAL)
        return text
    }

    override fun setTextToElement(elementID: String, text: String) {
        Logger.log("setTextToElement - $elementID text - $text", Logger.OPERATION_LEVEL.TRIVIAL)
        performAction { this.driver.findElementById(elementID).sendKeys(text) }
    }

    override fun clickOnElement(elementID: String) {
        Logger.log("clickOnElement - $elementID", Logger.OPERATION_LEVEL.TRIVIAL)
        performAction { this.driver.findElementById(elementID).click() }
    }

    override fun clickOnElementWithText(elementText:String) {
        Logger.log("clickOnElementWithText - $elementText", Logger.OPERATION_LEVEL.TRIVIAL)
        performAction { this.driver.findElementByAndroidUIAutomator("new UiSelector().text(\"$elementText\")").click() }
    }

    override fun clickOnElement(elementID: String, alternativeID: String) {
        //TODO: improve
        Logger.log("clickOnElement - $elementID", Logger.OPERATION_LEVEL.TRIVIAL)
        performAction {driver.findElementById(elementID).click()}
        Logger.log("clickOnElement - $alternativeID", Logger.OPERATION_LEVEL.TRIVIAL)
        performAction {driver.findElementById(alternativeID).click()}
    }

    override fun scrollToElementWithText(text: String) {
        Logger.log("scrollToElementWithText - $text", Logger.OPERATION_LEVEL.TRIVIAL)
        performAction { driver.findElement(
            MobileBy.AndroidUIAutomator(
                "new UiScrollable(new UiSelector())" +
                        ".scrollIntoView(new UiSelector().text(\"" + text + "\"));"
            )
        )}
    }

    override fun scrollToElementWithID(elementID: String) {
        Logger.log("scrollToElementWithID - $elementID", Logger.OPERATION_LEVEL.TRIVIAL)
        performAction { driver.findElements(MobileBy.AndroidUIAutomator("new UiScrollable( new UiSelector().scrollable(true).instance(0)).scrollIntoView( new UiSelector().resourceId(\"$elementID\").instance(0))")) }
    }

    override fun goBack() {
        Logger.log("goBack", Logger.OPERATION_LEVEL.TRIVIAL)
        performAction { driver.pressKey(KeyEvent(AndroidKey.BACK)) }
    }

    override fun pressOnMenu() {
        Logger.log("pressOnMenu", Logger.OPERATION_LEVEL.TRIVIAL)
        performAction { driver.pressKey(KeyEvent(AndroidKey.MENU)) }
    }

    override fun goBackFromModalWindow() {
        goBack()
    }

    override fun scrollToBottom() {
        Logger.log("scrollToBottom", Logger.OPERATION_LEVEL.TRIVIAL)
        performAction { driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollToEnd(1000)") }
    }

    override fun scrollToTop() {
        Logger.log("scrollToTop", Logger.OPERATION_LEVEL.TRIVIAL)
        performAction { driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).flingToBeginning(1000)") }
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
}
