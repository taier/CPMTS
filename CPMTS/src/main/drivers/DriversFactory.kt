package main.drivers

import main.interfaces.CPMTSDriver
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.ios.IOSDriver
import io.appium.java_client.remote.MobileCapabilityType
import main.Const
import main.Helpers
import org.openqa.selenium.remote.DesiredCapabilities
import java.net.MalformedURLException
import java.net.URL

object DriversFactory {

    @Throws(MalformedURLException::class)
    fun getDriver(
        device: Const.SUPPORTED_DEVICES,
        lang: Const.SUPPORTED_LANGUAGES,
        orientation: Const.SUPPORTED_ORIENTATIONS
    ): CPMTSDriver {

        val caps = DesiredCapabilities()

        caps.setCapability("deviceName", device.deviceName)
        caps.setCapability("device", device.deviceName)
        caps.setCapability("platformName", device.deviceOS.name)
        caps.setCapability("app", Helpers.appendRootPath(device.deviceOS.appPath))
        caps.setCapability("language", lang.locale)
        caps.setCapability("locale", "UK")
        caps.setCapability("fullReset", "true")
        caps.setCapability("orientation", orientation)
        caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, device.versionOS)

        if (device.deviceOS === Const.DEVICE_OS.ANDROID) {
            caps.setCapability("automationName", "UiAutomator2")
            caps.setCapability("appActivity", Const.ANDROID_START_ACTIVITY)
            caps.setCapability("avd", device.deviceName)
        } else {
            caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest")
        }

        val connectURL = URL(Const.APPIUM_URL)
        return if (device.deviceOS === Const.DEVICE_OS.IOS)
            CPMTSiOSDriver(IOSDriver(connectURL, caps), lang.locale, orientation.toString())
        else
            CPMTSAndroidDriver(AndroidDriver(connectURL, caps), lang.locale, orientation.toString())
    }
}