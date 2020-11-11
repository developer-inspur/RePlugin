/*
 * Copyright (C) 2006 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.app;

import com.qihoo360.replugin.utils.ReflectUtils;

/**
 * <p>For apps developing against {@link android.os.Build.VERSION_CODES#HONEYCOMB}
 * or later, tabs are typically presented in the UI using the new
 * {@link ActionBar#newTab() ActionBar.newTab()} and
 * related APIs for placing tabs within their action bar area.</p>
 *
 * @deprecated New applications should use Fragments instead of this class;
 * to continue to run on older devices, you can use the v4 support library
 * which provides a version of the Fragment API that is compatible down to
 * {@link android.os.Build.VERSION_CODES#DONUT}.
 */
@Deprecated
public class TabActivityFake extends TabActivity {

    public TabActivityFake() {
        super();

        LocalActivityManager activityManager = createLocalActivityManager(true);

        // REPLUGIN增加hook点，支持ActivityGroup
        // Android官方已废弃ActivityGroup以及TabActivity，建议业务使用Fragment进行多视图管理
        ReflectUtils.setFieldNonE(ActivityGroup.class, this, "mLocalActivityManager", activityManager);
    }

    public LocalActivityManager createLocalActivityManager(boolean singleActivityMode) {
        return new LocalActivityManager(this, singleActivityMode);
    }
}
