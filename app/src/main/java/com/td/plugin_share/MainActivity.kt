package com.td.plugin_share

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        PluginManager.init(this)
    }

    fun jump1(view: View) {
        val intent = Intent()
        intent.setClassName(this, "com.test.plugin.PluginActivity")
        PluginManager.startActivity(this, intent)
    }

    fun jump3(view: View) {
        startActivity(Intent(this,MainActivity3::class.java))
    }
}