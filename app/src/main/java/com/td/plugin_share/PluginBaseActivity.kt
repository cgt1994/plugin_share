package com.td.plugin_share

import android.content.Context
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.test.lib.ActivityImpl
import com.test.lib.ChangeApkContextWrapper
import kotlinx.android.synthetic.main.activity_plugin_base.*
import kotlin.concurrent.thread

class PluginBaseActivity : AppCompatActivity() {
    private var pluginContext: Context? = null
    lateinit var dexClassLoader: PluginClassLoader
    var classPath: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val i1 = System.currentTimeMillis()
        classPath = intent.extras?.getString(PluginManager.pluginKey) ?: ""
        setContentView(R.layout.activity_plugin_base)
        thread {

            dexClassLoader = PluginClassLoader(
                PluginManager.getPluginPath(this),
                null,
                PluginManager.getPluginPath(this),
                classLoader
            )
            if (dexClassLoader != null) {
                Looper.getMainLooper().queue.addIdleHandler {
                    init()
                    return@addIdleHandler false
                }
            }

        }
            .run()

        Log.e("pluginLoad", (System.currentTimeMillis() - i1).toString())

    }

    private fun init() {

        val loadClass = dexClassLoader.loadClass(classPath!!)
        if (loadClass != null) {
            val newInstance = loadClass.newInstance()
            if (newInstance is ActivityImpl) {
                pluginContext =
                    ChangeApkContextWrapper(this, PluginManager.getPluginPath(this), dexClassLoader)
                var impl = newInstance as ActivityImpl
                LayoutInflater.from(pluginContext).inflate(impl.getLayoutId(), contentFl)
            }
        } else {
            noPluginTv.visibility= View.VISIBLE

        }
    }
}