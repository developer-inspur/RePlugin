/*
 * Copyright (C) 2007 The Android Open Source Project
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
 * A screen that contains and runs multiple embedded activities.
 *
 * @deprecated Use the new {@link Fragment} and {@link FragmentManager} APIs
 * instead; these are also
 * available on older platforms through the Android compatibility package.
 */
@Deprecated
public class ActivityGroupFake extends ActivityGroup {

    public ActivityGroupFake() {
        this(true);
    }

    public ActivityGroupFake(boolean singleActivityMode) {
        super(singleActivityMode);

        LocalActivityManager activityManager = createLocalActivityManager(singleActivityMode);
        // REPLUGIN增加hook点，支持ActivityGroup
        // Android官方已废弃ActivityGroup以及TabActivity，建议业务使用Fragment进行多视图管理
        ReflectUtils.setFieldNonE(ActivityGroup.class, this, "mLocalActivityManager", activityManager);
    }

    public LocalActivityManager createLocalActivityManager(boolean singleActivityMode) {
        return new LocalActivityManager(this, singleActivityMode);
    }
}


