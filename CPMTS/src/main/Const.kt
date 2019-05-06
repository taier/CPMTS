package main

object Const {

    internal var SCREENSHOTS_ROOT_FOLDER = "/screenshots/"
    internal var APP_ANDROID = "/apps/android.apk"
    internal var APP_IOS = "/apps/ios.app"
    var ANDROID_START_ACTIVITY = ""
    var APPIUM_URL = "http://0.0.0.0:4723/wd/hub"

    enum class SUPPORTED_LANGUAGES private constructor(val locale: String) {
        EN("EN"),
        RU("RU");
//        DE("DE"),
//        ES("ES"),
//        FR("FR"),
//        HI("HI"),
//        IT("IT"),
//        ZH("ZH"),
//        PT("PT"),
//        SV("SV"),
//        FI("FI"),
//        NO("NO"),
//        BN("BN"),
//        AR("AR"),
//        PA("PA"),
//        JA("JA"),
//        VI("VI"),
//        TR("TR"),
//        KO("KO");
    }

    enum class SUPPORTED_ORIENTATIONS private constructor(val orientation: String) {
//        LANDSCAPE("LANDSCAPE"),
        PORTRAIT("PORTRAIT");
    }


    enum class SUPPORTED_DEVICES private constructor(val deviceName: String, val versionOS: String, val deviceOS: DEVICE_OS) {
        // iOS
        //        IPHONE_SE_12_2("iPhone SE", "12.2", DEVICE_OS.IOS),
        //        IPHONE_Air_12_2("iPad Air", "12.2", DEVICE_OS.IOS),
        //        IPHONE_8_PLUS_12_2("iPhone 8 Plus", "12.2", DEVICE_OS.IOS);
        // Android
        //        GOOGLE_PIXEL_7_1("Nexus_5_API_28", "9.0", DEVICE_OS.ANDROID),
        //        NEXUS_S_API_28("Nexus_S_API_28", "9.0", DEVICE_OS.ANDROID),
        //        GOOGLE_PIXEL_8_0("Google Pixel", "8.0", DEVICE_OS.ANDROID),
        Nexus_5X_API_24("test_device", "7.0", Const.DEVICE_OS.ANDROID)
    }

    enum class DEVICE_OS private constructor(val appPath: String) {
        IOS(APP_IOS),
        ANDROID(APP_ANDROID)
    }
}
