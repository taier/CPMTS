package main

import main.interfaces.CPMTSDriver
import io.appium.java_client.AppiumDriver
import org.apache.commons.io.FileUtils
import org.openqa.selenium.OutputType
import org.openqa.selenium.WebElement
import java.io.File
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object Helpers {

    fun takeScreenshot(driver: CPMTSDriver, testName: String, screenName: String) {

        val dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val localDate = LocalDate.now()
        val date = dtf.format(localDate)

        Logger.log("takeScreenshot - $screenName", Logger.OPERATION_LEVEL.MEDIUM)

        val testFolder = date + " " + testName + "/" + driver.platformName
        val composedPath =  testFolder + "/" + driver.deviceName + "/" + driver.orientation + "/" + driver.locale + "/" + screenName + ".jpg"

        screenshot(
            composedPath,
            driver.driver
        )
    }

    private fun screenshot(
        composedPath: String,
        driver: AppiumDriver<WebElement>
    ) {
        val srcFile = driver.getScreenshotAs(OutputType.FILE)
        val targetFile =
            File(appendRootPath(Const.SCREENSHOTS_ROOT_FOLDER + composedPath))
        try {
            FileUtils.copyFile(srcFile, targetFile)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun appendRootPath(urlPath: String): String {
        return File("").absolutePath + urlPath
    }
}
