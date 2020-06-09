/*
 * Tencent is pleased to support the open source community by making Tencent Shadow available.
 * Copyright (C) 2019 THL A29 Limited, a Tencent company.  All rights reserved.
 *
 * Licensed under the BSD 3-Clause License (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *     https://opensource.org/licenses/BSD-3-Clause
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.test.lib

import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.content.res.Resources
import android.content.res.Resources.Theme
import android.view.LayoutInflater

/**
 * 修改Context的apk路径的Wrapper。可将原Context的Resource和ClassLoader重新修改为新的Apk。
 */
class ChangeApkContextWrapper(
    base: Context,
    apkPath: String,
    private val mClassloader: ClassLoader
) : ContextWrapper(base) {
    private val mResources: Resources
    private var mLayoutInflater: LayoutInflater? = null
    private fun createResources(
        apkPath: String,
        base: Context
    ): Resources {
        val packageManager = base.packageManager
        val packageArchiveInfo =
            packageManager.getPackageArchiveInfo(apkPath, PackageManager.GET_META_DATA)
        packageArchiveInfo.applicationInfo.publicSourceDir = apkPath
        packageArchiveInfo.applicationInfo.sourceDir = apkPath
        return try {
            packageManager.getResourcesForApplication(packageArchiveInfo.applicationInfo)
        } catch (e: PackageManager.NameNotFoundException) {
            throw RuntimeException(e)
        }
    }

    override fun getAssets(): AssetManager {
        return mResources.assets
    }

    override fun getResources(): Resources {
        return mResources
    }

    override fun getTheme(): Theme {
        return mResources.newTheme()
    }

    override fun getSystemService(name: String): Any {
        if (Context.LAYOUT_INFLATER_SERVICE == name) {
            if (mLayoutInflater == null) {
                val layoutInflater = super.getSystemService(name) as LayoutInflater
                mLayoutInflater = layoutInflater.cloneInContext(this)
            }
            return mLayoutInflater!!
        }
        return super.getSystemService(name)
    }

    override fun getClassLoader(): ClassLoader {
        return mClassloader
    }

    init {
        mResources = createResources(apkPath, base)
    }
}