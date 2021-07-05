package modularization.features.dashboard.models

class MessageModel(val id: Int, val username: String, val message: String) {

    companion object {
        fun getContactList(messageCount: Int): ArrayList<MessageModel> {
            val messages = ArrayList<MessageModel>()
            for (i in 1..messageCount) {
                messages.add(MessageModel(i, "username $i", "Message for show with number $i"))
            }
            return messages
        }
    }

}