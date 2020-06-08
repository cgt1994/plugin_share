package com.td.plugin_share

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dalvik.system.DexClassLoader
import dalvik.system.PathClassLoader

class PluginBaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plugin_base)

        val classPath=intent.extras?.getString(PluginManager.pluginKey)?:""
        val dexClassLoader=DexClassLoader(PluginManager.getPluginPath(this),null,PluginManager.getPluginPath(this),classLoader)
//        dexClassLoader.loadClass()
    }
}