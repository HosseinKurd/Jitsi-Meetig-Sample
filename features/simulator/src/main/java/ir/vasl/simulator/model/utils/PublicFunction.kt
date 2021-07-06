package ir.vasl.simulator.model.utils

class PublicFunction {

    companion object {
        var messageId: Int = 1000

        @JvmName("getMessageIDValue")
        fun getMessageID(): Int {
            messageId += 1
            return messageId
        }
    }
}