package com.td.plugin_share

import android.content.Context
import android.content.Intent
import android.os.Bundle
import java.io.File

/**
 * Createde By chenguting
 * 2020/6/8
 *
 */
class PluginManager {

    companion object {
        fun init(context: Context){
            val s = context.getExternalFilesDir(null)!!.absolutePath + "/plugin"
            val file=File(s)
            if (!file.exists()){
                file.mkdirs()
            }
        }
        val pluginKey = "key1"
        val pluginPkg = "com.test.plugin"

        fun getPluginPath(context: Context): String {

            return context.getExternalFilesDir(null)!!.absolutePath + "/plugin/plugin1-debug.apk"
        }

        fun startActivity(context: Context, intent: Intent) {
            val pluginClassName = (intent.component?.className ?: "")

            //启动插件Activity
            if (pluginClassName.startsWith(pluginPkg)) {
                var intent = Intent()
                intent.setClass(context, PluginBaseActivity::class.java)
                val extras = intent.extras ?: Bundle()
                extras.putString(pluginKey, pluginClassName)
                intent.putExtras(extras)
                context.startActivity(intent)
            } else {
                //启动正常Activity
                context.startActivity(intent)
            }
        }
    }
}