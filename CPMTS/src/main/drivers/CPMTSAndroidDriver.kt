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

class CPMTSAndroidDriver(override val driver: AndroidDriver<WebElement>, override val locale: String, override val orientation: String) : CPMTSDriver {

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
        this.driver.findElementById(elementID).sendKeys(text)
        Logger.log("setTextToElement - $elementID text - $text", Logger.OPERATION_LEVEL.TRIVIAL)
    }

    override fun clickOnElement(elementID: String) {
        this.driver.findElementById(elementID).click()
        Logger.log("clickOnElement - $elementID", Logger.OPERATION_LEVEL.TRIVIAL)
    }

    override fun clickOnElementWithText(elementText:String) {
        this.driver.findElementByAndroidUIAutomator("new UiSelector().text(\"$elementText\")").click()
        Logger.log("clickOnElementWithText - $elementText", Logger.OPERATION_LEVEL.TRIVIAL)
    }

    override fun clickOnElement(elementID: String, alternativeID: String) {
        //NOTE: I know its ugly, but well... Appium.
        try {
            Logger.log("clickOnElement - $elementID", Logger.OPERATION_LEVEL.TRIVIAL)
            driver.findElementById(elementID).click()
        } catch (ex: Exception) {
            Logger.log("element - $elementID not found", Logger.OPERATION_LEVEL.TRIVIAL)
            driver.findElementById(alternativeID).click()
            Logger.log("clickOnElement - $alternativeID", Logger.OPERATION_LEVEL.TRIVIAL)
        }

    }

    override fun scrollToElementWithText(text: String) {
        driver.findElement(
            MobileBy.AndroidUIAutomator(
                "new UiScrollable(new UiSelector())" +
                        ".scrollIntoView(new UiSelector().text(\"" + text + "\"));"
            )
        )
        Logger.log("scrollToElementWithText - $text", Logger.OPERATION_LEVEL.TRIVIAL)
    }

    override fun scrollToElementWithID(elementID: String) {
        driver.findElements(MobileBy.AndroidUIAutomator("new UiScrollable( new UiSelector().scrollable(true).instance(0)).scrollIntoView( new UiSelector().resourceId(\"$elementID\").instance(0))"))
    }

    override fun goBack() {
        driver.pressKey(KeyEvent(AndroidKey.BACK))
        Logger.log("goBack", Logger.OPERATION_LEVEL.TRIVIAL)
    }

    override fun pressOnMenu() {
        driver.pressKey(KeyEvent(AndroidKey.MENU))
        Logger.log("pressOnMenu", Logger.OPERATION_LEVEL.TRIVIAL)
    }

    override fun goBackFromModalWindow() {
        goBack()
    }

    override fun scrollToBottom() {
        //NOTE: I know its ugly, but well... Appium.
        try {
            driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollToEnd(1000)")
        } catch (Ex: Exception) {
        }

        Logger.log("scrollToBottom", Logger.OPERATION_LEVEL.TRIVIAL)
    }

    override fun scrollToTop() {
        //NOTE: I know its ugly, but well... Appium.
        try {
            driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).flingToBeginning(1000)")
        } catch (Ex: Exception) {
        }

        Logger.log("scrollToTop", Logger.OPERATION_LEVEL.TRIVIAL)
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
