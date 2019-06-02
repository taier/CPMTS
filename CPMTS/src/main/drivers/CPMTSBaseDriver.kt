package main.drivers

import main.Logger

open class CPMTSBaseDriver {

    var delay:Long = 3000

    protected fun performAction(action: () -> Unit) {
        try { action() }
        catch (e: Exception) {
            Logger.log(e.localizedMessage,
                Logger.OPERATION_LEVEL.ERROR)
        }
        Thread.sleep(this.delay) // Wait for UI to update after operation
    }
}