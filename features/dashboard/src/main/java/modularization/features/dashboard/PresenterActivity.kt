package modularization.features.dashboard

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import modularization.features.dashboard.adapters.MessagesAdapter
import modularization.features.dashboard.models.MessageModel

class PresenterActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_presenter)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.title = "Android Presenter"

        findViewById<View>(R.id.button_send).setOnClickListener(this)

        val recyclerView = findViewById<View>(R.id.recyclerView) as RecyclerView
        val messages = MessageModel.getContactList(20)
        val adapter = MessagesAdapter(messages)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.presenter_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_video_call -> {
                startVideoCall()
                true
            }
            R.id.action_voice_call -> {
                startVoiceCall()
                true
            }
            else ->
                super.onOptionsItemSelected(item)
        }
    }

    private fun startVideoCall() {
        Toast.makeText(this@PresenterActivity, "start video call", Toast.LENGTH_SHORT).show()
    }

    private fun startVoiceCall() {
        Toast.makeText(this@PresenterActivity, "start voice call", Toast.LENGTH_SHORT).show()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button_send -> {
                Toast.makeText(this@PresenterActivity, "we have a click", Toast.LENGTH_SHORT).show()
            }
        }
    }

}