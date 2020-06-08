package com.td.plugin_share

import android.content.Context
import android.content.Intent
import android.os.Bundle

/**
 * Createde By chenguting
 * 2020/6/8
 *
 */
class PluginManager {

    companion object {
        val pluginKey = "key1"
        fun getPluginPath(context: Context) :String{
            return context.getExternalFilesDir(null)!!.absolutePath+"/plugin/app-debug.apk"
        }
        fun startActivity(context: Context, intent: Intent) {
            //启动插件Activity
            if ("com.test.plugin" == intent.component?.packageName
            ) {
                var intent = Intent()
                intent.setClass(context, PluginBaseActivity::class.java)
                val extras = intent.extras ?: Bundle()
                extras.putString(pluginKey, intent.component?.className)
                intent.putExtras(extras)
                context.startActivity(intent)
            } else {
                //启动正常Activity
                context.startActivity(intent)
            }
        }
    }
}