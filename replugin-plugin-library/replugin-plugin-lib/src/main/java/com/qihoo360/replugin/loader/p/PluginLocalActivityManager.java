package com.qihoo360.replugin.loader.p;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Window;

import com.qihoo360.replugin.RePluginInternal;
import com.qihoo360.replugin.helper.LogDebug;
import com.qihoo360.replugin.i.IPluginManager;

import static com.qihoo360.replugin.helper.LogDebug.LOG;
import static com.qihoo360.replugin.helper.LogDebug.PLUGIN_TAG;

public class PluginLocalActivityManager extends LocalActivityManager {
    private Activity mParent;

    public PluginLocalActivityManager(Activity parent, boolean singleMode) {
        super(parent, singleMode);
        mParent = parent;
    }

    @Override
    public Window startActivity(String id, Intent intent) {
        // 获取Activity的名字，有两种途径：
        // 1. 从Intent里取。通常是明确知道要打开的插件的Activity时会用
        // 2. 从Intent的ComponentName中获取
        String name = intent.getStringExtra("activity");
        if (TextUtils.isEmpty(name)) {
            ComponentName cn = intent.getComponent();
            if (cn != null) {
                name = cn.getClassName();
                if (LOG) {
                    LogDebug.d(PLUGIN_TAG, "start context: custom context=" + this);
                }
            }
        }

        ComponentName cn = RePluginInternal.loadPluginActivity(intent, mParent.getApplicationInfo().packageName, name, IPluginManager.PROCESS_AUTO);
        if (null != cn) {
            intent.setComponent(cn);
        }

        return super.startActivity(id, intent);
    }


}
