package com.td.plugin_share

import dalvik.system.DexClassLoader

/**
 * Createde By chenguting
 * 2020/6/8
 *
 */
class PluginClassLoader(
    dexPath: String?,
    optimizedDirectory: String?,
    librarySearchPath: String?,
    parent: ClassLoader?
) : DexClassLoader(dexPath, optimizedDirectory, librarySearchPath, parent) {

    override fun loadClass(name: String?): Class<*> {
        return super.loadClass(name)
    }
}