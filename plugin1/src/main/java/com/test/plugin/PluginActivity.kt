package com.test.plugin

import com.td.plugin.R
import com.test.lib.ActivityImpl

class PluginActivity : ActivityImpl {
    override fun getLayoutId(): Int {
        return R.layout.plugin_activity1
    }
}