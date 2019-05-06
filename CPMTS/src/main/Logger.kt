package main

object Logger {

    enum class OPERATION_LEVEL {
        TRIVIAL,
        MEDIUM,
        IMPORTANT
    }

    fun log(operation: String, level: OPERATION_LEVEL) {
        var operation = operation
        when (level) {
            Logger.OPERATION_LEVEL.IMPORTANT -> operation = "********** $operation **********"
            Logger.OPERATION_LEVEL.MEDIUM -> operation = "***** $operation *****"
            Logger.OPERATION_LEVEL.TRIVIAL -> {
            }
        }
        println(operation)
    }
}
