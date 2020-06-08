package com.td.plugin_share

import android.util.Log
import com.td.plugin_share.PluginManager.Companion.pluginPkg
import dalvik.system.DexClassLoader
import java.io.File

/**
 * Createde By chenguting
 * 2020/6/8
 *
 */
class PluginClassLoader(
   var dexPath: String?,
    optimizedDirectory: String?,
    librarySearchPath: String?,
    parent: ClassLoader?
) : DexClassLoader(dexPath, optimizedDirectory, librarySearchPath, parent) {
    init {
        Log.e("dexpath",File(dexPath).exists().toString())
    }
    override fun loadClass(name: String): Class<*>? {
        Log.e("loadClass",name)
        try {
            if (name.startsWith(pluginPkg)) {
                return findClass(name)
            }
        } catch (e: ClassNotFoundException) {
            return null
        }

        return super.loadClass(name)
    }


}