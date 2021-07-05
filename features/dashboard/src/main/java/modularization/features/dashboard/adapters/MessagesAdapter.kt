package modularization.features.dashboard.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import modularization.features.dashboard.R
import modularization.features.dashboard.models.MessageModel

class MessagesAdapter(private val messages: ArrayList<MessageModel>) : RecyclerView.Adapter<MessagesAdapter.ViewHolder>() {

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a row
        val messageTextView = itemView.findViewById<TextView>(R.id.textView_message)
        // val messageButton = itemView.findViewById<Button>(R.id.message_button)
    }

    // ... constructor and member variables
    // Usually involves inflating a layout from XML and returning the holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessagesAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView = inflater.inflate(R.layout.recyclerview_row_item, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }

    // Involves populating data into the item through holder
    override fun onBindViewHolder(viewHolder: MessagesAdapter.ViewHolder, position: Int) {
        // Get the data model based on position
        val message: MessageModel = messages[position]
        // Set item views based on your views and data model
        val textView = viewHolder.messageTextView
        textView.setText(message.message)

        // val button = viewHolder.messageButton
        // button.text = if (contact.isOnline) "Message" else "Offline"
        // button.isEnabled = contact.isOnline
    }

    // Returns the total count of items in the list
    override fun getItemCount(): Int {
        return messages.size
    }

}