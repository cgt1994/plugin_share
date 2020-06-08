package com.td.plugin_share

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.test.pluginlib.ActivityImpl
import dalvik.system.DexClassLoader
import dalvik.system.PathClassLoader

class PluginBaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val classPath=intent.extras?.getString(PluginManager.pluginKey)?:""
        val dexClassLoader=PluginClassLoader(PluginManager.getPluginPath(this),null,PluginManager.getPluginPath(this),classLoader)
        val loadClass = dexClassLoader.loadClass(classPath)
        if (loadClass!=null){
            val newInstance = loadClass.newInstance()
            if (newInstance is ActivityImpl){
                var impl=newInstance as ActivityImpl
                setContentView(impl.getLayoutId())
            }
        }else{
            setContentView(R.layout.activity_plugin_base)

        }
    }
}