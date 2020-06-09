package com.td.plugin_share

import android.app.Application
import android.content.Context
import android.util.Log
import com.test.lib.ReflectUtils

/**
 * Createde By chenguting
 * 2020/6/9
 *
 */
class PluginApp :Application(){

    override fun attachBaseContext(base: Context?) {
        patch(this)
        super.attachBaseContext(base)
    }
    fun patch(application: Application): Boolean {
        try {
            //********************************************
            // =》 1. 反射， 获取classLoader 字段
            val oBase = application.baseContext ?: return false
            val oPackageInfo: Any = ReflectUtils.readField(oBase, "mPackageInfo") ?: return false

            // mPackageInfo的类型主要有两种：
            // 1. android.app.ActivityThread$PackageInfo - Android 2.1 - 2.3
            // 2. android.app.LoadedApk - Android 2.3.3 and higher
            // 获取mPackageInfo.mClassLoader
            val oClassLoader =
                ReflectUtils.readField(oPackageInfo, "mClassLoader") as ClassLoader
                    ?: return false
            // 以上 拿到了classLoader字段
            // *****************************************************************
            // 都不用看， 这段肯定是用来生成RepluginClassLoader的
//            val cl: ClassLoader = RePlugin.getConfig().getCallbacks()
//                .createClassLoader(oClassLoader.parent, oClassLoader)
            val cl=  PluginClassLoader(
                PluginManager.getPluginPath(this),
                null,
                PluginManager.getPluginPath(this),
                classLoader
            )
            // 将新的ClassLoader写入mPackageInfo.mClassLoader
            ReflectUtils.writeField(oPackageInfo, "mClassLoader", cl)

            // 设置线程上下文中的ClassLoader为RePluginClassLoader
            // 防止在个别Java库用到了Thread.currentThread().getContextClassLoader()时，“用了原来的PathClassLoader”，或为空指针
            Thread.currentThread().contextClassLoader = cl
        } catch (e: Throwable) {
            Log.e("throwable",Log.getStackTraceString(e))
            e.printStackTrace()
            return false
        }
        return true
    }
}