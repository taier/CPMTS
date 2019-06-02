package main.interfaces

import io.appium.java_client.AppiumDriver
import org.openqa.selenium.WebElement

interface CPMTSDriver {

    // Device Info
    /**
     * @return current device name, the same as it was
     * defined in Constants
     */

    val deviceName: String

    /**
     * @return current device locale, the same as it was
     * defined in Constants
     */
    val locale: String

    /**
     * @return current device orientation, the same as it was
     * defined in Constants
     */
    val orientation: String

    /**
     * @return current device Platform (OS), the same as it was
     * defined in Constants
     */
    val platformName: String

    // Appium
    /**
     * @return original AppiumDriver
     */

    val driver: AppiumDriver<WebElement>

    // UI Actions
    /**
     * @param elementID ID of the element to get value from
     * For Android its +@id/ value
     * For iOS its accessibilityLabel value
     * @return
     */
    fun getTextFromElement(elementID: String): String

    /**
     * @param elementID ID of the element to set value to
     * For Android its +@id/ value
     * For iOS its accessibilityLabel value
     * @param text text to set to the element
     */
    fun setTextToElement(elementID: String, text: String)

    /**
     * @param elementID ID of the element to click to
     * For Android its +@id/ value
     * For iOS its accessibilityLabel value
     */
    fun clickOnElement(elementID: String)

    /**
     * @param elementID ID of the element to click to
     * For Android its +@id/ value
     * For iOS its accessibilityLabel value
     * @param alternativeID Alternative ID to click to
     * handy in cases when iOS and Android have
     * a same element but differently named
     */
    fun clickOnElement(elementID: String, alternativeID: String)

    /**
     * @param elementText text to find element with
     * and then to click on to
     * NOTE! Not implemented for iOS!
     */
    fun clickOnElementWithText(elementText:String)

    /**
     * @param text ID of the element to click to
     * For Android its +@id/ value
     * For iOS its accessibilityLabel value
     */
    fun scrollToElementWithText(text: String)

    /**
     * @param elementID  ID of the element to click to
     * For Android its +@id/ value
     * For iOS its accessibilityLabel value
     */
    fun scrollToElementWithID(elementID: String)

    /**
     * Go back from previous screen
     *
     * for iOS works only when current screen is
     * inside navigationController
     *
     * for Android its the same as press a back button
     */
    fun goBack()

    /**
     * For Android its sends AndroidKey.MENU
     * For iOS it does nothing
     */
    fun pressOnMenu()

    /**
     * That method is more relevant to iOS, because there are times
     * when you open a controller that you do not own (for example SFSafariViewController)
     * And on that screens, goBack would not work.
     *
     * As work around, this method would click on location (x:10, y:10) that in 99% represents
     * a back button position
     *
     * for Android, it calls toBack();
     */
    fun goBackFromModalWindow()

    /**
     * Scroll to the bottom on the current screen
     */
    fun scrollToBottom()

    /**
     * Scroll to the top of the current screen
     */
    fun scrollToTop()

    // Device Actions
    /**
     * Quit the current driver
     */

    fun quit()

    /**
     * Time to wait between each operation
     * Default - 3000
     * Most common use-case -> wait for elements to appear on the screen
     */
    var delay:Long
}
